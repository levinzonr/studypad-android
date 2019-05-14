package cz.levinzonr.studypad.presentation

import android.app.Application
import com.facebook.FacebookSdk
import cz.levinzonr.studypad.BuildConfig
import cz.levinzonr.studypad.injection.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin(this, appModule)

        // Enable Logs if DEBUG Mode
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        // Init Facebook SDK
        FacebookSdk.sdkInitialize(this)
    }

}