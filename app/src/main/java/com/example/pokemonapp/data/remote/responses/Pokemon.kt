package com.example.pokemonapp.data.remote.responses

data class Pokemon(
    val abilities: List<Ability>,
    val base_experience: Int,
    val height: Int,
    val id: Int,
    val location_area_encounters: String,
    val name: String,
    val species: Species?,
    val sprites: Sprites?,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)