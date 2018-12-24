package cz.levinzonr.studypad.presentation

import android.app.Application
import com.facebook.FacebookSdk
import cz.levinzonr.studypad.injection.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModule)
        Timber.plant(Timber.DebugTree())
        FacebookSdk.sdkInitialize(this)
    }

}