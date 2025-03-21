package ru.antonlm.auth.ui

import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.antonlm.auth.R
import ru.antonlm.auth.databinding.FragmentAuthBinding
import ru.antonlm.common.ui.BaseFragment
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager

class AuthFragment : BaseFragment() {

    private val binding get() = viewBinding as FragmentAuthBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initOperations(savedInstanceState: Bundle?) {
        (requireActivity() as? BottomNavigationViewVisibilityManager)?.setBottomNavigationViewVisible(isVisible = false)
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding){
        setupToRegistrationText()

        buttonToVk.setOnClickListener {
            openChromeTabsIntent(uri = Uri.parse(getString(R.string.vk_link)))
        }

        buttonToOk.setOnClickListener {
            openChromeTabsIntent(uri = Uri.parse(getString(R.string.ok_link)))
        }
    }

    private fun setupToRegistrationText() {
        val spannable = SpannableStringBuilder(getString(R.string.to_registration)).apply {
            val registrationString = getString(R.string.registration)
            val color = requireContext().getColor(ru.antonlm.theme.R.color.green)
            val span = ForegroundColorSpan(color)

            val startIndex = this.indexOf(registrationString)
            val endIndex = startIndex + registrationString.length

            if (startIndex != -1) {
                this.setSpan(span, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        binding.textViewToRegistration.text = spannable
    }
}
