package ru.antonlm.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.antonlm.common.domain.Course
import ru.antonlm.common.ui.DisplayedState
import ru.antonlm.data.domain.onFailure
import ru.antonlm.data.domain.onSuccess
import ru.antonlm.data.domain.usecases.GetFavoriteCoursesUseCase
import ru.antonlm.data.domain.usecases.RemoveFromFavoritesUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _favoriteCourses = MutableLiveData<DisplayedState<List<Course>>>()
    val favoriteCourses: LiveData<DisplayedState<List<Course>>> = _favoriteCourses

    fun removeFromFavorite(courseId: Int) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(courseId)
            removeFromFavoriteCourseInList(courseId)
        }
    }

    private fun removeFromFavoriteCourseInList(courseId: Int,) {
        val currentCourses = (_favoriteCourses.value as? DisplayedState.Success)?.data ?: return
        val updatedCourses = currentCourses.filter { it.id != courseId }
        _favoriteCourses.value = DisplayedState.Success(updatedCourses)
    }


    fun getFavoriteCourses() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favoriteCoursesResult = getFavoriteCoursesUseCase.invoke()

                favoriteCoursesResult.onSuccess { favoriteCourses ->
                    _favoriteCourses.postValue(DisplayedState.Success(favoriteCourses))
                }.onFailure {
                    _favoriteCourses.postValue(DisplayedState.Error())
                }
            }
        }
    }
}
