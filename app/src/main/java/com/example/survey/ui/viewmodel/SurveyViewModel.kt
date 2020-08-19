package com.example.survey.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.survey.model.SurveyResponse
import com.example.survey.repository.SurveyRepository
import kotlinx.coroutines.launch

class SurveyViewModel @ViewModelInject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {
   val surveys: MutableLiveData<SurveyResponse> = MutableLiveData()

    init {
        getSurveys()
    }

    private fun getSurveys() = viewModelScope.launch {
        surveys.value = surveyRepository.getSurveyFromNetwork()
    }
}