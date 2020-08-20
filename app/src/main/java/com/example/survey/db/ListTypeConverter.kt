package com.example.survey.db

import androidx.room.TypeConverter
import com.example.survey.model.network.SurveyResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ListTypeConverter {
    @TypeConverter
    fun fromOptionValuesList(optionValues: SurveyResponse): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<SurveyResponse>() {}.type
        return gson.toJson(optionValues , type)
    }

    @TypeConverter // note this annotation
    fun toOptionValuesList(optionValuesString: String?): SurveyResponse? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<SurveyResponse>() {}.type
        return gson.fromJson<SurveyResponse>(optionValuesString , type)
    }
}