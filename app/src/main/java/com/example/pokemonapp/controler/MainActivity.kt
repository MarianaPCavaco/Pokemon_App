package com.example.pokemonapp.controler

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemonapp.services.ApiService
import com.example.pokemonapp.ui.theme.PokemonAppTheme

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
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Scaffold { innerPadding ->
        PokemonHomeContent(modifier = Modifier.padding(innerPadding))
    }
}
