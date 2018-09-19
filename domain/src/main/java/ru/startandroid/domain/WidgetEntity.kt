package ru.startandroid.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "widgets")
data class WidgetEntity(
        @PrimaryKey val id: Long
)