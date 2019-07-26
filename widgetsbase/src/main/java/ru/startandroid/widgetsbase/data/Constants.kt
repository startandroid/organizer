package ru.startandroid.widgetsbase.data.db

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


// TODO move to another separate Constants.kt file
object PARAM_KEY {
    const val WIDGET_ID = "widgetid"
}