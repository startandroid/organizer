package ru.startandroid.device.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import ru.startandroid.domain.ScopeApplication
import javax.inject.Inject

@ScopeApplication
class Analytics @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) {

    fun logEvent(event: Event) {
        firebaseAnalytics.logEvent(event.name, event.params)
    }

}