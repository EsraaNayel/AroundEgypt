package com.esraa.aroundegypt.ui.list.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.esraa.aroundegypt.AppModule
import com.esraa.aroundegypt.data.ExperiencesRepositoryImpl
import com.esraa.aroundegypt.data.cache.ExperiencesCacheDataSource
import com.esraa.aroundegypt.data.remote.ExperiencesRemoteDataSource
import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.domain.usecases.GetRecentExperiencesUseCase
import com.esraa.aroundegypt.domain.usecases.GetRecommendedExperiencesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ExperienceViewModel(
    private val fetchRecommendedExperiencesUseCase: GetRecommendedExperiencesUseCase,
    private val fetchRecentExperiencesUseCase: GetRecentExperiencesUseCase,
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val api = AppModule.provideAPI()
                val db = AppModule.provideDatabase(context)

                val remoteDataSource = ExperiencesRemoteDataSource(api)
                val cacheDataSource = ExperiencesCacheDataSource(db.dataDao())
                val repo = ExperiencesRepositoryImpl(remoteDataSource, cacheDataSource)

                ExperienceViewModel(
                    GetRecommendedExperiencesUseCase(repo),
                    GetRecentExperiencesUseCase(repo)
                )
            }
        }
    }

    private val _recentExperiences = MutableStateFlow<List<Experience>>(emptyList())
    private val _recommendedExperiences = MutableStateFlow<List<Experience>>(emptyList())

    val recentExperiences: StateFlow<List<Experience>> get() = _recentExperiences
    val recommendedExperiences: StateFlow<List<Experience>> get() = _recommendedExperiences

    init {
        viewModelScope.launch {
            fetchRecommendedExperiences()
        }

        viewModelScope.launch {
            fetchRecentExperiences()
        }
    }


    suspend fun fetchRecentExperiences() {
        fetchRecentExperiencesUseCase().catch {

        }.collect { experiences ->
            _recentExperiences.value = experiences
        }
    }

    suspend fun fetchRecommendedExperiences() {
        fetchRecommendedExperiencesUseCase().catch {

        }.collect { experiences ->
            _recommendedExperiences.value = experiences
        }
    }
}
