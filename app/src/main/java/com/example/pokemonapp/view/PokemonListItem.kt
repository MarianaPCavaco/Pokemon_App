package com.example.pokemonapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

/*@Composable
fun PokemonListItem(pokemon: Pokemon,navigateToProfile: (Pokemon) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row( Modifier.clickable { navigateToProfile(pokemon) }) {
            PokemonImage(pokemon)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = pokemon.name, style = typography.titleLarge)
                Text(text = pokemon.types.joinToString(", "),
                    style = typography.bodySmall,
                    color = Color.Gray)
            }
        }
    }
}

@Composable
private fun PokemonImage(pokemon: Pokemon) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(
                data = pokemon.pokemonImage
            ).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )
    Image(
        painter = painter,
        contentDescription = "Pokemon Image",
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
*/