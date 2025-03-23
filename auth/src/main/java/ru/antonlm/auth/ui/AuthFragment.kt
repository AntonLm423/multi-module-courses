package ru.antonlm.auth.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ru.antonlm.auth.R
import ru.antonlm.auth.databinding.FragmentAuthBinding
import ru.antonlm.auth.di.AuthComponentViewModule
import ru.antonlm.common.extensions.safeNavigate
import ru.antonlm.common.ui.BaseFragment
import ru.antonlm.common.ui.DisplayedState
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    override val showBottomNavigationView = false

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

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        setupToRegistrationText()

        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.validateInput(editTextEmail.text.toString(), editTextPassword.text.toString())
            }
        }

        editTextEmail.addTextChangedListener(textChangeListener)
        editTextPassword.addTextChangedListener(textChangeListener)

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
        viewModel.isLoginButtonEnabled.observe { isEnabled ->
            binding.buttonContinue.alpha = if (isEnabled) 1f else 0.7f
        }

        viewModel.authResult.observe { state ->
            when (state) {
                is DisplayedState.Success -> {
                    dismissLoadingDialog()
                    navigateToMain()
                }

                is DisplayedState.Loading -> {
                    showLoadingDialog()
                }

                is DisplayedState.Error -> {
                    dismissLoadingDialog()
                    Toast.makeText(
                        requireContext(),
                        state.displayedMessage ?: getString(state.displayedMessageResId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun navigateToMain() {
        val deeplink = getString(ru.antonlm.common.R.string.deep_link_main)
        findNavController().popBackStack(R.id.authFragment, true)
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
