package ru.startandroid.organizer.fortest


import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test.*
import ru.startandroid.device.dialog.DialogConfig
import ru.startandroid.device.dialog.DialogHelper

import ru.startandroid.organizer.R

class TestFragment : Fragment() {

    val dialogHelper = DialogHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialogConfig = DialogConfig()
                .title(R.string.test_title_1)
                .message(R.string.test_message_1)
                .positive { Log.d("qweee", "positive1 action") }
                .negative { Log.d("qweee", "negative1 action") }
                .neutral { Log.d("qweee", "neutral1 action") }
        dialogHelper.registerDialogConfig(1, dialogConfig)

        val dialogConfig2 = DialogConfig()
                .title(R.string.test_title_2)
                .message(R.string.test_message_2)
                .positive { Log.d("qweee", "positive2 action") }
                .negative { Log.d("qweee", "negative2 action") }
                .neutral { Log.d("qweee", "neutral2 action") }
        dialogHelper.registerDialogConfig(2, dialogConfig2)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    var cnt = 0

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
        dialogHelper.onActivityResult(requestCode, resultCode, data)
        //Log.d("qweee", "onActivityResult request = $requestCode, result = $resultCode, data = $data")
    }

}
