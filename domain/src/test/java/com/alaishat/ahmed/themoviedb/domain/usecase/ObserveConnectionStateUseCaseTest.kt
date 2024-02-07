package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.givenBlocking

/**
 * Created by Ahmed Al-Aishat on Feb/07/2024.
 * The Movie DB Project.
 */
@RunWith(MockitoJUnitRunner::class)
class ObserveConnectionStateUseCaseTest {
    private lateinit var classUnderTest: ObserveConnectionStateUseCase

    @Mock
    private lateinit var connectionRepository: ConnectionRepository

    @Before
    fun setUp() {
        classUnderTest = ObserveConnectionStateUseCase(connectionRepository = connectionRepository)
    }

    @Test
    fun `Given connection status when invoke then returns connection status`() = runBlocking {
        // Given
        val expected = ConnectionStateDomainModel.Connected(backOnline = false)
        givenBlocking { connectionRepository.observeConnectionState() }
            .willReturn(flowOf(expected))

        // When
        val actualValue = classUnderTest.invoke().first()

        // Then
        Assert.assertEquals(expected, actualValue)
    }
}