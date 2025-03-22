package ru.antonlm.data.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.antonlm.data.data.prefs.PreferenceHelper.defaultPrefs
import ru.antonlm.data.data.prefs.PreferenceStorage
import ru.antonlm.data.data.remote.ApiService
import ru.antonlm.data.data.remote.ApiService.Companion.BASE_URL
import ru.antonlm.data.data.remote.ApiService.Companion.CONNECTION_TIMEOUT
import ru.antonlm.data.data.utils.NetworkResultCallAdapterFactory
import ru.antonlm.data.mock.ApiServiceMock
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Room DataBase, Retrofit ApiService, SharedPreference and etc.
 */
@Module
internal class DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        client: OkHttpClient,
        gson: Gson
    ): ApiService {
        return ApiServiceMock()
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    /** Local */
    @Provides
    @Singleton
    fun providePreferenceStorage(context: Application, gson: Gson): PreferenceStorage {
        return PreferenceStorage(defaultPrefs(context), gson)
    }
}
