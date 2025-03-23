package ru.antonlm.data.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.antonlm.data.data.local.models.CourseDbo

@Database(entities = [CourseDbo::class], version = 1)
@TypeConverters(Converters::class)
abstract class CoursesDb : RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDao
}
