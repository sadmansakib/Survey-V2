package com.example.survey.repository



import com.example.survey.db.SurveyCacheDao
import com.example.survey.model.db.SurveyCache
import com.example.survey.network.SurveyNetworkInterface
import javax.inject.Inject

class SurveyRepository @Inject constructor(
    private val surveyNetworkInterface: SurveyNetworkInterface,
    private val surveyCacheDao: SurveyCacheDao
) {
    suspend fun getSurveyFromNetwork() = surveyNetworkInterface.getSurvey()

    suspend fun saveFilledSurvey(surveyCache: SurveyCache) = surveyCacheDao.saveSurvey(surveyCache)

    fun seeAllSavedSurveys() = surveyCacheDao.seeAllSurvey()
}