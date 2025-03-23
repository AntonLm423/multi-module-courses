package ru.antonlm.common.domain

import androidx.recyclerview.widget.DiffUtil
import ru.antonlm.common.domain.Course.Payloads

/**
 * Interface should be inherit if class will be used in AdapterDelegate.
 * */
interface DisplayableItem {

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<DisplayableItem>() {
            override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
                return when {
                    oldItem is Course -> newItem is Course && oldItem.id == newItem.id
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
                return when {
                    oldItem is Course && newItem is Course -> (oldItem as Course) == (newItem as Course)
                    else -> false
                }
            }

            override fun getChangePayload(oldItem: DisplayableItem, newItem: DisplayableItem): Any? {
                return when {
                    oldItem is Course && newItem is Course -> if (oldItem.hasLike != newItem.hasLike) {
                        Payloads.FavoriteState(newItem.hasLike)
                    } else {
                        null
                    }

                    else -> null
                }
            }
        }
    }
}
