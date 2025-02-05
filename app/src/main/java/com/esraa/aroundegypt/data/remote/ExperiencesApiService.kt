package com.esraa.aroundegypt.data.remote

import com.esraa.aroundegypt.data.remote.models.ExperiencesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExperiencesApiService {

    @GET("experiences?filter[recommended]=true")
    suspend fun getRecommendedExperiences(): Response<ExperiencesResponse>

    @GET("experiences")
    suspend fun getRecentExperiences(): Response<ExperiencesResponse>
}