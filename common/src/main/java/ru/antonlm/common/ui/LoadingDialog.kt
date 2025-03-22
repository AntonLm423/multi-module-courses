package ru.antonlm.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.antonlm.common.R
import ru.antonlm.common.databinding.DialogLoadingBinding
import ru.antonlm.common.extensions.dpToPx

class LoadingDialog : DialogFragment()  {

    private var viewBinding: DialogLoadingBinding? = null
    private val binding get() = requireNotNull(viewBinding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog ?: return
        val window = dialog.window ?: return

        isCancelable = false
        dialog.setCanceledOnTouchOutside(false)

        window.setBackgroundDrawableResource(R.color.transparent)
        window.attributes = window.attributes.apply {
            dimAmount = 0.1F
            width = 150.dpToPx()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
