package com.esraa.aroundegypt.domain.models

data class Experience(
    val id: String,
    val title: String,
    val cover_photo: String,
//    val description: String,
    val views_no: Int,
    val likes_no: Int,
    val recommended: Int,
//    val has_video: Int,
//    val tags: List<Tag>,
//    val city: City,
//    val tour_html: String,
//    val famous_figure: String,
//    val period: Period,
//    val era: Era,
//    val founded: String?,
//    val detailed_description: String,
//    val address: String,
//    val gmap_location: GMapLocation,
//    val opening_hours: Map<String, List<String>>,
//    val translated_opening_hours: Map<String, TranslatedOpeningHours>,
//    val starting_price: Int,
//    val ticket_prices: List<TicketPrice>,
//    val experience_tips: List<String>,
    val is_liked: Boolean?,
//    val reviews: List<String>,
    val rating: Int,
    val reviews_no: Int,
//    val audio_url: String?,
//    val has_audio: Boolean
)


data class Tag(
    val id: Int,
    val name: String,
    val disable: Boolean?,
    val top_pick: Int
)

data class City(
    val id: Int,
    val name: String,
    val disable: Boolean?,
    val top_pick: Int
)

data class Period(
    val id: String,
    val value: String,
    val created_at: String,
    val updated_at: String
)

data class Era(
    val id: String,
    val value: String,
    val created_at: String,
    val updated_at: String
)

data class GMapLocation(
    val type: String,
    val coordinates: List<Double>
)

data class TranslatedOpeningHours(
    val day: String,
    val time: String
)

data class TicketPrice(
    val type: String,
    val price: Int
)


