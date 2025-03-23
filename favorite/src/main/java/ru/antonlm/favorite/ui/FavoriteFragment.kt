package ru.antonlm.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.antonlm.common.domain.DisplayableItem
import ru.antonlm.common.extensions.addSystemWindowInsetToPadding
import ru.antonlm.common.extensions.hide
import ru.antonlm.common.extensions.show
import ru.antonlm.common.ui.BaseFragment
import ru.antonlm.common.ui.CourseItemDecoration
import ru.antonlm.common.ui.DisplayedState
import ru.antonlm.common.ui.coursesAdapterDelegate
import ru.antonlm.favorite.databinding.FragmentFavoriteBinding
import ru.antonlm.favorite.di.FavoriteComponentViewModule
import javax.inject.Inject

class FavoriteFragment : BaseFragment() {

    override val showBottomNavigationView = true

    private val binding get() = viewBinding as FragmentFavoriteBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FavoriteViewModel> { viewModelFactory }

    private val favoriteAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            DisplayableItem.diffUtil,
            AdapterDelegatesManager<List<DisplayableItem>>().addDelegate(
                coursesAdapterDelegate(
                    onAddToFavorite = { /*there is no such functionality on favorite screen*/ },
                    onRemoveFromFavorite = { viewModel.removeFromFavorite(it) })
            )
        )
    }

    private companion object {
        private const val FIRST_ITEM_MARGIN = 0
        private const val ITEM_MARGIN = 16
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<FavoriteComponentViewModule>()
            .favoriteComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // forced update
        viewModel.getFavoriteCourses()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?): Unit = with(binding) {
        binding.root.addSystemWindowInsetToPadding(top = true)

        recyclerViewMain.apply {
            adapter = favoriteAdapter
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    CourseItemDecoration(
                        firstItemTopMarginDp = FIRST_ITEM_MARGIN,
                        normalItemTopMarginDp = ITEM_MARGIN,
                        lastItemBottomMarginDp = ITEM_MARGIN
                    )
                )
            }
        }
    }

    override fun onSubscribeViewModel() {
        viewModel.favoriteCourses.observe { state ->
            when (state) {
                is DisplayedState.Success -> {
                    val courses = state.data

                    if (courses.isNullOrEmpty()) {
                        displayEmptyState()
                    } else {
                        favoriteAdapter.items = courses
                        displayDataState()
                    }
                }

                is DisplayedState.Loading -> {
                }

                is DisplayedState.Error -> {
                    Toast.makeText(requireContext(), getString(state.displayedMessageResId), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayEmptyState() = with(binding) {
        viewEmpty.root.show()
        recyclerViewMain.hide()
    }

    private fun displayDataState() = with(binding) {
        viewEmpty.root.hide()
        recyclerViewMain.show()
    }
}
