package com.example.pokemonapp.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

    val entries by viewModel.pokemonList.collectAsState(emptyList())

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier) {
            Spacer(modifier = Modifier.height(16.dp))
            PokemonLogo()
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
            ) {
                viewModel.searchPokemonList(it)
            }
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
            .height(100.dp)
    )
}

@Composable
fun PokemonItem(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val types = entry.types.ifEmpty { listOf("Unknown") }
    val capitalizedTypes = types.map { it.replaceFirstChar { char -> char.uppercaseChar() } }

    val primaryType = types.first().lowercase()

    val backgroundColor = when (primaryType) {
        "grass" -> Color(0xFF78C850)
        "fire" -> Color(0xFFF08030)
        "water" -> Color(0xFF6890F0)
        "bug" -> Color(0xFFA8B820)
        "normal" -> Color(0xFFA8A878)
        "poison" -> Color(0xFFA040A0)
        "electric" -> Color(0xFFF8D030)
        "ground" -> Color(0xFFE0C068)
        "fairy" -> Color(0xFFEE99AC)
        else -> Color(0xFFD3D3D3)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable {
                navController.navigate("pokemon_detail_screen/${entry.pokemonName}")
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = entry.pokemonName.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                capitalizedTypes.forEach { type ->
                    Text(
                        text = type,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }

            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .build()
                ),
                contentDescription = "${entry.pokemonName} Image",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}



@Composable
fun PokedexList(
    entries: List<PokedexListEntry>,
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState(false)
    val endReached by viewModel.endReached.collectAsState(false)
    val isSearching by viewModel.isSearching.collectAsState(false)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = entries) { index, item ->
            PokemonItem(entry = item, navController = navController)
            if (index >= entries.size - 3 && !isLoading && !endReached && !isSearching) {
                viewModel.loadPokemon()
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
){
    var text by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        label = { Text(hint) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Ícone de procura"
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    text = ("")
                    onSearch("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Eliminar texto"
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}


