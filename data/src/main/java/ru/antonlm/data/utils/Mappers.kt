package ru.antonlm.data.utils

import ru.antonlm.data.data.local.models.CourseDbo
import ru.antonlm.data.data.remote.models.CourseDto
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

internal class CourseDaoToCourseMapper : Mapper<CourseDbo, Course> {
    override fun map(input: CourseDbo): Course {
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

internal class CourseToCourseDboMapper : Mapper<Course, CourseDbo> {
    override fun map(input: Course): CourseDbo {
        return CourseDbo(
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

