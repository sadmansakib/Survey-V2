package com.example.survey.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.survey.model.db.SurveyCache

@Dao
interface SurveyCacheDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  saveSurvey(survey: SurveyCache)

    @Query("SELECT * FROM survey_table")
    fun seeAllSurvey(): LiveData<List<SurveyCache>>
}