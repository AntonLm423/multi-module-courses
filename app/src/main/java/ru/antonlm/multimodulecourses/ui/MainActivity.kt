package ru.antonlm.multimodulecourses.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.enableEdgeToEdge
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import ru.antonlm.common.domain.UserStatus
import ru.antonlm.common.extensions.addSystemWindowInsetToPadding
import ru.antonlm.common.extensions.safeNavigate
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager
import ru.antonlm.multimodulecourses.R
import ru.antonlm.multimodulecourses.appComponent
import ru.antonlm.multimodulecourses.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BottomNavigationViewVisibilityManager {

    private var viewBinding: ActivityMainBinding? = null
    private val binding get() = viewBinding as ActivityMainBinding

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appComponent.inject(this)

        setupNavigation()
        processInsets()
    }

    private fun processInsets() {
        enableEdgeToEdge()
        binding.bottomNavigationView.addSystemWindowInsetToPadding(bottom = true)
    }

    /**
     * Navigation
     */
    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        navController = navHostFragment.findNavController().also {
            navView.setupWithNavController(it)
        }

        binding.bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener)

        if (viewModel.isOnboardingShown()) {
            val deepLinkString = getString(
                when (viewModel.getUserStatus()) {
                    UserStatus.ANONYMOUS -> ru.antonlm.common.R.string.deep_link_auth
                    UserStatus.AUTHORIZED -> ru.antonlm.common.R.string.deep_link_main
                }
            )
            navController?.safeNavigate(Uri.parse(deepLinkString))
        } else {
            // in onboarding by default
        }
    }

    private val onItemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
        handleMenuSelectionByNavController(item.itemId)
        true
    }

    @MainThread
    private fun handleMenuSelectionByNavController(menuItemId: Int, restoreState: Boolean = true, args: Bundle? = null) {
        val bundle = Bundle()
        val destination = when (menuItemId) {
            R.id.action_main -> {
                R.id.action_global_nav_graph_main
            }

            R.id.action_favorite -> {
                R.id.action_global_nav_graph_favorite
            }

            else -> return
        }

        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(false)
            .setRestoreState(restoreState)
            .setPopUpTo(destinationId = ru.antonlm.main.R.id.mainFragment, inclusive = false, saveState = true)

        navController?.safeNavigate(destination, bundle, navOptions.build())
    }

    /**
     * BottomNavigationViewVisibilityManager
     */
    override fun setBottomNavigationViewVisible(isVisible: Boolean) {
        binding.bottomNavigationView.isVisible = isVisible
    }
}
