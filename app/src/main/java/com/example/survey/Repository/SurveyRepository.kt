package com.example.survey.Repository

import com.example.survey.network.SurveyNetworkInterface
import javax.inject.Inject

class SurveyRepository @Inject constructor(
    private val surveyNetworkInterface: SurveyNetworkInterface
) {
    suspend fun getSurveyFromNetwork() = surveyNetworkInterface.getSurvey()
}