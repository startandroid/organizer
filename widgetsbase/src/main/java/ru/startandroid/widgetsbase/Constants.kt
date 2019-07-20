package ru.startandroid.widgetsbase

object DB_TABLE_CONFIG {
    const val TABLE_NAME = "widgets_config"

    object COLUMNS {
        const val ID = "id"
        const val CONFIG = "config"
        const val ENABLED = "enabled"
    }
}

object DB_TABLE_DATA {
    const val TABLE_NAME = "widgets_data"

    object COLUMNS {
        const val ID = "id"
        const val DATA = "data"
    }
}

object PARAM_KEY {
    const val WIDGET_ID = "widgetid"
}