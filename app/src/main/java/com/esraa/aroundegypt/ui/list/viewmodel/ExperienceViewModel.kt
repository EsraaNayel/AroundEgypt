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
import com.esraa.aroundegypt.data.remote.models.ExperienceRemote
import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.domain.usecases.GetRecentExperiencesUseCase
import com.esraa.aroundegypt.domain.usecases.GetRecommendedExperiencesUseCase
import com.esraa.aroundegypt.domain.usecases.SearchExperiencesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ExperienceViewModel(
    private val fetchRecommendedExperiencesUseCase: GetRecommendedExperiencesUseCase,
    private val fetchRecentExperiencesUseCase: GetRecentExperiencesUseCase,
    private val searchExperiencesUseCase: SearchExperiencesUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

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
                    GetRecentExperiencesUseCase(repo),
                    SearchExperiencesUseCase("", repo)
                )
            }
        }
    }

    private val _searchResult = MutableStateFlow<List<Experience>>(emptyList())
    val searchResult: StateFlow<List<Experience>> get() = _searchResult

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

//        viewModelScope.launch {
//            SearchExperiences()
//        }
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

    suspend fun searchExperiences() {
        searchExperiencesUseCase().catch {

        }.collect { experiences ->
            _searchResult.value = experiences
        }
    }


    fun searchExperiences(searchText: String) {



        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                searchExperiencesUseCase().collect { experiences ->
                    _searchResult.value = experiences
                }
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }








//////////////////////////////////////////////
//        _isLoading.value = true
//        _error.value = null
//
//        searchExperiencesUseCase().catch {
//
//        }.collect { experiences ->
//            if (experiences.isEmpty()) {
//                _error.value = "No experiences found"
//                _searchResult.value = emptyList()
//            } else {
//                _searchResult.value = experiences
//                _isLoading.value = false
//            }
//        }

//                if (response.isSuccessful) {
//                    _searchResult.value = experiences
//                } else {
//                    val errorMessage = when (response.code()) {
//                        400 -> "Bad Request"
//                        401 -> "Unauthorized"
//                        404 -> "Not Found"
//                        500 -> "Internal Server Error"
//                        else -> "Unknown Error"
//                    }
//                    _error.value = errorMessage


}
