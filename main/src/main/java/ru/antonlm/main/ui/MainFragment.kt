package ru.antonlm.main.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.antonlm.common.extensions.addSystemWindowInsetToPadding
import ru.antonlm.common.ui.BaseFragment
import ru.antonlm.common.ui.DisplayedState
import ru.antonlm.common.ui.coursesAdapterDelegate
import ru.antonlm.common.domain.Course
import ru.antonlm.common.domain.DisplayableItem
import ru.antonlm.common.ui.CourseItemDecoration
import ru.antonlm.main.databinding.FragmentMainBinding
import ru.antonlm.main.di.MainComponentViewModule
import javax.inject.Inject

class MainFragment : BaseFragment() {

    override val showBottomNavigationView = true

    private val binding get() = viewBinding as FragmentMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private val mainAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            DisplayableItem.diffUtil,
            AdapterDelegatesManager<List<DisplayableItem>>().addDelegate(
                coursesAdapterDelegate(
                    onAddToFavorite = { viewModel.addToFavorite(it) },
                    onRemoveFromFavorite = { viewModel.removeFromFavorite(it) })
            )
        )
    }

    private companion object {
        private const val FIRST_ITEM_MARGIN = 0
        private const val NORMAL_ITEM_MARGIN = 16
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<MainComponentViewModule>()
            .mainComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = with(binding) {
        root.addSystemWindowInsetToPadding(top = true)

        recyclerViewMain.apply {
            adapter = mainAdapter
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    CourseItemDecoration(
                        firstItemTopMarginDp = FIRST_ITEM_MARGIN,
                        normalItemTopMarginDp = NORMAL_ITEM_MARGIN,
                        lastItemBottomMarginDp = NORMAL_ITEM_MARGIN
                    )
                )
            }
        }

        layoutSort.setOnClickListener {
            viewModel.changeSortType()
        }
    }

    override fun onSubscribeViewModel() {
        viewModel.selectedSortType.observe { sortType ->
            binding.textViewSort.text = getString(sortType.titleResId)
            viewModel.getCourses(sortType = sortType)
        }

        viewModel.courses.observe { state ->
            binding.layoutSort.isEnabled = state !is DisplayedState.Loading

            when (state) {
                is DisplayedState.Success -> {
                    // TODO: Process empty state
                    val courses = state.data ?: return@observe
                    mainAdapter.items = courses
                }

                is DisplayedState.Loading -> {
                }

                is DisplayedState.Error -> {
                    Toast.makeText(requireContext(), getString(state.displayedMessageResId), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
