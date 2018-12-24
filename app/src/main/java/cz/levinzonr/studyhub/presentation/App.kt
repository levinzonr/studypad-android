package cz.levinzonr.studyhub.presentation

import android.app.Application
import android.util.Log
import com.facebook.FacebookSdk
import cz.levinzonr.studyhub.injection.appModule
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