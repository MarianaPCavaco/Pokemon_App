package com.example.pokemonapp.controler

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.services.ApiService
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.view.PokemonHomeContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ApiService.getAllPokemon(this) { success ->
            if (success) {
                Log.d("API", "Pokemons carregados com sucesso")
            } else {
                Log.e("API", "Erro ao carregar pokemons")
            }
        }
        setContent {
            PokemonAppTheme {
                MyApp{
                    startActivity(PokemonProfileActivity.newIntent(this, it))
                }
            }
        }
    }
}

@Composable
fun MyApp(navigateToProfile: (Pokemon) -> Unit) {
    Scaffold { paddingValues ->
        PokemonHomeContent(navigateToProfile = navigateToProfile, modifier = Modifier.padding(paddingValues))
    }
}


