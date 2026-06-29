package com.example.githubuserexplorer.domain.usecase

import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.User
import com.example.githubuserexplorer.domain.Model.usecase.GetUserUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetUserUseCaseTest {

    private val mockRepository : GitHubRepository = mock()

    private val useCase = GetUserUseCase(mockRepository)

    @Test
    fun `invoke with black username return null` () = runTest {

        //ACT - call the fuction
        val result = useCase("")

        //ASSERT - verify expected behaviour
        assertNull(result)
        verify(mockRepository, never()).getUser(any())
    }

    @Test
    fun `invoke with valid username return user`() = runTest{
        //ARRANGE
        val expected = User("torvalds","Linus", 234000,0,8,"url")
        whenever(mockRepository.getUser("torvalds")).thenReturn(expected)
        //ACT
        val result = useCase("torvalds")

        // ASSERT
        assertEquals(expected,result)
    }

    @Test
    fun `invoke with spaces trims username before calling repository`()= runTest(){

        // ARRANGE
        val fakeUser = User("torvalds", "Linus",0,0,0,"url")
        whenever(mockRepository.getUser("torvalds")).thenReturn(fakeUser)

        //ACT
        useCase("   torvalds   ")

        //ASSERT - repository called with trimmed name
        verify(mockRepository).getUser("torvalds")
    }

}