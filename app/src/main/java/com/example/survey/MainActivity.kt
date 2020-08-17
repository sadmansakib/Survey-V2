package com.example.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.survey.Repository.SurveyRepository
import com.example.survey.network.SurveyNetworkInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var surveyRepository: SurveyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val response = surveyRepository.getSurveyFromNetwork()
            Timber.d(response.toString())
        }

    }
}