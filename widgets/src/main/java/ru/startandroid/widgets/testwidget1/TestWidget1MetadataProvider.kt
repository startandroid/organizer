package ru.startandroid.widgets.testwidget1

import ru.startandroid.organizer.home.widget.TestWidget1Content
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_1
import ru.startandroid.widgets.testwidget1.config.TestWidget1Config
import ru.startandroid.widgets.testwidget1.config.TestWidget1ConfigFragment
import ru.startandroid.widgets.testwidget1.content.TestWidget1Data
import ru.startandroid.widgets.testwidget1.update.TestWidget1DbDataHelper
import ru.startandroid.widgetsbase.data.metadata.*
import javax.inject.Inject

class TestWidget1MetadataProvider @Inject constructor (): WidgetMetadataProvider {

    override fun getWidgetId(): Int = TEST_WIDGET_1

    override fun provideMetadata(): WidgetMetadata {
        return metadata {

            details {
                titleResId = R.string.test_widget_1_title
                descriptionResId = R.string.test_widget_1_description
            }

            content {
                widgetDataCls = TestWidget1Data::class
                widgetContent = { TestWidget1Content() }
            }

            header {
                refreshButtonIsVisible = true
                configButtonIsVisible = true
                closeButtonIsVisible = true
            }

            config {
                widgetConfigCls = TestWidget1Config::class
                widgetConfigFragment = { TestWidget1ConfigFragment() }
            }

            update {
                autoRefresh = false
                needsInternet = false
                widgetRefresher = { TestWidget1DbDataHelper() }
            }
        }
    }
}
