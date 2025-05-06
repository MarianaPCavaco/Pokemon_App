package com.example.pokemonapp.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.model.PokedexListEntry
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.util.Constants.PAGE_SIZE
import com.example.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<PokedexListEntry>>(emptyList())
    val pokemonList: MutableStateFlow<List<PokedexListEntry>> get() = _pokemonList

    private val _loadError = MutableStateFlow("")
    val loadError: MutableStateFlow<String> get() = _loadError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> get() = _isLoading

    private val _endReached = MutableStateFlow(false)
    val endReached: MutableStateFlow<Boolean> get() = _endReached

    private val _isSearching = MutableStateFlow(false)
    val isSearching: MutableStateFlow<Boolean> get() = _isSearching

    private val _fullPokemonList = MutableStateFlow<List<PokedexListEntry>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: MutableStateFlow<String> get() = _searchQuery


    var currentPage = 0

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)

            when (result) {
                is Resource.Success -> {
                    _endReached.value = currentPage * PAGE_SIZE >= result.data!!.count

                    val pokedexEntries = result.data.results.mapIndexed { _, entry ->
                        val id = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
                        Triple(entry.name, url, id.toInt())
                    }

                    val detailedEntries = pokedexEntries.map { (name, url, id) ->
                        async {
                            val info = repository.getPokemonInfo(name)
                            val types = if (info is Resource.Success) {
                                info.data?.types?.map { it.type.name } ?: emptyList()
                            } else emptyList()
                            PokedexListEntry(name, url, id, types)
                        }
                    }.map { it.await() }

                    currentPage++

                    val updatedList = _fullPokemonList.value + detailedEntries

                    _fullPokemonList.value = updatedList.distinctBy { "${it.pokemonName}_${it.id}" }
                    _pokemonList.value = _fullPokemonList.value

                    _loadError.value = ""
                    _isLoading.value = false
                }

                is Resource.Error -> {
                    _loadError.value = result.message ?: "Error occurred."
                    _isLoading.value = false
                }

                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
            _isLoading.value = false
        }
    }

    fun searchPokemonList(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _searchQuery.value = query

            if (query.isBlank()) {
                _pokemonList.value = _fullPokemonList.value
                _isSearching.value = false
            } else {
                val results = _fullPokemonList.value.filter {
                    it.pokemonName.startsWith(
                        query.trim(),
                        ignoreCase = true)
                }
                _pokemonList.value = results
                _isSearching.value = true
            }

            _isLoading.value = false
        }
    }

}
