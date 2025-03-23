package ru.antonlm.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.antonlm.common.ui.DisplayedState
import ru.antonlm.data.domain.models.Course
import ru.antonlm.data.domain.onFailure
import ru.antonlm.data.domain.onSuccess
import ru.antonlm.data.domain.usecases.AddToFavoritesUseCase
import ru.antonlm.data.domain.usecases.GetAllCoursesUseCase
import ru.antonlm.data.domain.usecases.RemoveFromFavoritesUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _selectedSortType = MutableLiveData<SortType>(SortType.DEFAULT)
    val selectedSortType: LiveData<SortType> = _selectedSortType

    private val _courses = MutableLiveData<DisplayedState<List<Course>>>()
    val courses: LiveData<DisplayedState<List<Course>>> = _courses

    fun addToFavorite(course: Course) {
        viewModelScope.launch {
            addToFavoritesUseCase(course)
            updateCourseInList(courseId = course.id, hasLike = true)
        }
    }

    fun removeFromFavorite(courseId: Int) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(courseId)
            updateCourseInList(courseId = courseId, hasLike = false)
        }
    }

    private fun updateCourseInList(courseId: Int, hasLike: Boolean) {
        val currentCourses = (_courses.value as? DisplayedState.Success)?.data ?: return
        val updatedCourses = currentCourses.map { course ->
            if (course.id == courseId) {
                course.copy(hasLike = hasLike)
            } else {
                course
            }
        }
        _courses.value = DisplayedState.Success(updatedCourses)
    }


    fun changeSortType() {
        val currentSort = _selectedSortType.value ?: SortType.DEFAULT
        _selectedSortType.value = if (currentSort == SortType.DEFAULT) SortType.DATE_DESCENDING else SortType.DEFAULT
    }

    fun getCourses(sortType: SortType, searchQuery: String? = null) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val coursesResult = getAllCoursesUseCase.invoke()

                coursesResult.onSuccess { courses ->
                    val sortedCourses = sortCourses(sortType, courses)
                    _courses.postValue(DisplayedState.Success(sortedCourses))
                }.onFailure {
                    _courses.postValue(DisplayedState.Error())
                }
            }
        }
    }

    private fun sortCourses(sortType: SortType, courses: List<Course>): List<Course> {
        return when (sortType) {
            SortType.DEFAULT -> courses
            SortType.DATE_DESCENDING -> sortCoursesByPublishDateDescending(courses)
        }
    }

    private fun sortCoursesByPublishDateDescending(courses: List<Course>): List<Course> {
        return courses.sortedByDescending { course ->
            course.publishDate
        }
    }
}
