package com.example.survey.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.survey.model.db.SurveyCache

@Database(
    entities = [SurveyCache::class],
    version = 1
)
@TypeConverters(ListTypeConverter::class)
abstract class SurveyCacheDataBase : RoomDatabase() {

    abstract fun getSurveyCacheDao(): SurveyCacheDao
}