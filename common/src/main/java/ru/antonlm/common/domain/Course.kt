package ru.antonlm.common.domain

import java.util.Date

data class Course(
    val hasLike: Boolean,
    val id: Int,
    val price: String,
    val publishDate: Date,
    val rate: String,
    val startDate: Date,
    val text: String,
    val title: String
) : DisplayableItem {

    sealed class Payloads {
        data class FavoriteState(val inFavorite: Boolean) : Payloads()
    }
}
