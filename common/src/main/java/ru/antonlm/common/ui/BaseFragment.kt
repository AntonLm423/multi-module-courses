package ru.antonlm.common.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager


abstract class BaseFragment : Fragment() {

    abstract val showBottomNavigationView: Boolean
    protected var viewBinding: ViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
        initOperations(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupLayout(savedInstanceState)
        onSubscribeViewModel()
        processInsets()
    }

    override fun onResume() {
        super.onResume()
        setBottomNavigationViewVisible(isVisible = showBottomNavigationView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun processInsets() {
        viewBinding?.root?.updatePaddingOnKeyboardVisibilityChanged()
    }

    protected fun <T : View> T.postSafe(postCallback: (T) -> Unit) {
        this.post {
            if (this@BaseFragment.isAdded) {
                postCallback.invoke(this)
            }
        }
    }

    protected fun setBottomNavigationViewVisible(isVisible: Boolean) {
        (requireActivity() as? BottomNavigationViewVisibilityManager)?.setBottomNavigationViewVisible(isVisible)
    }

    /**
     * Dialogs
     */
    protected var loadingDialog: LoadingDialog? = null

    protected fun showLoadingDialog() {
        if(loadingDialog != null) return

        loadingDialog = LoadingDialog().also {
            it.show(parentFragmentManager, LoadingDialog::class.java.name)
        }
    }

    protected fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    /**
     * Chrome tabs
     */
    private val CHROME_STABLE_PACKAGE = "com.android.chrome"

    protected fun openChromeTabsIntent(uri: Uri) {
        try {
            val builder = CustomTabsIntent.Builder()

            val colorSchemeParams = CustomTabColorSchemeParams.Builder()
                .build()
            builder.setDefaultColorSchemeParams(colorSchemeParams)
            val customTabsIntent = builder.build()
            if (chromeAppIsInstalled(requireContext())) {
                customTabsIntent.intent.setPackage(CHROME_STABLE_PACKAGE)
            }
            customTabsIntent.launchUrl(requireContext(), uri)
        } catch (e: Exception) {
            when (e) {
                is ActivityNotFoundException -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(browserIntent)
                }

                else -> {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun chromeAppIsInstalled(context: Context): Boolean {
        return try {
            val pm = context.packageManager
            pm.getApplicationInfo(CHROME_STABLE_PACKAGE, PackageManager.GET_META_DATA)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Keyboard listener
     */
    var onUpdatePaddingAction: ((Boolean) -> Unit?)? = null

    /* Method hide bottom navigation view when keyboard is open */
    private fun View.updatePaddingOnKeyboardVisibilityChanged() {
        addKeyboardInsetListener(this) { isOpen: Boolean, height: Int ->
            runCatching {
                setBottomNavigationViewVisible(!isOpen && showBottomNavigationView)
                onUpdatePaddingAction?.invoke(isOpen)

                view?.updatePadding(
                    bottom = if (isOpen) {
                        height + (view?.paddingBottom ?: 0)
                    } else {
                        0
                    }
                )
            }
        }
    }

    private fun addKeyboardInsetListener(rootView: View, keyboardCallback: (visible: Boolean, Int) -> Unit) {
        rootView.postSafe {
            var isKeyboardVisible = false

            ViewCompat.setOnApplyWindowInsetsListener(it) { view, insetsCompat ->
                val keyboardHeight = insetsCompat.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val systemBarsHeight = insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).bottom

                val keyboardUpdateCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    keyboardHeight > 0
                } else {
                    val keyboardRatio = 0.25
                    val systemScreenHeight = requireActivity().window.decorView.height
                    val heightDiff = insetsCompat.systemWindowInsetBottom + insetsCompat.systemWindowInsetTop
                    heightDiff > keyboardRatio * systemScreenHeight
                }

                if (keyboardUpdateCheck != isKeyboardVisible) {
                    keyboardCallback(keyboardUpdateCheck, keyboardHeight - systemBarsHeight)
                    isKeyboardVisible = keyboardUpdateCheck
                }

                insetsCompat
            }
        }
    }

    /** Извлечение аргументов */
    protected open fun handleArguments() {}

    /** Вызывать методы вью модели, которые получают данные из репозиториев */
    protected open fun initOperations(savedInstanceState: Bundle?) {}

    /** Установка верстки фрагмента */
    protected open fun onSetupLayout(savedInstanceState: Bundle?) {}

    /** Подписки на State/LiveData */
    protected open fun onSubscribeViewModel() {}

    /**
     * LiveData ext
     */
    protected inline fun <T> LiveData<T>.observe(crossinline block: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer { t -> block.invoke(t) })
    }
}
