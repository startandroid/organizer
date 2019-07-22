package ru.startandroid.device.dialog

import android.content.Intent

class DialogConfig {

    var title: Int? = null
        private set

    var message: Int? = null
        private set

    var positiveText: Int? = null
        private set

    var negativeText: Int? = null
        private set

    var neutralText: Int? = null
        private set

    var positiveAction: ((Intent?) -> Unit)? = null
        private set

    var negativeAction: ((Intent?) -> Unit)? = null
        private set

    var neutralAction: ((Intent?) -> Unit)? = null
        private set


    fun title(text: Int) = apply { title = text }

    fun message(text: Int) = apply { message = text }

    fun positive(text: Int, action: ((Intent?) -> Unit)? = null) = apply {
        positiveText = text
        positiveAction = action
    }

    fun negative(text: Int, action: ((Intent?) -> Unit)? = null) = apply {
        negativeText = text
        negativeAction = action
    }

    fun neutral(text: Int, action: ((Intent?) -> Unit)? = null) = apply {
        neutralText = text
        neutralAction = action
    }
}