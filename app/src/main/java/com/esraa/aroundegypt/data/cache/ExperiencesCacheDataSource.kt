package com.esraa.aroundegypt.data.cache

class ExperiencesCacheDataSource(
    private val experienceDao: ExperienceDao
) : ExperienceCacheDataSource {

    override suspend fun getRecommendedExperiences(): List<RecommendedExperienceCache> {
        return experienceDao.getRecommendedExperiencesCache()
    }

    override suspend fun setRecommendedExperiences(recommendedExperiences: List<RecommendedExperienceCache>) {
        experienceDao.insertRecommendedExperiencesCache(recommendedExperiences)
    }

    override suspend fun getRecentExperiences(): List<RecentExperienceCache> {
        return experienceDao.getRecentExperiencesCache()
    }

    override suspend fun setRecentExperiences(recentExperiences: List<RecentExperienceCache>) {
        experienceDao.insertRecentExperiencesCache(recentExperiences)
    }
}