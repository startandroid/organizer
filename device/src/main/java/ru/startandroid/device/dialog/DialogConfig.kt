package ru.startandroid.device.dialog

import android.app.Fragment
import android.content.Intent
import android.os.Bundle

class DialogConfig {

    private var positiveText: Int? = null
    private var negativeText: Int? = null
    private var neutralText: Int? = null
    private var positiveAction: ((Intent?) -> Unit)? = null
    private var negativeAction: ((Intent?) -> Unit)? = null
    private var neutralAction: ((Intent?) -> Unit)? = null
    private var title: Int? = null
    private var message: Int? = null

    fun title(text: Int) = apply { title = text }

    fun message(text: Int) = apply { message = text }

    fun positive(text: Int = android.R.string.yes, action: (Intent?) -> Unit) = apply {
        positiveText = text
        positiveAction = action
    }

    fun negative(text: Int = android.R.string.no, action: (Intent?) -> Unit) = apply {
        negativeText = text
        negativeAction = action
    }

    fun neutral(text: Int = android.R.string.cancel, action: (Intent?) -> Unit) = apply {
        neutralText = text
        neutralAction = action
    }

    internal fun show(dialogCode: Int, targetFragment: Fragment) {
        val fragment = DialogFragment()
        fragment.setTargetFragment(targetFragment, dialogCode)

        val args = Bundle()
        args.putInt(DialogFragment.EXTRA_DIALOG_CODE, dialogCode)
        title?.let { args.putInt(DialogFragment.EXTRA_TITLE, it) }
        message?.let { args.putInt(DialogFragment.EXTRA_MESSAGE, it) }
        positiveText?.let { args.putInt(DialogFragment.EXTRA_POSITIVE_TEXT, it) }
        negativeText?.let { args.putInt(DialogFragment.EXTRA_NEGATIVE_TEXT, it) }
        neutralText?.let { args.putInt(DialogFragment.EXTRA_NEUTRAL_TEXT, it) }
        fragment.arguments = args

        fragment.show(targetFragment.activity.fragmentManager, "dialog")
    }

    internal fun handleResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            DialogFragment.RESULT_POSITIVE -> positiveAction
            DialogFragment.RESULT_NEGATIVE -> negativeAction
            DialogFragment.RESULT_NEUTRAL -> neutralAction
            else -> null
        }?.invoke(data)
    }

}