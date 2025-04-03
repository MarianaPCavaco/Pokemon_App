package com.example.pokemonapp.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.model.PokedexListEntry
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokedexListEntry>>(emptyList())
    val pokemonList: LiveData<List<PokedexListEntry>> get() = _pokemonList

    private val _loadError = MutableLiveData<String>("")
    val loadError: LiveData<String> get() = _loadError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _endReached = MutableLiveData(false)
    val endReached: LiveData<Boolean> get() = _endReached

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = repository.getPokemonList(20, 0)

            when (result){
                is Resource.Success -> {
                    val pokedexEntries = result.data!!.results.mapIndexed { index, entry ->
                        val id = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
                        PokedexListEntry(entry.name, url, id.toInt())
                    }

                    _loadError.value = ""
                    _isLoading.value = false
                    _pokemonList.value = pokedexEntries
                }
                is Resource.Error -> {
                    _loadError.value = result.message!!
                    _isLoading.value = false
                }
            }
            _isLoading.value = false
        }
    }
}