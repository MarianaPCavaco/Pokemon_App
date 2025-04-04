package com.example.pokemonapp.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.data.remote.responses.Pokemon
import com.example.pokemonapp.data.remote.responses.PokemonList
import com.example.pokemonapp.model.PokedexListEntry
import com.example.pokemonapp.util.Resource

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    Column (modifier = Modifier.fillMaxSize()){
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ProfileHeader(pokemonInfo, this@BoxWithConstraints.maxHeight)
                    /*ProfileContent(pokemon, this@BoxWithConstraints.maxHeight)*/
                }
            }
        }

    }
}

@Composable
private fun ProfileHeader(
    entry: Resource<Pokemon>,
    containerHeight: Dp) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(entry.data?.sprites?.front_default)
                .build()
        ),
        contentDescription = entry.data?.name,
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
    )
}
/*
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

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
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
}*/