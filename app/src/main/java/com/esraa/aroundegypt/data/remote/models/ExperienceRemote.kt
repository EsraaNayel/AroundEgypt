package com.esraa.aroundegypt.data.remote.models

import com.esraa.aroundegypt.domain.models.Experience

class ExperienceRemote(
    val id: String,
    val title: String,
    val cover_photo: String,
    val views_no: Int,
    val likes_no: Int,
    val recommended: Int,
    val is_liked: Boolean?,
    val rating: Int,
    val reviews_no: Int,
)

fun ExperienceRemote.toExperience(): Experience = Experience(
    id = id,
    title = title,
    cover_photo = cover_photo,
    views_no = views_no,
    likes_no= likes_no,
    recommended= recommended,
    is_liked = is_liked,
    rating = rating,
    reviews_no = reviews_no
)
