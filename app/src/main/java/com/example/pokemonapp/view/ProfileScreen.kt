package com.example.pokemonapp.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemonapp.R

/*@Composable
fun ProfileScreen(pokemon: Pokemon) {
    val scrollState = rememberScrollState()

    Column (modifier = Modifier.fillMaxSize()){
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ProfileHeader(pokemon, this@BoxWithConstraints.maxHeight)
                    ProfileContent(pokemon, this@BoxWithConstraints.maxHeight)
                }
            }
        }

    }
}

@Composable
private fun ProfileHeader(pokemon: Pokemon, containerHeight: Dp) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pokemon.pokemonImage)
            .crossfade(true)
            .build(),
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
private fun ProfileContent(pokemon: Pokemon, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        Title(pokemon)

        ProfileProperty(
            label = stringResource(R.string.base_experience),
            value = pokemon.base_experience
        )

        ProfileProperty(
            label = stringResource(R.string.height),
            value = pokemon.height
        )

        ProfileProperty(
            label = stringResource(R.string.weight),
            value = pokemon.weight
        )

        ProfileProperty(
            label = stringResource(R.string.types),
            value = pokemon.types.joinToString(", ")
        )

        ProfileProperty(
            label = stringResource(R.string.abilities),
            value = pokemon.abilities.joinToString(", ")
        )

        ProfileProperty(
            label = stringResource(R.string.species),
            value = pokemon.species
        )
    }
}

@Composable
private fun Title(pokemon: Pokemon){
    Column (modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)){
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProfileProperty(label: String, value:String){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        HorizontalDivider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Visible
        )
    }
}

 */