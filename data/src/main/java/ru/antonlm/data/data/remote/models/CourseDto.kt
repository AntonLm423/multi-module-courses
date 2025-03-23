package ru.antonlm.data.data.remote.models

import java.util.Date

/**
 * @param id - идентификатор курса
 * @param title - заголовок курса
 * @param text - описание курса.
 * @param price - цена курса
 * @param rate - рейтинг курса
 * @param startDate - дата начала курса
 * @param hasLike - признак, добавлен ли курс в избранное
 * @param publishDate - дата публикации курса
 */
class CourseDto(
    val hasLike: Boolean,
    val id: Int,
    val price: String,
    val publishDate: Date,
    val rate: String,
    val startDate: Date,
    val text: String,
    val title: String
)