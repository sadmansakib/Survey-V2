package com.example.survey.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.survey.R
import com.example.survey.model.SurveyResponse
import com.example.survey.model.SurveyResponseItem
import com.example.survey.ui.helper.*
import com.example.survey.ui.viewmodel.SurveyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_survey.*
import timber.log.Timber

@AndroidEntryPoint
class SurveyFragment : Fragment(R.layout.fragment_survey) {

    private var position = MutableLiveData<Int>()
    private val surveyViewModel: SurveyViewModel by viewModels()
    private var response = MutableLiveData<SurveyResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position.postValue(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surveyViewModel.surveys.observe(viewLifecycleOwner, Observer {SurveyResponse ->
            SurveyResponse?.let {
                createSurvey(it[position.value!!])
                response.value = it
                Timber.d(response.value.toString())
                back_btn.isEnabled = false
            }
        })


        next_btn.setOnClickListener {
            position.value = position.value!!.plus(1)
            response.value?.get(position.value!!)?.let { it1 -> createSurvey(it1) }
            enableBackButton()
            Timber.d(position.value.toString())
            if (position.value == response.value!!.size-1){
                next_btn.text = getString(R.string.submit_btn_name)
                it.setOnClickListener {
                    findNavController().navigate(R.id.dashboardFragment)
                }
            }
        }

        back_btn.setOnClickListener {
            position.value = position.value !!.minus(1)
            response.value?.get(position.value!!)?.let { it1 ->
                createSurvey(it1)}
            if (position.value == 0){
                back_btn.isEnabled = false
            }

            }
        }


    private fun enableBackButton(){
        position.value?.let {
            if (it > 0){
                back_btn.isEnabled = true
            }
        }
    }


    private fun createSurvey(surveyResponseItem: SurveyResponseItem) {
        questions.text = surveyResponseItem.question
        when(surveyResponseItem.type){
            "text" -> addEditText(option_holder, InputTypeHelper.TEXT)
            "number" -> addEditText(option_holder, InputTypeHelper.NUMBER)
            "Checkbox" -> addRadioButton(option_holder, surveyResponseItem.options)
            "dropdown" -> addDropdown(option_holder, surveyResponseItem.options)
            "multiple choice" -> addCheckbox(option_holder,surveyResponseItem.options)
        }
    }

}


