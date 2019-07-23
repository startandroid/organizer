package ru.startandroid.organizer.fortest


import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_test.*
import ru.startandroid.device.dialog.DialogConfig
import ru.startandroid.device.dialog.DialogHelper

import ru.startandroid.organizer.R
import javax.inject.Inject

class TestFragment : Fragment() {

    @Inject
    lateinit var dialogHelper: DialogHelper

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerDialogs()
    }

    private fun registerDialogs() {
        val dialogConfig = DialogConfig()
                .title(R.string.test_title_1)
                .message(R.string.test_message_1)
                .positive(R.string.dialog_yes) { Log.d("qweee", "positive action") }
                .negative(R.string.dialog_no) { Log.d("qweee", "negative action") }
                .neutral(R.string.dialog_cancel) { Log.d("qweee", "neutral action") }
        dialogHelper.registerDialogConfig(1, dialogConfig)

        val dialogConfig2 = DialogConfig()
                .title(R.string.test_title_2)
                .message(R.string.test_message_2)
                .positive(R.string.dialog_yes) { Log.d("qweee", "positive2 action") }
                .negative(R.string.dialog_no) { Log.d("qweee", "negative2 action") }
                .neutral(R.string.dialog_cancel) { Log.d("qweee", "neutral2 action") }
        dialogHelper.registerDialogConfig(2, dialogConfig2)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    private var cnt = 0

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            cnt++
            dialogHelper.showDialog(cnt % 2 + 1, this)
            Log.d("qweee", "showdialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dialogHelper.handleResult(requestCode, resultCode, data)
    }

}
