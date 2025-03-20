package com.example.pokemonapp.services

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.utilities.URL_GET_POKEMONS

object ApiService {

    val pokemonList = mutableStateListOf<Pokemon>()

    fun getAllPokemon(context: Context, complete: (Boolean) -> Unit) {
        val url = URL_GET_POKEMONS

        val pokemonRequest = object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
            try {
                val results = response.getJSONArray("results")
                pokemonList.clear()

                for (i in 0 until results.length()) {
                    val pokemon = results.getJSONObject(i)
                    val name = pokemon.getString("name")
                    val url = pokemon.getString("url")

                    val id = url.trimEnd('/').split("/").last()
                    val pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                    val newPokemon = Pokemon(name, pokemonImage, url)
                    pokemonList.add(newPokemon)
                }
                Log.d("API", "Pokemons carregados: ${pokemonList.size}")
                complete(true)
            } catch (e: Exception) {
                Log.e("API", "Erro ao interpretar JSON: $e")
                complete(false)
            }
        }, { error ->
            Log.e("API", "Erro no pedido: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        Volley.newRequestQueue(context).add(pokemonRequest)
    }
}
