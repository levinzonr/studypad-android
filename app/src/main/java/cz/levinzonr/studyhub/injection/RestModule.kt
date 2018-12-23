package cz.levinzonr.studyhub.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.levinzonr.studyhub.BuildConfig
import cz.levinzonr.studyhub.rest.utils.Api
import cz.levinzonr.studyhub.rest.utils.ItemTypeAdaperFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val rest = module {

    single { ItemTypeAdaperFactory() }


    // Okhttp
    single<OkHttpClient> {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = okhttp3.logging.HttpLoggingInterceptor()
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        clientBuilder.build()
    }


    single<Gson> {
        GsonBuilder()
            .registerTypeAdapterFactory(get())
            .registerTypeAdapter(Date::class.java, get())
            .create()
    }

    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { GsonConverterFactory.create(get()) }

    single<Api> {
        val retrofit : Retrofit = get()
        retrofit.create(Api::class.java)
    }

}