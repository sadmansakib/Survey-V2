package com.example.survey.model

data class SurveyResponseItem(
    val options: String,
    val question: String,
    val required: Boolean,
    val type: String
)