package com.example.pokemonapp.controler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapp.model.Pokemon
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.view.ProfileScreen

class PokemonProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                ProfileScreen(pokemon = pokemon)
            }
        }
    }
    companion object {
        private const val POKEMON_ID = "pokemon_id"

        fun newIntent(context: Context, pokemon: Pokemon) =
            Intent(context, PokemonProfileActivity::class.java).apply {
                putExtra(POKEMON_ID, pokemon)
            }
    }

    private val pokemon: Pokemon by lazy {
        intent?.getSerializableExtra(POKEMON_ID) as Pokemon
    }
}