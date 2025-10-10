package ru.practicum.android.diploma.filter.presentation.main

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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

        setFragmentResultListener(PLACE_REQUEST_KEY) { _, bundle ->
            val place = bundle.getString(PLACE_RESULT_KEY).orEmpty()
            mainFilterViewModel.setPlace(place)
            updateFieldState(binding.placeInputLayout, place.isNotEmpty())
        }

        setFragmentResultListener(INDUSTRY_REQUEST_KEY) { _, bundle ->
            val industry = bundle.getString(INDUSTRY_RESULT_KEY).orEmpty()
            mainFilterViewModel.setIndustry(industry)
            updateFieldState(binding.industryInputLayout, industry.isNotEmpty())
        }

        mainFilterViewModel.filters.observe(viewLifecycleOwner) { filterUiState ->
            render(filterUiState)
        }

        binding.placeEditText.setOnClickListener {
            // Заглушка для теста, удалится после реализации экрана "Место работы"
            val text = "Москва"
            mainFilterViewModel.setPlace(text)
            binding.placeInputLayout.apply {
                updateFieldState(this, true)
            }
            // Удалить с 66 до 71 строки включительно, нижнюю раскоментить
            // findNavController().navigate(R.id.action_mainFilterFragment_to_workPlaceFragment)
        }

        binding.placeInputLayout.setEndIconOnClickListener {
            mainFilterViewModel.setPlace("")
            binding.placeInputLayout.apply {
                updateFieldState(this, false)
            }
        }

        binding.industryEditText.setOnClickListener {
            // Заглушка для теста, удалится после реализации экрана "Отрасль"
            val text = "IT"
            mainFilterViewModel.setIndustry(text)
            binding.industryInputLayout.apply {
                updateFieldState(this, true)
            }
            // Удалить с 84 до 89 строки включительно, нижние раскоментить
//            findNavController().navigate(
//                R.id.action_mainFilterFragment_to_chooserFragment,
//                bundleOf(ARG_NAME to ChooserType.SectorType),
//            )
        }

        binding.industryInputLayout.setEndIconOnClickListener {
            mainFilterViewModel.setIndustry("")
            binding.industryInputLayout.apply {
                updateFieldState(this, false)
            }
        }

        binding.salaryEditText.setOnFocusChangeListener { _, _ ->
            updateSalaryField()
        }

        binding.root.setOnClickListener {
            hideKeyboardAndClearFocus()
        }

        binding.salaryEditText.addTextChangedListener { text ->
            val textString = text.toString()
            if (textString != mainFilterViewModel.filters.value?.salary) {
                updateSalaryField()
                mainFilterViewModel.setSalary(textString)
            }
        }

        binding.salaryInputLayout.setEndIconOnClickListener {
            mainFilterViewModel.setSalary("")
            updateSalaryField()
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
        binding.industryEditText.setText(filterUIState.industry)
        if (binding.salaryEditText.text.toString() != filterUIState.salary) {
            binding.salaryEditText.setText(filterUIState.salary)
        }
        binding.onlyWithSalaryCheckbox.isChecked = filterUIState.onlyWithSalary
        isButtonsApplyAndResetVisible(filterUIState.hasAnyFilter)
    }

    private fun resetFilter() {
        mainFilterViewModel.reset()
        updateFieldState(binding.placeInputLayout, hasText = false)
        updateFieldState(binding.industryInputLayout, hasText = false)
        updateSalaryField()
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
