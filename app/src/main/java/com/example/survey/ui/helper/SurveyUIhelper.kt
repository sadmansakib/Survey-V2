package com.example.survey.ui.helper

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.survey.ui.fragment.SurveyFragment
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_survey.*
import timber.log.Timber

fun SurveyFragment.addEditText(optionHolder: MaterialCardView?, type: InputTypeHelper) = when(type){
    InputTypeHelper.TEXT -> {
        optionHolder?.removeAllViews()
        val editText = EditText(requireContext()).also { editText ->
            editText.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT ,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            editText.maxLines = 4
            next_btn.isEnabled = false
        }.apply {

            addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence? , p1: Int , p2: Int , p3: Int) {
                    Timber.d("Text Changed")
                    next_btn.isEnabled = p0.toString().trim().isNotEmpty()
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
        optionHolder !!.addView(editText)
    }

    InputTypeHelper.NUMBER -> {
        optionHolder?.removeAllViews()
        val editText = EditText(requireContext()).also {
            it.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }
        optionHolder!!.addView(editText)
    }
}

fun SurveyFragment.addCheckbox(optionHolder: MaterialCardView?, options: String) {
    optionHolder?.removeAllViews()
    next_btn.isEnabled = false
    val optionArray = options.split(',')
    val linearLayout = LinearLayout(requireContext()).also {
        it.orientation = LinearLayout.VERTICAL
    }
    for(i in optionArray){
        val checkBox = CheckBox(requireContext()).also {
            it.text = i
            it.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            it.setOnCheckedChangeListener { button , _ ->
                button?.let {
                    Timber.d((button.isChecked).toString())
                    next_btn.isEnabled = button.isChecked
                }
            }
        }
        linearLayout.addView(checkBox)
    }


    optionHolder!!.addView(linearLayout)
}

fun SurveyFragment.addRadioButton(optionHolder: MaterialCardView?, option: String) {
    optionHolder?.removeAllViews()
    next_btn.isEnabled = false
    val radioGroup = RadioGroup(requireContext())
    for (i in option.split(',')){
        val radioButton = RadioButton(requireContext()).also {
            it.id = View.generateViewId()
            it.text = i
        }
        radioGroup.addView(radioButton)
    }

    radioGroup.setOnCheckedChangeListener(fun(radioGroup: RadioGroup , i: Int) = radioGroup.let {
        next_btn.isEnabled = it.checkedRadioButtonId == i
    })

    optionHolder!!.addView(radioGroup)
}

fun SurveyFragment.addDropdown(optionHolder: MaterialCardView?, options: String) {
    optionHolder?.removeAllViews()
    val list = options.split(',')
    val arrayList = ArrayList<String>().apply {
        add("None")
        addAll(list)
    }
    val spinner = Spinner(requireContext()).also {
        it.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayList)
        it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>? , p1: View? , p2: Int , p3: Long) {
                if (p0 != null) {
                    Timber.d(p0.selectedItem.toString().trim())
                    this@addDropdown.next_btn.isEnabled = p0.selectedItem.toString().trim() != "None"
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Timber.d(" nothing selected on spinner")
            }
        }
    }
    optionHolder!!.addView(spinner)
}