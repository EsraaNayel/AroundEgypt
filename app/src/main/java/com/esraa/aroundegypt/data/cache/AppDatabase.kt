package com.esraa.aroundegypt.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecommendedExperienceCache::class, RecentExperienceCache::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): ExperienceDao
}