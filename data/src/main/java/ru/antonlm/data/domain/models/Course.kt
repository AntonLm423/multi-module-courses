package ru.antonlm.data.domain.models

import androidx.recyclerview.widget.DiffUtil
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

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean = oldItem == newItem

            override fun getChangePayload(oldItem: Course, newItem: Course): Any? {
                return if (oldItem.hasLike != newItem.hasLike) {
                    Payloads.FavoriteState(newItem.hasLike)
                } else {
                    null
                }
            }
        }
    }

}
