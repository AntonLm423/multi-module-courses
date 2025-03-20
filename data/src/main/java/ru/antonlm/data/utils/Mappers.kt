package ru.antonlm.data.utils

import ru.antonlm.data.data.models.CourseDto
import ru.antonlm.data.domain.models.Course

internal class CourseDtoToCourseMapper : Mapper<CourseDto, Course> {
    override fun map(input: CourseDto): Course {
        return Course(
            hasLike = input.hasLike,
            id = input.id,
            price = input.price,
            publishDate = input.publishDate,
            rate = input.rate,
            startDate = input.startDate,
            text = input.text,
            title = input.title
        )
    }
}
