package ru.startandroid.organizer.home.widget.common

import java.lang.IllegalArgumentException
import javax.inject.Inject

class WidgetDeepLinkBuilder @Inject constructor() {

    private val scheme: String = "app"
    private val authority: String = "organizer"

    enum class Action constructor(val path: String) {
        SETTINGS("settings"), REFRESH("refresh"), CLOSE("close")
    }

    private var id: Int = 0
    private lateinit var action: Action

    fun widget(id: Int):WidgetDeepLinkBuilder {
        this.id = id
        return this
    }

    fun action(action: Action): WidgetDeepLinkBuilder {
        this.action = action
        return this
    }

    fun build(): String {
        if (id <= 0) throw IllegalArgumentException("id must be > 0")
        return "$scheme://$authority/widget/$id/${action.path}"
    }

}