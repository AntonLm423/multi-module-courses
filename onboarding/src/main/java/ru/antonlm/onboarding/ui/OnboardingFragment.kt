package ru.antonlm.onboarding.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.antonlm.common.extensions.updateMargin
import ru.antonlm.common.ui.BaseFragment
import ru.antonlm.data.domain.models.DisplayableItem
import ru.antonlm.onboarding.R
import ru.antonlm.onboarding.databinding.FragmentOnboardingBinding
import ru.antonlm.onboarding.di.OnboardingComponentViewModule
import javax.inject.Inject

class OnboardingFragment : BaseFragment() {

    private val binding get() = viewBinding as FragmentOnboardingBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<OnboardingViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<OnboardingComponentViewModule>()
            .onboardingComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        createLines()
        binding.buttonContinue.setOnClickListener {
            viewModel.markOnboardingShown()
            navigateToAuth()
        }
    }

    private fun createLines() {
        val recyclers = technologies.map { line -> createSingleLine(line) }
        var currentMarginTop = 0

        recyclers.forEach { recyclerView ->
            with(recyclerView) {
                val verticalPadding = resources.getDimensionPixelSize(R.dimen.technologies_line_margin_top)
                clipToPadding = false

                measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
                binding.frameLayoutTechnologies.addView(recyclerView)

                updatePadding(top = verticalPadding * 2, bottom = verticalPadding)
                updateMargin(top = currentMarginTop)

                currentMarginTop += measuredHeight + verticalPadding

                // for example
                postSafe {
                    layoutManager?.scrollToPosition((1..2).random())
                }
            }
        }
    }

    private fun createSingleLine(technologies: List<Technology>) = RecyclerView(requireContext()).apply {
        adapter = ListDelegationAdapter(
            AdapterDelegatesManager<List<DisplayableItem>>().addDelegate(technologiesAdapterDelegate())
        ).also { it.items = technologies }

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        addItemDecoration(TechnologyItemDecoration())

        // TODO: Think about better solution
        z = if (technologies.any { it.angle != Angle.DEFAULT }) 1f else 10f
    }

    private fun navigateToAuth() {
        val link = getString(ru.antonlm.common.R.string.deep_link_auth)
        val uri = Uri.parse(link)
        findNavController().navigate(uri)
    }

    private companion object {
        val technologies = listOf(
            listOf(
                Technology.normal(titleResId = R.string.technology_administration_1c),
                Technology.startToDown(titleResId = R.string.technology_rabbitmq),
                Technology.normal(titleResId = R.string.technology_traffic)
            ),
            listOf(
                Technology.normal(titleResId = R.string.technology_content_marketing),
                Technology.normal(titleResId = R.string.technology_b2b_marketing),
                Technology.normal(titleResId = R.string.technology_google_analytics)
            ),
            listOf(
                Technology.normal(titleResId = R.string.technology_ux_researcher),
                Technology.normal(titleResId = R.string.technology_web_analytics),
                Technology.startToTop(titleResId = R.string.technology_big_data)
            ),
            listOf(
                Technology.normal(titleResId = R.string.technology_game_design),
                Technology.normal(titleResId = R.string.technology_web_design),
                Technology.normal(titleResId = R.string.technology_cinema_4d),
                Technology.normal(titleResId = R.string.technology_prompt_engineering)
            ),
            listOf(
                Technology.normal(titleResId = R.string.technology_webflow),
                Technology.endToTop(titleResId = R.string.technology_three_js),
                Technology.normal(titleResId = R.string.technology_parsing),
                Technology.normal(titleResId = R.string.technology_python_development)
            )
        )
    }
}
