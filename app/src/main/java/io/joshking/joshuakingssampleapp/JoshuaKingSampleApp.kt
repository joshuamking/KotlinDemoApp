package io.joshking.joshuakingssampleapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate

@Suppress("unused")
class JoshuaKingSampleApp : Application() {
    companion object {
        init {
            // Enables Night/Day mode for app.
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}