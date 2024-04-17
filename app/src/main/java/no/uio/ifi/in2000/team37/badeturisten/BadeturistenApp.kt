package no.uio.ifi.in2000.team37.badeturisten

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BadeturistenApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}