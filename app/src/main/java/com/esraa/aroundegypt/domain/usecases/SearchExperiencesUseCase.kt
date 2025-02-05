package com.esraa.aroundegypt.domain.usecases

import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.domain.repositories.ExperiencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class SearchExperiencesUseCase(text: String,
    private val experiencesRepository: ExperiencesRepository
) {
    private val text = text.trim()
    operator fun invoke(): Flow<List<Experience>> =
        experiencesRepository.searchExperiences(text)
            .flowOn(Dispatchers.IO)

}