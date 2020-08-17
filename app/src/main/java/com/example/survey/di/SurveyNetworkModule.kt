package com.example.survey.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SurveyNetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://example-response.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideSurveyNetwork(retrofit: Retrofit.Builder): SurveyNetworkModule{
        return retrofit.build().create(SurveyNetworkModule::class.java)
    }
}