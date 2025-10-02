package ru.practicum.android.diploma.vacancy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.domain.GetVacancyInteractor

const val ERROR_404 = 404
const val TWO_SECOND = 2000L

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val getVacancyInteractor: GetVacancyInteractor by inject()
    private var loadJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarVacancy.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        loadVacancy()
    }

    private fun loadVacancy() {
        showLoading()

        // Заглушка - через 2 секунды показываем случайное состояние
        binding.root.postDelayed({
            val randomState = (0..2).random() // 0, 1 или 2

            when (randomState) {
                0 -> showContent() // Успех
                1 -> showNoInternet() // Нет интернета
                2 -> showError(ERROR_404) // Ошибка 404
            }
        }, TWO_SECOND)
    }

    private fun showLoading() {
        binding.progressbar.isVisible = true
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = false
    }

    private fun showContent() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = true
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = false
    }

    private fun showError(errorCode: Int) {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false

        when (errorCode) {
            ERROR_404 -> {
                binding.placeholderVacancyNotFound.isVisible = true
                binding.placeholderServerError.isVisible = false
            }
            else -> {
                binding.placeholderServerError.isVisible = true
                binding.placeholderVacancyNotFound.isVisible = false
            }
        }
    }

    private fun showNoInternet() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = true
        binding.placeholderVacancyNotFound.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadJob?.cancel()
        _binding = null
    }
}
