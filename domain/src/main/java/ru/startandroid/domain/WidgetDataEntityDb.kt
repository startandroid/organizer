package ru.startandroid.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO use constants for table names
@Entity(tableName = "widgets")
data class WidgetDataEntityDb(
        @PrimaryKey val id: Int,
        val data: String
)