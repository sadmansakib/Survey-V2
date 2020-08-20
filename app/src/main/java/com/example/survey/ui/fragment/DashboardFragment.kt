package com.example.survey.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.survey.R
import com.example.survey.ui.viewmodel.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val surveyViewModel : SurveyViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        surveyViewModel.savedSurveys.observe(viewLifecycleOwner, {
            survey_counter.text = "Total Submitted: ${it.count()}"
        })
    }
}