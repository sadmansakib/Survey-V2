package com.example.survey.ui.fragment


import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.survey.R
import com.example.survey.model.db.SurveyCache
import com.example.survey.model.network.SurveyResponse
import com.example.survey.model.network.SurveyResponseItem
import com.example.survey.ui.helper.*
import com.example.survey.ui.viewmodel.SurveyViewModel
import com.example.survey.util.DataState
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
        surveyViewModel.surveys.observe(viewLifecycleOwner, { surveyResponse ->
            when(surveyResponse){
                is DataState.Success ->{
                    hideProgressBar()
                    question_holder.visibility = VISIBLE
                    surveyResponse.data?.let {
                        createSurvey(it[position.value!!])
                        response.value = it
                        back_btn.isEnabled = false
                    }
                }

                is DataState.Error ->{
                    hideProgressBar()
                    question_holder.visibility = GONE
                    surveyResponse.message?.let { message ->
                        Timber.e("An error: $message")
                    }
                }

                is DataState.Loading -> {
                    question_holder.visibility = GONE
                    showProgressBar()
                }
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
                    surveyViewModel.saveSurveyResponse(SurveyCache(response.value !!))
                    Timber.d(SurveyCache(response.value !!).surveyResponse.toString())
                    findNavController().navigate(R.id.action_surveyFragment_to_dashboardFragment)
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

    private fun hideProgressBar() {
        surveyProgressBar.visibility = INVISIBLE
    }

    private fun showProgressBar() {
        surveyProgressBar.visibility = VISIBLE
    }

    private fun enableBackButton(){
        position.value?.let {
            if (it > 0){
                back_btn.isEnabled = true
            }
        }
    }


    private fun createSurvey(surveyResponseItem: SurveyResponseItem) {
        surveyResponseItem.question
        questions.text = surveyResponseItem.question
        option_holder.addView(
            when(surveyResponseItem.type){
                "text" -> {
                    option_holder.removeAllViews()
                    addEditText(InputTypeHelper.TEXT)
                }
                "number" -> {
                    option_holder.removeAllViews()
                    addEditText(InputTypeHelper.NUMBER)
                }
                "Checkbox" ->{
                    option_holder.removeAllViews()
                    addRadioButton(surveyResponseItem.options)
                }
                "dropdown" -> {
                    option_holder.removeAllViews()
                    addDropdown(surveyResponseItem.options)
                }
                "multiple choice" -> {
                    option_holder.removeAllViews()
                    addCheckbox(surveyResponseItem.options)
                }
                else ->{
                    option_holder.removeAllViews()
                    TextView(requireContext()).apply {
                        this.text = "No view found"
                    }
                }
            }
        )
    }
}




