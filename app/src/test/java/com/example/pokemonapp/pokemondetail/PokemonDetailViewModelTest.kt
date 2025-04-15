package com.example.pokemonapp.pokemondetail

import com.example.pokemonapp.data.remote.responses.Pokemon
import com.example.pokemonapp.repository.PokemonRepository
import com.example.pokemonapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

    private lateinit var viewModel: PokemonDetailViewModel
    private val repository: PokemonRepository = mockk()

    @Before
    fun beforeEach() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = PokemonDetailViewModel(repository)
    }

    @After
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `Successful Pokemon retrieval`() = runTest {
        val mockPokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            height = 7,
            weight = 69,
            types = emptyList(),
            sprites = null,
            stats = emptyList(),
            abilities = emptyList(),
            location_area_encounters = "",
            species = null,
            base_experience = 0
        )

        coEvery { repository.getPokemonInfo("bulbasaur") } returns Resource.Success(mockPokemon)

        val result = viewModel.getPokemonInfo("bulbasaur")

        assertTrue(result is Resource.Success)
        assertEquals("bulbasaur", (result as Resource.Success).data?.name)
        assertEquals(7, result.data?.height)
        assertEquals(69, result.data?.weight)
        assertEquals(0, result.data?.base_experience)
    }

    @Test
    fun `Pokemon not found`() = runTest {
        val invalidName = "InvalidPokemonName"
        val errorMessage = "Pokemon not found"

        coEvery { repository.getPokemonInfo(invalidName) } returns Resource.Error(errorMessage)

        val result = viewModel.getPokemonInfo(invalidName)

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }
}