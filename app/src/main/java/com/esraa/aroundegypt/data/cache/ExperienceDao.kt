package com.esraa.aroundegypt.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExperienceDao {

    @Query("SELECT * FROM RecommendedExperienceCache")
    suspend fun getRecommendedExperiencesCache(): List<RecommendedExperienceCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendedExperiencesCache(experiences: List<RecommendedExperienceCache>)

    @Query("SELECT * FROM RecentExperienceCache")
    suspend fun getRecentExperiencesCache(): List<RecentExperienceCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentExperiencesCache(experiences: List<RecentExperienceCache>)
}