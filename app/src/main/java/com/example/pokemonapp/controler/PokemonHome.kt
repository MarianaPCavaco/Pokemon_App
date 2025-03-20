package com.example.pokemonapp.controler

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.services.ApiService
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier

@Composable
fun PokemonHomeContent(modifier: Modifier = Modifier) {
    val pokemons = remember { ApiService.pokemonList }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = pokemons,
            itemContent = {
                PokemonListItem(pokemon = it)
            }
        )
    }
}