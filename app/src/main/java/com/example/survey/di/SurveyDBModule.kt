package com.example.survey.di

import android.content.Context
import androidx.room.Room
import com.example.survey.db.SurveyCacheDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SurveyDBModule {
    @Singleton
    @Provides
    fun provideSurveyDatabase(
        @ApplicationContext app: Context
    ): SurveyCacheDataBase = Room.databaseBuilder(
        app, SurveyCacheDataBase::class.java, "survey_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideSurveyDao(db : SurveyCacheDataBase) = db.getSurveyCacheDao()
}