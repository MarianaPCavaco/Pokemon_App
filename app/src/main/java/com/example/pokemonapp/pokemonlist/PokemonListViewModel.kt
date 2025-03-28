package com.example.pokemonapp.pokemonlist

import android.graphics.pdf.PdfDocument.Page
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.model.PokedexListEntry
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.util.Constants.PAGE_SIZE
import com.example.pokemonapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel()  {
    }