package com.example.pokemonapp.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.model.PokedexListEntry
import com.google.accompanist.coil.CoilImage

@Composable
fun PokemonListScreen(navController : NavController) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier) {
            PokemonLogo()
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
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(Color.White)
            .clickable {
                navController.navigate(
                    "pokemon_details_screen/${entry.pokemonName}"
                )
            }
        ){
        Column {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .build(),
                contentDescription = entry.pokemonName,
                fadeIn = true,
                modifier = Modifier
                    .size(100.dp)
                    .align(CenterHorizontally)
            ) {
                Text(
                    text = entry.pokemonName,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


