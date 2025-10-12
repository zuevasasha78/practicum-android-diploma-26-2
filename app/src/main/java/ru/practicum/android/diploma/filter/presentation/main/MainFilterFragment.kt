package ru.practicum.android.diploma.filter.presentation.main

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.bundle.bundleOf
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainFilterBinding
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.COUNTRY_NAME
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.REGION_NAME

class MainFilterFragment : Fragment() {
    private var _binding: FragmentMainFilterBinding? = null
    private val binding get() = _binding!!
    private val mainFilterViewModel by viewModel<MainFilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarFilter.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setListeners()
        mainFilterViewModel.filters.observe(viewLifecycleOwner) { filterUiState ->
            render(filterUiState)
        }

        setPlaceListeners()
        setIndustryListeners()

        binding.salaryEditText.setOnFocusChangeListener { _, _ ->
            updateSalaryField()
        }

        binding.root.setOnClickListener {
            hideKeyboardAndClearFocus()
        }

        binding.salaryEditText.addTextChangedListener { text ->
            val textString = text.toString()
            if (textString != mainFilterViewModel.filters.value?.salary) {
                mainFilterViewModel.setSalary(textString)
            }
        }

        binding.salaryInputLayout.setEndIconOnClickListener {
            mainFilterViewModel.setSalary("")
        }

        binding.onlyWithSalaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            mainFilterViewModel.setOnlyWithSalary(isChecked)
        }

        binding.applyButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFilterFragment_to_searchFragment
            )
        }

        binding.resetButton.setOnClickListener {
            resetFilter()
        }
    }

    private fun setIndustryListeners() {
        binding.industryEditText.setOnClickListener {
            // Заглушка для теста, удалится после реализации экрана "Отрасль"
            val text = "IT"
            mainFilterViewModel.setIndustry(text)
            // findNavController().navigate(
            //     R.id.action_mainFilterFragment_to_chooserFragment,
            //     bundleOf(ARG_NAME to ChooserType.SectorType),
            // )
        }

        binding.industryInputLayout.setEndIconOnClickListener {
            mainFilterViewModel.setIndustry("")
        }
    }

    private fun setPlaceListeners() {
        binding.placeEditText.setOnClickListener {
            // todo заменить на значение из сохраненного фильтра
            val countryName = null
            val regionName = null
            val args = bundleOf(
                COUNTRY_NAME to countryName,
                REGION_NAME to regionName,
            )
            findNavController().navigate(
                R.id.workplaceFragment,
                args
            )
        }

        binding.placeInputLayout.setEndIconOnClickListener {
            mainFilterViewModel.setPlace("")
        }
    }

    private fun setListeners() {
        setFragmentResultListener(PLACE_REQUEST_KEY) { _, bundle ->
            val place = bundle.getString(PLACE_RESULT_KEY).orEmpty()
            mainFilterViewModel.setPlace(place)
        }

        setFragmentResultListener(INDUSTRY_REQUEST_KEY) { _, bundle ->
            val industry = bundle.getString(INDUSTRY_RESULT_KEY).orEmpty()
            mainFilterViewModel.setIndustry(industry)
        }
    }

    private fun updateFieldState(textInputLayout: TextInputLayout, hasText: Boolean) {
        val colorResId = if (hasText) R.color.text_color else R.color.hint_color_filter_screen
        textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), colorResId)
        )
        val iconRes = if (hasText) R.drawable.icon_close else R.drawable.leading_icon
        textInputLayout.setEndIconDrawable(iconRes)
    }

    private fun updateSalaryField() {
        val hasFocus = binding.salaryEditText.hasFocus()
        val hasText = binding.salaryEditText.text?.isNotEmpty() == true
        val colorResId = when {
            hasFocus -> R.color.blue // При фокусе - синий
            hasText -> R.color.black_universal // Заполнено без фокуса - черный
            else -> R.color.salary_text_hint // Пустое без фокуса - серый/белый в зависимости от темы
        }
        binding.salaryInputLayout.defaultHintTextColor = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), colorResId)
        )

        binding.salaryInputLayout.setEndIconDrawable(R.drawable.icon_close)
        binding.salaryInputLayout.isEndIconVisible = hasText
    }

    private fun hideKeyboardAndClearFocus() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        binding.salaryEditText.clearFocus()
    }

    private fun isButtonsApplyAndResetVisible(isVisible: Boolean) {
        binding.applyButton.isVisible = isVisible
        binding.resetButton.isVisible = isVisible
    }

    private fun render(filterUIState: FilterUiState) {
        binding.placeEditText.setText(filterUIState.place)
        updateFieldState(binding.placeInputLayout, filterUIState.place.isNotEmpty())

        binding.industryEditText.setText(filterUIState.industry)
        updateFieldState(binding.industryInputLayout, filterUIState.industry.isNotEmpty())

        if (binding.salaryEditText.text.toString() != filterUIState.salary) {
            binding.salaryEditText.setText(filterUIState.salary)
        }
        updateSalaryField()

        binding.onlyWithSalaryCheckbox.isChecked = filterUIState.onlyWithSalary
        isButtonsApplyAndResetVisible(filterUIState.hasAnyFilter)
    }

    private fun resetFilter() {
        mainFilterViewModel.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PLACE_REQUEST_KEY = "place_request"
        const val PLACE_RESULT_KEY = "place"

        const val INDUSTRY_REQUEST_KEY = "industry_request"
        const val INDUSTRY_RESULT_KEY = "industry"
    }
}
