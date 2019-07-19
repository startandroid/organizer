package ru.startandroid.device.dialog

import android.app.*
import android.content.Intent
import android.os.Bundle

// TODO fix !! and check all ?

class DialogHelper {

    private val configs: MutableMap<Int, DialogConfig> = mutableMapOf()

    fun registerDialogConfig(dialogCode: Int, dialogConfig: DialogConfig) {
        configs[dialogCode] = dialogConfig
    }

    fun showDialog(dialogCode: Int, targetFragment: Fragment) {
        configs[dialogCode]?.show(dialogCode, targetFragment)
    }

    fun onActivityResult(dialogCode: Int, resultCode: Int, data: Intent?) {
        configs[dialogCode]?.handleResult(resultCode, data)
    }

}

