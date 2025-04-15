package com.example.pokemonapp.pokemondetail

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.data.remote.responses.Pokemon
import com.example.pokemonapp.util.Resource

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
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
                    ProfileContent(pokemonInfo, this@BoxWithConstraints.maxHeight)
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

@Composable
private fun ProfileContent(
    entry: Resource<Pokemon>,
    containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        Title(entry)

        ProfileProperty(
            label = stringResource(R.string.base_experience),
            value = entry.data?.base_experience.toString()
        )

        ProfileProperty(
            label = stringResource(R.string.height),
            value = entry.data?.height.toString()
        )

        ProfileProperty(
            label = stringResource(R.string.weight),
            value = entry.data?.weight.toString()
        )

        entry.data?.types?.let { types ->
            val typeNames = types.joinToString(", ") { it.type.name.replaceFirstChar { char -> char.uppercaseChar() } }

            ProfileProperty(
                label = stringResource(R.string.types),
                value = typeNames
            )
        }
        entry.data?.abilities?.let { abilities ->
            val abilityNames = abilities.joinToString(", ") { it.ability.name.replaceFirstChar { char -> char.uppercaseChar() } }
            ProfileProperty(
                label = stringResource(R.string.abilities),
                value = abilityNames
            )
        }
        entry.data?.stats?.forEach { stat ->
            ProfileProperty(
                label = stat.stat.name.replaceFirstChar { it.uppercaseChar() },
                value = stat.base_stat.toString()
            )
        }
    }
}


@Composable
private fun Title(entry: Resource<Pokemon>){
    Column (modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)){
        entry.data?.let {
            Text(
                text = it.name.replaceFirstChar { char -> char.uppercaseChar() },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
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