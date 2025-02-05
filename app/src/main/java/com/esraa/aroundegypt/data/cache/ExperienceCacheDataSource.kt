package com.esraa.aroundegypt.data.cache


interface ExperienceCacheDataSource {

    suspend fun getRecommendedExperiences(): List<RecommendedExperienceCache>

   suspend fun setRecommendedExperiences(recommendedExperiences: List<RecommendedExperienceCache>)

    suspend fun getRecentExperiences(): List<RecentExperienceCache>

   suspend fun setRecentExperiences(recentExperiences: List<RecentExperienceCache>)
}