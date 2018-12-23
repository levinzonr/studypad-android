package cz.levinzonr.studyhub.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import cz.levinzonr.studyhub.BuildConfig
import cz.levinzonr.studyhub.rest.utils.Api
import cz.levinzonr.studyhub.rest.utils.ItemTypeAdaperFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val rest = module {

    single<TypeAdapterFactory> { ItemTypeAdaperFactory() }


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
            .create()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(get())
    }

    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single { GsonConverterFactory.create(get()) }

    single<Api> {
        get<Retrofit>().create<Api>(Api::class.java)
    }

}