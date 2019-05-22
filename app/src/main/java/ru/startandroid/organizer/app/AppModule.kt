package ru.startandroid.organizer.app

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.organizer.home.widget.TestWidget1RegisterData
import ru.startandroid.organizer.home.widget.TestWidget2RegisterData
import ru.startandroid.organizer.home.widget.widgets.WeatherWidgetRegisterData
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @ScopeApplication
    @Provides
    fun provideGson(): Gson = Gson()


    // TODO move to another module
    @Provides
    fun provideWidgetsData(
            testWidget1RegisterData: TestWidget1RegisterData,
            testWidget2RegisterData: TestWidget2RegisterData,
            weatherWidgetRegisterData: WeatherWidgetRegisterData
    ): MutableSet<WidgetRegistratorImpl.RegisterData> {
        return mutableSetOf(testWidget1RegisterData,
                testWidget2RegisterData,
                weatherWidgetRegisterData)
    }
}
