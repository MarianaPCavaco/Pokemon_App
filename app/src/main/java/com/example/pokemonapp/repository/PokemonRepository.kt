package com.example.pokemonapp.repository

import com.example.pokemonapp.data.remote.PokeApi
import com.example.pokemonapp.data.remote.responses.Pokemon
import com.example.pokemonapp.data.remote.responses.PokemonList
import com.example.pokemonapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        return try {
            val response = api.getPokemonList(limit, offset)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred.")
        }
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return try {
            val response = api.getPokemonInfo(pokemonName)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred.")
        }
    }
}