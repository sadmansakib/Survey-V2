package com.example.survey.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.survey.model.network.SurveyResponse
import com.example.survey.model.network.SurveyResponseItem

@Entity(tableName = "survey_table")
data class SurveyCache(
   var surveyResponse: SurveyResponse
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}