package com.example.belissimo.utils

import android.app.Activity
import android.content.res.Configuration
import com.example.belissimo.data.PreferenceManager
import java.util.*


object LanguageUtils {

    fun setLocale(activity: Activity, languageCode: String, preferenceManager: PreferenceManager) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )

        // Optional: Save the selected language in shared preferences
        preferenceManager.saveLanguage(languageCode)

        // Restart the activity
        activity.recreate()
    }

}