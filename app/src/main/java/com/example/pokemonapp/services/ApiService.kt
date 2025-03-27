package com.example.pokemonapp.services

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.utilities.URL_GET_POKEMONS

object ApiService {

    val pokemonList = mutableStateListOf<Pokemon>()
    var nextUrl = mutableStateOf<String?>(null)
    var previousUrl = mutableStateOf<String?>(null)

    fun getAllPokemon(context: Context, complete: (Boolean) -> Unit) {
        loadPokemonPage(context, URL_GET_POKEMONS, complete)
    }

    fun loadPokemonPage(context: Context, url: String, complete: (Boolean) -> Unit) {

        val pokemonRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                val results = response.getJSONArray("results")
                nextUrl.value = response.optString("next", null)
                previousUrl.value = response.optString("previous", null)

                pokemonList.clear()

                for (i in 0 until results.length()) {
                    val pokemon = results.getJSONObject(i)
                    val name = pokemon.getString("name")
                    val urlPokemon = pokemon.getString("url")
                    val id = urlPokemon.trimEnd('/').split("/").last()
                    val pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                    pokemonDetails(context, id, pokemonImage, name, complete)
                }

                complete(true)

            } catch (e: Exception) {
                Log.e("API", "Erro ao interpretar JSON: $e")
                complete(false)
            }
        }, Response.ErrorListener { error ->
            Log.e("API", "Erro no pedido: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        Volley.newRequestQueue(context).add(pokemonRequest)
    }

    private fun pokemonDetails(
        context: Context,
        pokemonId: String,
        pokemonImage: String,
        name: String,
        complete: (Boolean) -> Unit
    ) {
        val url = "https://pokeapi.co/api/v2/pokemon/$pokemonId/"

        val detailRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                val baseExperience = response.getString("base_experience")
                val height = response.getString("height")
                val weight = response.getString("weight")

                val abilities = mutableListOf<String>()
                val abilitiesArray = response.getJSONArray("abilities")
                for (i in 0 until abilitiesArray.length()) {
                    val abilityObject = abilitiesArray.getJSONObject(i)
                    val abilityName = abilityObject.getJSONObject("ability").getString("name")
                    abilities.add(abilityName)
                }

                val types = mutableListOf<String>()
                val typesArray = response.getJSONArray("types")
                for (i in 0 until typesArray.length()) {
                    val typeObject = typesArray.getJSONObject(i)
                    val typeName = typeObject.getJSONObject("type").getString("name")
                    types.add(typeName)
                }
                val species = response.getJSONObject("species").getString("name")

                val newPokemon = Pokemon(
                    name = name,
                    pokemonImage = pokemonImage,
                    url = url,
                    base_experience = baseExperience,
                    height = height,
                    weight = weight,
                    abilities = abilities,
                    types = types,
                    species = species)

                pokemonList.add(newPokemon)

                Log.d("API", "Pokémon detalhado carregado: $name")
                complete(true)
            } catch (e: Exception) {
                Log.e("API", "Erro ao interpretar detalhes do Pokémon: $e")
                complete(false)
            }
        }, Response.ErrorListener { error ->
            Log.e("API", "Erro ao obter detalhes: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        Volley.newRequestQueue(context).add(detailRequest)
    }
}

