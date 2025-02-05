package com.esraa.aroundegypt.data

import com.esraa.aroundegypt.data.cache.ExperiencesCacheDataSource
import com.esraa.aroundegypt.data.cache.toExperience
import com.esraa.aroundegypt.data.cache.toRecentExperienceCache
import com.esraa.aroundegypt.data.cache.toRecommendedExperienceCache
import com.esraa.aroundegypt.data.remote.ExperiencesRemoteDataSource
import com.esraa.aroundegypt.data.remote.models.toExperience
import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.domain.repositories.ExperiencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExperiencesRepositoryImpl(
    private val remoteDataSource: ExperiencesRemoteDataSource,
    private val cacheDataSource: ExperiencesCacheDataSource
) : ExperiencesRepository {

    override fun getRecommendedExperiences(): Flow<List<Experience>> = flow {
        var cachedData = cacheDataSource.getRecommendedExperiences()
        if (cachedData.isNotEmpty()) {
            val experiences = cachedData.map { it.toExperience() }
            emit(experiences)
        }

        val remoteData = remoteDataSource.getRecommendedExperiences()
        val experiences = remoteData.map { it.toExperience() }
        cachedData = experiences.map { it.toRecommendedExperienceCache() }
        cacheDataSource.setRecommendedExperiences(cachedData)
        emit(experiences)
    }

    override fun getRecentExperiences(): Flow<List<Experience>> = flow {
        var cachedData = cacheDataSource.getRecentExperiences()
        if (cachedData.isNotEmpty()) {
            val experiences = cachedData.map { it.toExperience() }
            emit(experiences)
        }

        val remoteData = remoteDataSource.getRecentExperiences()
        val experiences = remoteData.map { it.toExperience() }
        cachedData = experiences.map { it.toRecentExperienceCache() }
        cacheDataSource.setRecentExperiences(cachedData)
        emit(experiences)
    }

    override fun searchExperiences(text: String): Flow<List<Experience>> = flow {
        val remoteData = remoteDataSource.searchExperiences(text)
        val experiences = remoteData.map { it.toExperience() }

        emit(experiences)
    }
}