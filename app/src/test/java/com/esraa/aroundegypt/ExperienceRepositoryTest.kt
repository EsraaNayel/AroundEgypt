package com.esraa.aroundegypt


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.esraa.aroundegypt.data.ExperiencesRepositoryImpl
import com.esraa.aroundegypt.data.cache.ExperiencesCacheDataSource
import com.esraa.aroundegypt.data.cache.RecentExperienceCache
import com.esraa.aroundegypt.data.cache.RecommendedExperienceCache
import com.esraa.aroundegypt.data.remote.ExperiencesRemoteDataSource
import com.esraa.aroundegypt.data.remote.models.ExperienceRemote
import com.esraa.aroundegypt.domain.models.Experience
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class DefaultAuthorRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ExperiencesRepositoryImpl
    private lateinit var remoteDataSource: ExperiencesRemoteDataSource
    private lateinit var cacheDataSource: ExperiencesCacheDataSource

    private val testExperience = Experience("1", "test", "test", 100, 3, 1, true, 0, 20)
    private val testExperienceRemote = ExperienceRemote("1", "test", "test", 100, 3, 1, true, 0, 20)
    private val testRecommendedExperienceCache =
        RecommendedExperienceCache("1", "test", "test", 100, 3, 1, true, 0, 20)
    private val testRecentExperienceCache =
        RecentExperienceCache("1", "test", "test", 100, 3, 1, true, 0, 20)


    @Before
    fun setUp() {
        remoteDataSource = mock()
        cacheDataSource = mock()
        repository = ExperiencesRepositoryImpl(remoteDataSource, cacheDataSource)
    }

    @Test
    fun `getRecommendedExperiences - cache is not empty - should emit cached data`() {
        runTest {
            val cachedData = listOf(testRecommendedExperienceCache)
            val remoteData = listOf(testExperienceRemote)
            whenever(cacheDataSource.getRecommendedExperiences()).thenReturn(cachedData)
            whenever(remoteDataSource.getRecommendedExperiences()).thenReturn(remoteData)

            val result = repository.getRecommendedExperiences().toList()
            Assert.assertEquals(result[0], listOf(testExperience))
            Assert.assertEquals(2, result.size)

            verify(cacheDataSource).getRecommendedExperiences()
            verify(remoteDataSource).getRecommendedExperiences()
            verify(cacheDataSource).setRecommendedExperiences(listOf(testRecommendedExperienceCache))
        }
    }

    @Test
    fun `getRecommendedExperiences - cache is empty - should fetch from remote and update cache`() =
        runTest {
            val remoteData = listOf(testExperienceRemote)
            whenever(cacheDataSource.getRecommendedExperiences()).thenReturn(emptyList())
            whenever(remoteDataSource.getRecommendedExperiences()).thenReturn(remoteData)

            val result = repository.getRecommendedExperiences().toList()
            Assert.assertEquals(result[0], listOf(testExperience))
            Assert.assertEquals(1, result.size)

            verify(cacheDataSource).getRecommendedExperiences()
            verify(remoteDataSource).getRecommendedExperiences()
            verify(cacheDataSource).setRecommendedExperiences(listOf(testRecommendedExperienceCache))
        }

    @Test
    fun `getRecentExperiences - cache is not empty - should emit cached data`() = runTest {
        val cachedData = listOf(testRecentExperienceCache)
        val remoteData = listOf(testExperienceRemote)
        whenever(cacheDataSource.getRecentExperiences()).thenReturn(cachedData)
        whenever(remoteDataSource.getRecentExperiences()).thenReturn(remoteData)

        val result = repository.getRecentExperiences().toList()
        Assert.assertEquals(result[0], listOf(testExperience))
        Assert.assertEquals(2, result.size)

        verify(cacheDataSource).getRecentExperiences()
        verify(remoteDataSource).getRecentExperiences()
        verify(cacheDataSource).setRecentExperiences(listOf(testRecentExperienceCache))
    }

    @Test
    fun `getRecentExperiences - cache is empty - should fetch from remote and update cache`() =
        runTest {
            val remoteData = listOf(testExperienceRemote)
            whenever(cacheDataSource.getRecentExperiences()).thenReturn(emptyList())
            whenever(remoteDataSource.getRecentExperiences()).thenReturn(remoteData)

            val result = repository.getRecentExperiences().toList()
            Assert.assertEquals(result[0], listOf(testExperience))
            Assert.assertEquals(1, result.size)

            verify(cacheDataSource).getRecentExperiences()
            verify(remoteDataSource).getRecentExperiences()
            verify(cacheDataSource).setRecentExperiences(listOf(testRecentExperienceCache))
        }
}
