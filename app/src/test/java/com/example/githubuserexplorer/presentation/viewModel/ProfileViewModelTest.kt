package com.example.githubuserexplorer.presentation.viewModel

import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.User
import com.example.githubuserexplorer.domain.Model.usecase.GetUserUseCase
import com.example.githubuserexplorer.presentation.Screen.viewModel.ProfileVIewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*

class ProfileViewModelTest {

    // TestDispatcher makes coroutines run synchronously in tests
    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository : GitHubRepository = mock()

    private val getUserUseCase = GetUserUseCase(mockRepository)
    private lateinit var viewModel: ProfileVIewModel

    @Before
    fun setup() {
        // Replace Main dispatcher with test dispatcher
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileVIewModel(getUserUseCase = getUserUseCase,
            ioDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        // Reset dispatcher after each test
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProfile success updates uiState with user`() = runTest {
        // ARRANGE
        val user = User("torvalds", "Linus", 0, 0, 0, "url")
        whenever(mockRepository.getUser("torvalds")).thenReturn(user)

        // ACT
        viewModel.loadProfile("torvalds")
        advanceUntilIdle()  // wait for all coroutines to finish

        // ASSERT
        val state = viewModel.uiState.value
        assertEquals(user, state.user)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `loadProfile failure sets error state`() = runTest {
        // ARRANGE — mock returns null (user not found)
        whenever(mockRepository.getUser("unknown")).thenReturn(null)

        // ACT
        viewModel.loadProfile("unknown")
        advanceUntilIdle()

        // ASSERT
        val state = viewModel.uiState.value
        assertNull(state.user)
        assertNotNull(state.error)
    }
}