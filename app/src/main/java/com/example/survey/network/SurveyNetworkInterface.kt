package com.example.survey.network

import com.example.survey.model.SurveyResponse
import retrofit2.http.GET

interface SurveyNetworkInterface {
    @GET("getSurvey")
    suspend fun getSurvey():SurveyResponse
}