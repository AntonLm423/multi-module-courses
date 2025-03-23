package ru.antonlm.data.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorites")
class CourseDbo(
    @PrimaryKey val id: Int,
    @ColumnInfo("hasLike")
    val hasLike: Boolean,
    @ColumnInfo("price")
    val price: String,
    @ColumnInfo("publishDate")
    val publishDate: Date,
    @ColumnInfo("rate")
    val rate: String,
    @ColumnInfo("startDate")
    val startDate: Date,
    @ColumnInfo("text")
    val text: String,
    @ColumnInfo("title")
    val title: String
)
