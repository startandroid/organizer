package ru.startandroid.device.dialog

import android.app.Fragment
import android.content.Intent
import javax.inject.Inject

class DialogHelper @Inject constructor() {

    private val configs: MutableMap<Int, DialogConfig> = mutableMapOf()

    fun registerDialogConfig(dialogCode: Int, dialogConfig: DialogConfig) {
        configs[dialogCode] = dialogConfig
    }

    fun showDialog(dialogCode: Int, targetFragment: Fragment) {
        DialogFragment.newInstance(dialogCode, configs[dialogCode], targetFragment)
                .show(targetFragment.activity.fragmentManager, "dialog")
    }

    fun handleResult(dialogCode: Int, resultCode: Int, data: Intent?) {
        configs[dialogCode]?.run {
            when (resultCode) {
                DialogFragment.RESULT_POSITIVE -> positiveAction
                DialogFragment.RESULT_NEGATIVE -> negativeAction
                DialogFragment.RESULT_NEUTRAL -> neutralAction
                else -> null
            }?.invoke(data)
        }
    }

}

