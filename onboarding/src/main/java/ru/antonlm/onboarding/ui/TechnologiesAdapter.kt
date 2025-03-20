package ru.antonlm.onboarding.ui

import android.content.res.ColorStateList
import android.view.View.MeasureSpec
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.antonlm.data.domain.models.DisplayableItem
import ru.antonlm.onboarding.databinding.ItemTechnologyBinding


internal fun technologiesAdapterDelegate() =
    adapterDelegateViewBinding<Technology, DisplayableItem, ItemTechnologyBinding>({ layoutInflater, root ->
        ItemTechnologyBinding.inflate(
            layoutInflater, root, false
        )
    }) {
        bind {
            binding.buttonTitle.apply {
                text = getString(item.titleResId)

                elevation = if(item.angle != Angle.DEFAULT) 100f else 0.5f

                backgroundTintList = ColorStateList.valueOf(
                    context.getColor(
                        if (item.angle == Angle.DEFAULT) {
                            ru.antonlm.theme.R.color.glass
                        } else {
                            ru.antonlm.theme.R.color.green
                        }
                    )
                )

                if (item.angle != Angle.DEFAULT && item.bias != Bias.NO) {
                    measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)

                    val originalHeight = measuredHeight

                    rotation = item.angle.degree
                    post {
                        translationY = when (item.bias) {
                            Bias.NO -> 0f
                            Bias.TOP -> -(height - originalHeight).toFloat() / 2
                            Bias.BOTTOM -> (height - originalHeight).toFloat() / 2
                        }
                    }
                }
            }
        }
    }
