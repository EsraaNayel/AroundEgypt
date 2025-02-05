package com.esraa.aroundegypt.domain.repositories

import com.esraa.aroundegypt.domain.models.Experience
import kotlinx.coroutines.flow.Flow

interface ExperiencesRepository {
    fun getRecommendedExperiences(): Flow<List<Experience>>
    fun getRecentExperiences(): Flow<List<Experience>>
}