package ru.startandroid.device.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat

/*

Quiet SwitchCompat. Based on usual SwitchCompat, but adds one method: setCheckedQuiet.
When using this method to set checked value, listener will not called.

Might be useful for programmatic setting of checked value.

 */

class QuietSwitchCompat : SwitchCompat {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var lastListener: OnCheckedChangeListener? = null

    override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        super.setOnCheckedChangeListener(listener)
        lastListener = listener
    }

    fun setCheckedQuiet(chckd: Boolean) {
        super.setOnCheckedChangeListener(null)
        isChecked = chckd
        super.setOnCheckedChangeListener(lastListener)
    }

}