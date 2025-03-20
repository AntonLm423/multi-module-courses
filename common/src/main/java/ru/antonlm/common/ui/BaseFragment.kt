package ru.antonlm.common.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import ru.antonlm.common.extensions.addSystemWindowInsetToMargin
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager

abstract class BaseFragment : Fragment() {

    protected var viewBinding: ViewBinding? = null

    /** добавлять ли инсеты, при false - контент под системными окнами (edge-to-edge) */
    open val topInset = true
    open val bottomInset = false
    open val leftInset = true
    open val rightInset = true

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

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun processInsets() {
        viewBinding?.root?.updatePaddingOnKeyboardVisibilityChanged()
        if (bottomInset) {
            viewBinding?.root?.addSystemWindowInsetToMargin(bottom = true)
        }
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

    /** Извлечение аргументов */
    protected open fun handleArguments() {}

    /** Вызывать методы вью модели, которые получают данные из репозиториев */
    protected open fun initOperations(savedInstanceState: Bundle?) {}

    /** Установка верстки фрагмента */
    protected open fun onSetupLayout(savedInstanceState: Bundle?) {}

    /** Подписки на State/LiveData */
    protected open fun onSubscribeViewModel() {}

    /**
     * Keyboard listener
     */
    var onUpdatePaddingAction: ((Boolean) -> Unit?)? = null

    fun View.updatePaddingOnKeyboardVisibilityChanged() {
        addKeyboardInsetListener(this) { isOpen: Boolean, height: Int ->
            runCatching {
                setBottomNavigationViewVisible(!isOpen)
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

    fun addKeyboardInsetListener(rootView: View, keyboardCallback: (visible: Boolean, Int) -> Unit) {
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

    protected inline fun <T> LiveData<T>.observe(crossinline block: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer { t -> block.invoke(t) })
    }
}