package com.example.pokemonapp.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.services.ApiService
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.pokemonapp.R
import com.example.pokemonapp.model.Pokemon

@Composable
fun PokemonHomeContent(navigateToProfile: (Pokemon) -> Unit, modifier: Modifier) {

    val pokemons = remember { ApiService.pokemonList }
    val context = LocalContext.current
    val nextUrl = ApiService.nextUrl.value
    val previousUrl = ApiService.previousUrl.value

    Column (modifier = modifier){

    PokemonLogo()
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = {
                    if (!previousUrl.isNullOrEmpty()) {
                        ApiService.loadPokemonPage(context, previousUrl){
                                success ->
                            if (!success){
                                Toast.makeText(context, "Error loading previous page", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.buttonColor),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (!nextUrl.isNullOrEmpty()) {
                        ApiService.loadPokemonPage(context, nextUrl){
                                success ->
                            if (!success){
                                Toast.makeText(context, "Error loading next page", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.buttonColor),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Next")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
    ) {

        items(
            items = pokemons,
            itemContent = {
                PokemonListItem(pokemon = it,navigateToProfile)
            }
        )
        }
    }

}

@Composable
fun PokemonLogo(){
    val logoPainter = painterResource(id = R.drawable.pokemonlogo)

    Image(
        painter = logoPainter,
        contentDescription = "Pokemon Logo",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp)
    )
}