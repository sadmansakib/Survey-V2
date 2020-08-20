package com.example.survey.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.survey.model.db.SurveyCache
import com.example.survey.model.network.SurveyResponse
import com.example.survey.repository.SurveyRepository
import com.example.survey.util.DataState
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class SurveyViewModel @ViewModelInject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {

   val surveys: MutableLiveData<DataState<SurveyResponse>> = MutableLiveData()
   val savedSurveys = MediatorLiveData<List<SurveyCache>>()

    init {
        getSurveys()
        savedSurveys.addSource(surveyRepository.seeAllSavedSurveys()){result ->
            result?.let { savedSurveys.value = it }
        }
        Timber.d(savedSurveys.toString())
    }

    private fun getSurveys() = viewModelScope.launch {
        surveys.postValue(DataState.Loading())
        val response = surveyRepository.getSurveyFromNetwork()
        surveys.postValue(handleSurveyResponse(response))
    }

    fun saveSurveyResponse(response: SurveyCache) = viewModelScope.launch {
        surveyRepository.saveFilledSurvey(response)
    }

    private fun handleSurveyResponse(response: Response<SurveyResponse>) : DataState<SurveyResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return DataState.Success(resultResponse)
            }
        }
        return DataState.Error(response.message())
    }
}