package ru.startandroid.organizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test_widget2_config.*
import ru.startandroid.organizer.home.widget.TestWidget2Config
import ru.startandroid.widgets.WidgetConfig

class TestWidget2ConfigFragment : BaseWidgetConfigFragment<TestWidget2Config>() {


    override fun getLayoutId(): Int = R.layout.fragment_test_widget2_config

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flag1.isChecked = getOriginalConfig().flag1
        flag2.isChecked = getOriginalConfig().flag2

    }

    override fun getNewConfig(): WidgetConfig {
        return getOriginalConfig().copy(flag1 = flag1.isChecked, flag2 = flag2.isChecked)
    }

}
