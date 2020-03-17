package ru.startandroid.widgets.testwidget1.config

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_test_widget1_config.*
import ru.startandroid.widgets.R
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment

class TestWidget1ConfigFragment : BaseWidgetConfigFragment<TestWidget1Config>() {

    override fun getLayoutId(): Int = R.layout.fragment_test_widget1_config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edittext.setText(getOriginalConfig().text)
        flag.isChecked = getOriginalConfig().flag
    }

    override fun getNewConfig(): WidgetConfig {
        return getOriginalConfig().copy(flag = flag.isChecked, text = edittext.text.toString())
    }

    override fun checkIfNewConfigIsValid(): Boolean {
        return true
    }

}
