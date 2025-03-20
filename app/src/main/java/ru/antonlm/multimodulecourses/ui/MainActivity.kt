package ru.antonlm.multimodulecourses.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.antonlm.common.utils.BottomNavigationViewVisibilityManager
import ru.antonlm.multimodulecourses.R
import ru.antonlm.multimodulecourses.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationViewVisibilityManager {

    private var viewBinding: ActivityMainBinding? = null
    private val binding get() = viewBinding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navView.setupWithNavController(navController)
    }

    override fun setBottomNavigationViewVisible(isVisible: Boolean) {
        TODO("Not yet implemented")
    }
}
