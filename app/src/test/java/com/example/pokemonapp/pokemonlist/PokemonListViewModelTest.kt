package com.example.pokemonapp.pokemonlist

import app.cash.turbine.test
import com.example.pokemonapp.data.remote.responses.PokemonList
import com.example.pokemonapp.data.remote.responses.Result
import com.example.pokemonapp.model.PokedexListEntry
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private lateinit var viewModel: PokemonListViewModel
    private val repository: PokemonRepository = mockk(relaxed = true)

    @Before
    fun beforeEach() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = PokemonListViewModel(repository)
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `test loadPokemon success updates pokemon list`() = runTest {
        val pokemonListResponse = PokemonList(
            count = 2,
            next = "https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20",
            previous = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=20",
            results = listOf(
                Result(
                    "bulbasaur",
                    "https://pokeapi.co/api/v2/pokemon/1/"
                ),
                Result("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
            )
        )

        val mockResponse = Resource.Success(pokemonListResponse)

        coEvery { repository.getPokemonList(any(), any()) } returns mockResponse

        viewModel.loadPokemon()

        viewModel.pokemonList.test {
            val emittedList = awaitItem()

            assertEquals(2, emittedList.size)

            assertEquals("bulbasaur", emittedList[0].pokemonName)
            assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", emittedList[0].imageUrl)
            assertEquals(1, emittedList[0].id)

            assertEquals("ivysaur", emittedList[1].pokemonName)
            assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png", emittedList[1].imageUrl)
            assertEquals(2, emittedList[1].id)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadPokemon success with empty results`() = runTest{
        val pokemonListResponse = PokemonList(
            count = 0,
            next = "",
            previous = "",
            results = emptyList()
        )

        val mockResponse = Resource.Success(pokemonListResponse)

        coEvery { repository.getPokemonList(any(), any()) } returns mockResponse

        viewModel.loadPokemon()

        viewModel.pokemonList.test {
            val emittedList = awaitItem()

            assertEquals(0, emittedList.size)

            assertEquals(false, viewModel.isLoading.value)

            assertEquals("", viewModel.loadError.value)

            cancelAndConsumeRemainingEvents()
        }
    }
}
