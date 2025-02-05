package com.esraa.aroundegypt.data.remote

import com.esraa.aroundegypt.data.remote.models.ExperiencesResponse
import com.esraa.aroundegypt.domain.models.Experience
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExperiencesApiService {

    @GET("experiences?filter[recommended]=true")
    suspend fun getRecommendedExperiences(): Response<ExperiencesResponse>

    @GET("experiences")
    suspend fun getRecentExperiences(): Response<ExperiencesResponse>

    @GET("/api/v2/experiences")
    suspend fun searchExperiences(@Query("filter[title]") title: String):
            Response<ExperiencesResponse>
}