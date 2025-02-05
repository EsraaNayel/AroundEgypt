package com.esraa.aroundegypt.data.remote

import com.esraa.aroundegypt.data.remote.models.ExperienceRemote
import com.esraa.aroundegypt.data.remote.models.ExperiencesResponse
import retrofit2.Response

class ExperiencesRemoteDataSource(
    private val apiService: ExperiencesApiService
) {
    suspend fun getRecommendedExperiences(): List<ExperienceRemote> {
        try {
            val response: Response<ExperiencesResponse> = apiService.getRecommendedExperiences()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return body.data
                } else {
                    throw EmptyResponseException("Recommended experiences response body is null")
                }
            } else {
                throw ApiException("API Error: ${response.code()} - ${response.message()}")
            }

        } catch (e: Exception) {
            throw NetworkException("Failed to fetch recommended experiences", e)
        }
    }

    suspend fun getRecentExperiences(): List<ExperienceRemote> {
        try {
            val response: Response<ExperiencesResponse> = apiService.getRecentExperiences()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return body.data
                } else {
                    throw EmptyResponseException("Recent experiences response body is null")
                }
            } else {
                throw ApiException("API Error: ${response.code()} - ${response.message()}")
            }

        } catch (e: Exception) {
            throw NetworkException("Failed to fetch recent experiences", e)
        }
    }

    suspend fun searchExperiences(text : String): List<ExperienceRemote> {
        try {
            val response: Response<ExperiencesResponse> = apiService.searchExperiences(text)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return body.data
                } else {
                    throw EmptyResponseException("Recent experiences response body is null")
                }
            } else {
                throw ApiException("API Error: ${response.code()} - ${response.message()}")
            }

        } catch (e: Exception) {
            throw NetworkException("Failed to fetch recent experiences", e)
        }
    }
}

class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause)
class ApiException(message: String) : Exception(message)
class EmptyResponseException(message: String) : Exception(message)
