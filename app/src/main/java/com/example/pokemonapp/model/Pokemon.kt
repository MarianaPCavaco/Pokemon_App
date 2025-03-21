package com.example.pokemonapp.model

import java.io.Serializable

data class Pokemon(
    val name: String,
    val pokemonImage: String,
    val url: String,
    val base_experience: String,
    val height: String,
    val weight: String,
    val abilities: List<String> = emptyList(),
    val types: List<String> = emptyList(),
    val species: String
    ) : Serializable
