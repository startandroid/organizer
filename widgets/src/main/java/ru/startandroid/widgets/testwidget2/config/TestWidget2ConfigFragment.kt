package ru.startandroid.widgets.testwidget2.config

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_test_widget2_config.*
import ru.startandroid.widgets.R
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment

class TestWidget2ConfigFragment : BaseWidgetConfigFragment<TestWidget2Config>() {

    override fun getLayoutId(): Int = R.layout.fragment_test_widget2_config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flag1.isChecked = getOriginalConfig().flag1
        flag2.isChecked = getOriginalConfig().flag2
    }

    override fun fillNewConfig(): WidgetConfig {
        return getOriginalConfig().copy(flag1 = flag1.isChecked, flag2 = flag2.isChecked)
    }

    override fun checkIfNewConfigIsValid(): Boolean {
        return true
    }
}
