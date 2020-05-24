package ru.startandroid.organizer.exchange.presentation.di

import dagger.Component
import ru.startandroid.data.network.di.NetworkModule
import ru.startandroid.widgets.WidgetsModule
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider

/**
 * Used as a common component for all widgets. Main target is to avoid multiple dependencies like:
 * app -> widget1
 * app -> widget2
 * ...
 *
 * Instead there are dependencies:
 * app -> widgets
 * widgets -> widget1
 * widgets -> widget2
 */

@Component(modules = [WidgetsModule::class, NetworkModule::class])
interface WidgetComponent {
    fun provideWidgetMetadataProviders(): MutableSet<WidgetMetadataProvider>
}