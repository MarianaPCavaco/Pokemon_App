package com.example.pokemonapp.pokemonlist


import androidx.compose.runtime.mutableStateOf
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

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            isLoading.value = true

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

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }

        }
    }
}