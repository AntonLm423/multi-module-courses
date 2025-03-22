package ru.antonlm.auth.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import ru.antonlm.auth.R
import ru.antonlm.auth.databinding.FragmentAuthBinding
import ru.antonlm.auth.di.AuthComponentViewModule
import ru.antonlm.common.extensions.safeNavigate
import ru.antonlm.common.ui.BaseFragment
import ru.antonlm.common.ui.DisplayedState
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager
import ru.antonlm.data.domain.NetworkResult
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }


    private val binding get() = viewBinding as FragmentAuthBinding

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<AuthComponentViewModule>()
            .authComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initOperations(savedInstanceState: Bundle?) {
        (requireActivity() as? BottomNavigationViewVisibilityManager)?.setBottomNavigationViewVisible(isVisible = false)
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        setupToRegistrationText()

        buttonContinue.setOnClickListener {
            viewModel.auth(email = editTextEmail.text.toString(), password = editTextPassword.text.toString())
        }

        buttonToVk.setOnClickListener {
            openChromeTabsIntent(uri = Uri.parse(getString(R.string.vk_link)))
        }

        buttonToOk.setOnClickListener {
            openChromeTabsIntent(uri = Uri.parse(getString(R.string.ok_link)))
        }
    }

    override fun onSubscribeViewModel() {
        viewModel.authResult.observe { result ->
            when (result) {
                is DisplayedState.Success -> {
                    dismissLoadingDialog()
                    navigateToMain()
                }

                is DisplayedState.Loading -> {
                    showLoadingDialog()
                }

                is DisplayedState.Error -> {
                    dismissLoadingDialog()
                    Toast.makeText(requireContext(), getString(result.displayedMessageResId), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun navigateToMain() {
        val deeplink = getString(ru.antonlm.common.R.string.deep_link_main)
        findNavController().safeNavigate(Uri.parse(deeplink))
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
