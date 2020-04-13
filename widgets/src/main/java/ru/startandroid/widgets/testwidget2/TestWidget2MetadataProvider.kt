package ru.startandroid.widgets.testwidget2

import ru.startandroid.organizer.home.widget.TestWidget2Content
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.widgets.testwidget2.config.TestWidget2Config
import ru.startandroid.widgets.testwidget2.config.TestWidget2ConfigFragment
import ru.startandroid.widgets.testwidget2.content.TestWidget2Data
import ru.startandroid.widgets.testwidget2.update.TestWidget2Correct
import ru.startandroid.widgets.testwidget2.update.TestWidget2Refresh
import ru.startandroid.widgetsbase.data.db.model.UpdateInterval
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider
import ru.startandroid.widgetsbase.data.metadata.metadata
import ru.startandroid.widgetsbase.domain.model.WidgetMainConfig
import javax.inject.Inject

class TestWidget2MetadataProvider @Inject constructor() : WidgetMetadataProvider {

    override fun getWidgetId(): Int = TEST_WIDGET_2

    override fun provideMetadata(): WidgetMetadata {
        return metadata {

            details {
                titleResId = R.string.test_widget_2_title
                descriptionResId = R.string.test_widget_2_description
            }

            content {
                widgetDataCls = TestWidget2Data::class
                widgetContent = TestWidget2Content()
                initWidgetData = TestWidget2Data("test1", text2 = "test2")
            }

            header {
                refreshButtonIsVisible = true
                configButtonIsVisible = true
                closeButtonIsVisible = true
            }

            config {
                widgetConfigCls = TestWidget2Config::class
                widgetConfigFragment = { TestWidget2ConfigFragment() }
                initWidgetConfig = TestWidget2Config(true, false)
                initWidgetMainConfig = WidgetMainConfig(true, UpdateInterval.MINUTES_15)
            }

            update {
                needsInternet = false
                widgetRefresh = TestWidget2Refresh()
                widgetCorrect = TestWidget2Correct()
            }
        }
    }
}