package com.example.pokemonapp.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.model.PokedexListEntry

@Composable
fun PokemonListScreen(navController : NavController, viewModel: PokemonListViewModel =  hiltViewModel()) {

    val entries by viewModel.pokemonList.observeAsState(emptyList())

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier) {
            PokemonLogo()
            Spacer(modifier = Modifier.height(16.dp))
            PokedexList(entries = entries, navController = navController)
            }
        }
    }


@Composable
fun PokemonLogo(){
    val pokemonLogo = painterResource(id = R.drawable.pokemonlogo)
    Image(
        painter = pokemonLogo,
        contentDescription = "Pokemon Logo",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun PokemonItem(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable {
                navController.navigate("pokemon_detail/${entry.pokemonName}")
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .build()
                ),
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = entry.pokemonName,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PokedexList(
    entries: List<PokedexListEntry>,
    navController: NavController
) {
    LazyColumn {
        itemsIndexed(items = entries) { index, item ->
            PokemonItem(entry = item, navController = navController)
        }
    }
}


