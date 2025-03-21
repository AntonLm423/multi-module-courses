package ru.antonlm.multimodulecourses.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.antonlm.common.extensions.safeNavigate
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager
import ru.antonlm.multimodulecourses.R
import ru.antonlm.multimodulecourses.databinding.ActivityMainBinding
import ru.antonlm.multimodulecourses.di.MainActivityComponentViewModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BottomNavigationViewVisibilityManager {

    private var viewBinding: ActivityMainBinding? = null
    private val binding get() = viewBinding as ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(this)
            .get<MainActivityComponentViewModule>()
            .mainActivityComponent.inject(this)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        setupNavigation()

        if (viewModel.getOnboardingStatus()) {
            val uri = Uri.parse(getString(ru.antonlm.common.R.string.deep_link_auth))
            navController?.safeNavigate(uri)
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
