package ru.startandroid.device.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle

typealias AndroidDialogFragment = android.app.DialogFragment

class DialogFragment: AndroidDialogFragment() {

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_POSITIVE_TEXT = "positive_text"
        const val EXTRA_NEGATIVE_TEXT = "negative_text"
        const val EXTRA_NEUTRAL_TEXT = "neutral_text"
        const val EXTRA_DIALOG_CODE = "dialog_code"

        const val RESULT_POSITIVE = 1
        const val RESULT_NEGATIVE = 2
        const val RESULT_NEUTRAL = 3
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogCode = arguments.getInt(EXTRA_DIALOG_CODE, 0)
        val builder = AlertDialog.Builder(activity)

        if (arguments.containsKey(EXTRA_TITLE)) {
            builder.setTitle(getTextFromArguments(EXTRA_TITLE))
        }

        builder.run {

            ifArgumentExists(EXTRA_TITLE) { setTitle(getTextFromArguments(EXTRA_TITLE)) }

            ifArgumentExists(EXTRA_MESSAGE) { setMessage(getTextFromArguments(EXTRA_MESSAGE)) }

            ifArgumentExists(EXTRA_POSITIVE_TEXT) {
                setPositiveButton(getTextFromArguments(EXTRA_POSITIVE_TEXT)) { _, _ ->
                    targetFragment.onActivityResult(dialogCode, RESULT_POSITIVE, Intent())
                }
            }

            ifArgumentExists(EXTRA_NEGATIVE_TEXT) {
                setNegativeButton(getTextFromArguments(EXTRA_NEGATIVE_TEXT)) { _, _ ->
                    targetFragment.onActivityResult(dialogCode, RESULT_NEGATIVE, Intent())
                }
            }

            ifArgumentExists(EXTRA_NEUTRAL_TEXT) {
                setNeutralButton(getTextFromArguments(EXTRA_NEUTRAL_TEXT)) { _, _ ->
                    targetFragment.onActivityResult(dialogCode, RESULT_NEUTRAL, Intent())
                }
            }
        }

        return builder.create()
    }

    private fun getTextFromArguments(key: String) = getString(arguments.getInt(key))

    private fun ifArgumentExists(key: String, func: () -> Unit) {
        if (arguments.containsKey(key)) {
            func.invoke()
        }
    }

}