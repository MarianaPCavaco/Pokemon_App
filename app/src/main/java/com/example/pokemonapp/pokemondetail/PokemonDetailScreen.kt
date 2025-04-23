package com.example.pokemonapp.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.data.remote.responses.Pokemon
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
                    ProfileHeader(
                        pokemonInfo,
                        this@BoxWithConstraints.maxHeight,
                        onBackClick = {
                            navController.popBackStack()
                        })
                    ProfileContent(
                        pokemonInfo,
                        this@BoxWithConstraints.maxHeight
                    )

                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }

    }
}

@Composable
private fun ProfileHeader(
    entry: Resource<Pokemon>,
    containerHeight: Dp,
    onBackClick: () -> Unit) {
    val type = entry.data?.types?.firstOrNull()?.type?.name ?: "normal"
    val backgroundColor = when (type.lowercase()) {
        "grass" -> Color(0xFF48D0B0)
        "fire" -> Color(0xFFFF6C6C)
        "water" -> Color(0xFF76BEFE)
        "bug" -> Color(0xFF92D050)
        "normal" -> Color(0xFFC6C6C6)
        "poison" -> Color(0xFFB97FC9)
        "electric" -> Color(0xFFFFD86F)
        "ground" -> Color(0xFFD2B48C)
        "fairy" -> Color(0xFFFFC0CB)
        else -> Color(0xFFE0E0E0)
    }

    Box(
        modifier = Modifier
            .background(
                backgroundColor,
                shape = RoundedCornerShape(
                    bottomStart = 64.dp,
                    bottomEnd = 64.dp)
            )

    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(entry.data?.sprites?.front_default)
                    .build()
            ),
            contentDescription = "${entry.data?.name} Image",
            modifier = Modifier
                .heightIn(max = containerHeight)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
        BackButton(onBackClick)
    }
}

@Composable
private fun ProfileContent(
    entry: Resource<Pokemon>,
    containerHeight: Dp
) {
    Column(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Title(entry)

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            entry.data?.types?.forEach {
                TypeBadge(it.type.name)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        WeightAndHeight(
            weight = "%.1f".format((entry.data?.weight ?: 0) / 10f),
            height = "%.1f".format((entry.data?.height ?: 0) / 10f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )

        entry.data?.stats?.forEach { stat ->
            val color = when (stat.stat.name.lowercase()) {
                "hp" -> Color(0xFFE57373)
                "attack" -> Color(0xFFFFB74D)
                "defense" -> Color(0xFF64B5F6)
                "speed" -> Color(0xFF81C784)
                "special-attack" -> Color(0xFFBA68C8)
                "special-defense" -> Color(0xFF4DB6AC)
                else -> Color.Gray
            }

            StatBar(
                statName = stat.stat.name,
                value = stat.base_stat,
                color = color
            )
        }

        StatBar(
            statName = "EXP",
            value = entry.data?.base_experience ?: 0,
            maxValue = 1000,
            color = Color(0xFF81C784)
        )
    }
}



@Composable
private fun Title(
    entry: Resource<Pokemon>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
fun TypeBadge(type: String) {
    val color = when (type.lowercase()) {
        "grass" -> Color(0xFF48D0B0)
        "fire" -> Color(0xFFFF6C6C)
        "water" -> Color(0xFF76BEFE)
        "bug" -> Color(0xFF92D050)
        "normal" -> Color(0xFFC6C6C6)
        "poison" -> Color(0xFFB97FC9)
        "electric" -> Color(0xFFFFD86F)
        "ground" -> Color(0xFFD2B48C)
        "fairy" -> Color(0xFFFFC0CB)
        "ice" -> Color(0xFFB2EBF2)
        "flying" -> Color(0xFF90CAF9)
        else -> Color(0xFFE0E0E0)
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .width(120.dp)
            .height(32.dp)
            .background(color, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercaseChar() },
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeightAndHeight(
    weight: String,
    height: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$weight KG",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Weight",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$height M",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Height",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun StatBar(statName: String, value: Int, maxValue: Int = 300, color: Color) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)) {
        Text(
            text = statName.uppercase(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        ) {
            val progress = value.coerceAtMost(maxValue).toFloat() / maxValue
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(color, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "$value/$maxValue",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BackButton(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


