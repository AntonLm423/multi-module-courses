package ru.antonlm.common.ui

import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.antonlm.common.R
import ru.antonlm.common.databinding.ItemCourseBinding
import ru.antonlm.common.extensions.loadImage
import ru.antonlm.common.domain.Course
import ru.antonlm.common.domain.DisplayableItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun coursesAdapterDelegate(onAddToFavorite: (course: Course) -> Unit, onRemoveFromFavorite: (courseId: Int) -> Unit) =
    adapterDelegateViewBinding<Course, DisplayableItem, ItemCourseBinding>({ layoutInflater, root ->
        ItemCourseBinding.inflate(
            layoutInflater, root, false
        )
    }) {
        bind { payloads ->
            if (payloads.isEmpty()) {
                binding.bind(item, onAddToFavorite, onRemoveFromFavorite)
            } else {
                payloads.forEach { payload ->
                    when (payload) {
                        is Course.Payloads.FavoriteState ->
                            binding.bindFavorite(item, payload.inFavorite, onAddToFavorite, onRemoveFromFavorite)

                        else ->
                            binding.bind(item, onAddToFavorite, onRemoveFromFavorite)
                    }
                }
            }
        }
    }

private fun ItemCourseBinding.bind(
    item: Course,
    onAddToFavorite: (course: Course) -> Unit,
    onRemoveFromFavorite: (courseId: Int) -> Unit
) {
    textViewRating.text = item.rate
    textViewPublishDate.text = item.publishDate.formatDate()
    textViewTitle.text = item.title
    textViewDescription.text = item.text
    textViewPrice.text = root.context.getString(R.string.price_rubble, item.price)

    // Example URL, because mocked data don't have images
    imageViewPreview.loadImage(
        url = "https://farm9.staticflickr.com/8295/8007075227_dc958c1fe6_z_d.jpg",
        cornerRadius = 16
    )

    bindFavorite(item, item.hasLike, onAddToFavorite, onRemoveFromFavorite)
}

private fun ItemCourseBinding.bindFavorite(
    item: Course,
    inFavorite: Boolean,
    onAddToFavorite: (course: Course) -> Unit,
    onRemoveFromFavorite: (courseId: Int) -> Unit
) {
    imageButtonFavorite.setImageDrawable(
        getDrawable(root.context, if (inFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite)
    )

    imageButtonFavorite.setOnClickListener {
        if (inFavorite) {
            onRemoveFromFavorite.invoke(item.id)
        } else {
            onAddToFavorite.invoke(item)
        }
    }
}

private fun Date.formatDate(): String {
    val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    return dateFormat.format(this)
}
