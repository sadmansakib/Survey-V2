package com.example.survey.network

import com.example.survey.model.network.SurveyResponse
import retrofit2.Response
import retrofit2.http.GET

interface SurveyNetworkInterface {
    @GET("getSurvey")
    suspend fun getSurvey(): Response<SurveyResponse>
}