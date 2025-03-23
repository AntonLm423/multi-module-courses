package ru.antonlm.data.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.antonlm.data.data.local.models.CourseDbo

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<CourseDbo>

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(course: CourseDbo)

    @Query("DELETE FROM favorites WHERE id = :courseId")
    suspend fun removeFromFavorites(courseId: Int)

    @Query("DELETE FROM favorites")
    suspend fun clear()
}
