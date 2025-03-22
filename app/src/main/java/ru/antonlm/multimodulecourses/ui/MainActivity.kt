package ru.antonlm.multimodulecourses.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.antonlm.common.domain.UserStatus
import ru.antonlm.common.extensions.safeNavigate
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager
import ru.antonlm.multimodulecourses.R
import ru.antonlm.multimodulecourses.appComponent
import ru.antonlm.multimodulecourses.databinding.ActivityMainBinding
import ru.antonlm.multimodulecourses.di.AppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BottomNavigationViewVisibilityManager {

    private var viewBinding: ActivityMainBinding? = null
    private val binding get() = viewBinding as ActivityMainBinding

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         appComponent.inject(this)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        setupNavigation()

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

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        navController = navHostFragment.findNavController().also {
            navView.setupWithNavController(it)
        }
    }

    override fun setBottomNavigationViewVisible(isVisible: Boolean) {
        binding.bottomNavigationView.isVisible = isVisible
    }
}
