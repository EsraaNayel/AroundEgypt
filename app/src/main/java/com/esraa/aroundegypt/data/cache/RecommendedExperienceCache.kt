package com.esraa.aroundegypt.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esraa.aroundegypt.domain.models.Experience

@Entity
data class RecommendedExperienceCache(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "cover_photo")
    val coverPhoto: String,
    @ColumnInfo(name = "views_no")
    val viewsNo: Int,
    @ColumnInfo(name = "likes_no")
    val likesNo: Int,
    @ColumnInfo(name = "recommended")
    val recommended: Int,
    @ColumnInfo(name = "is_liked")
    val isLiked: Boolean?,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "reviews_no")
    val reviewsNo: Int,
)

fun RecommendedExperienceCache.toExperience(): Experience {
    return Experience(
        id,
        title,
        coverPhoto,
        viewsNo,
        likesNo,
        recommended,
        isLiked,
        rating,
        reviewsNo
    )
}

fun Experience.toRecommendedExperienceCache(): RecommendedExperienceCache {
    return RecommendedExperienceCache(
        id,
        title,
        cover_photo,
        views_no,
        likes_no,
        recommended,
        is_liked,
        rating,
        reviews_no
    )
}

