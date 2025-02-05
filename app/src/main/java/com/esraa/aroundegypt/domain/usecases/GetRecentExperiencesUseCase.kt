package com.esraa.aroundegypt.domain.usecases

import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.domain.repositories.ExperiencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetRecentExperiencesUseCase(
    private val experiencesRepository: ExperiencesRepository
) {
    operator fun invoke(): Flow<List<Experience>> =
        experiencesRepository.getRecentExperiences()
            .flowOn(Dispatchers.IO)
}