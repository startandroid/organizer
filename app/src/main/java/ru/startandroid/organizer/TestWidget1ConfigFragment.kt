package ru.startandroid.organizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.startandroid.organizer.home.widget.TestWidget1Config
import ru.startandroid.widgets.WidgetConfig
import kotlinx.android.synthetic.main.fragment_test_widget1_config.*


class TestWidget1ConfigFragment : BaseWidgetConfigFragment<TestWidget1Config>() {

    override fun getLayoutId(): Int = R.layout.fragment_test_widget1_config

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edittext.setText(getOriginalConfig().text)
        flag.isChecked = getOriginalConfig().flag
    }

    override fun getNewConfig(): WidgetConfig {
        return getOriginalConfig().copy(flag = flag.isChecked, text = edittext.text.toString())
    }

}
