package com.megaache.composernotepad

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}