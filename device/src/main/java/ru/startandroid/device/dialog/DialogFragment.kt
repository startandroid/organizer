package ru.startandroid.device.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.Fragment
import android.content.Intent
import android.os.Bundle

typealias AndroidDialogFragment = android.app.DialogFragment

class DialogFragment : AndroidDialogFragment() {

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

        fun newInstance(dialogCode: Int, config: DialogConfig?, targetFragment: Fragment): DialogFragment {
            val fragment = DialogFragment()
            fragment.setTargetFragment(targetFragment, dialogCode)

            val args = Bundle()
            fragment.arguments = args

            args.putInt(EXTRA_DIALOG_CODE, dialogCode)
            config?.run {
                title?.let { args.putInt(EXTRA_TITLE, it) }
                message?.let { args.putInt(EXTRA_MESSAGE, it) }
                positiveText?.let { args.putInt(EXTRA_POSITIVE_TEXT, it) }
                negativeText?.let { args.putInt(EXTRA_NEGATIVE_TEXT, it) }
                neutralText?.let { args.putInt(EXTRA_NEUTRAL_TEXT, it) }
            }

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogCode = arguments.getInt(EXTRA_DIALOG_CODE, 0)
        val builder = AlertDialog.Builder(activity)

        if (arguments.containsKey(EXTRA_TITLE)) {
            builder.setTitle(getTextFromArguments(EXTRA_TITLE))
        }

        builder.run {
            if (argumentExists(EXTRA_TITLE)) {
                setTitle(getTextFromArguments(EXTRA_TITLE))
            }
            if (argumentExists(EXTRA_MESSAGE)) {
                setMessage(getTextFromArguments(EXTRA_MESSAGE))
            }

            if (argumentExists(EXTRA_POSITIVE_TEXT)) {
                setPositiveButton(getTextFromArguments(EXTRA_POSITIVE_TEXT)) { _, _ ->
                    targetFragment.onActivityResult(dialogCode, RESULT_POSITIVE, Intent())
                }
            }

            if (argumentExists(EXTRA_NEGATIVE_TEXT)) {
                setNegativeButton(getTextFromArguments(EXTRA_NEGATIVE_TEXT)) { _, _ ->
                    targetFragment.onActivityResult(dialogCode, RESULT_NEGATIVE, Intent())
                }
            }

            if (argumentExists(EXTRA_NEUTRAL_TEXT)) {
                setNeutralButton(getTextFromArguments(EXTRA_NEUTRAL_TEXT)) { _, _ ->
                    targetFragment.onActivityResult(dialogCode, RESULT_NEUTRAL, Intent())
                }
            }
        }

        return builder.create()
    }

    private fun getTextFromArguments(key: String) = getString(arguments.getInt(key))

    private fun argumentExists(key: String) = arguments.containsKey(key)

}