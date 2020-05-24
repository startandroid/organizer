package ru.startandroid.widgetsbase.data

object DB_TABLE_CONFIG {
    const val TABLE_NAME = "widgets_config"

    object COLUMNS {
        const val ID = "id"
        const val CONFIG = "config"
        const val ENABLED = "enabled"
        const val UPDATE_INTERVAL = "update_interval"
    }
}

object DB_TABLE_DATA {
    const val TABLE_NAME = "widgets_data"

    object COLUMNS {
        const val ID = "id"
        const val DATA = "data"
    }
}

object DB_TABLE_REFRESH_STATUS {
    const val TABLE_NAME = "widgets_refresh_status"

    object COLUMNS {
        const val ID = "id"
        const val STATUS = "status" // 0 - done, 1 - in progress
    }
}

object DB_MAPPING {
    object BOOLEAN {
        const val TRUE = 1
        const val FALSE = 0
    }

    object REFRESH_STATUS {
        const val REFRESHING = 1
        const val DONE = 0
    }
}


object PARAM_KEY {
    const val WIDGET_ID = "widgetid"
    const val WIDGET_IDS = "widgetids"
}