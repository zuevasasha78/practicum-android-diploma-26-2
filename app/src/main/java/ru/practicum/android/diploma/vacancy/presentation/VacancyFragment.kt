package ru.practicum.android.diploma.vacancy.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.domain.VacancyState
import ru.practicum.android.diploma.vacancy.domain.model.VacancyModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R


class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyViewModel by viewModel()
    private var currentVacancy: VacancyModel? = null

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

        setupObservers()
        loadVacancy()
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyState.Loading -> showLoading()
                is VacancyState.Content -> {
                    showContent()
                    currentVacancy = state.vacancy
                    bindVacancyData(state.vacancy)
                }
                is VacancyState.NoInternet -> showNoInternet()
                is VacancyState.ServerError -> showError()
                is VacancyState.VacancyNotFound -> showVacancyNotFound()
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteIcon(isFavorite)
        }
    }

    private fun loadVacancy() {
        val vacancyId = arguments?.getString(ARG_VACANCY_ID) ?: ""
        viewModel.loadVacancy(vacancyId)
    }

    private fun bindVacancyData(vacancy: VacancyModel) {
        binding.vacancyName.text = vacancy.name
        binding.vacancyPayment.text = vacancy.salary ?: "Зарплата не указана"
        binding.employerName.text = vacancy.employerName
        binding.area.text = vacancy.area
        binding.experience.text = vacancy.experience ?: "Не указан"
        binding.employmentType.text = vacancy.employment ?: "Не указан"

        Glide.with(this).load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.employer_logo_placeholder)
            .into(binding.employerLogo)

        binding.vacancyResponsibilities.text = vacancy.responsibilities ?: "Не указаны"
        binding.vacancyRequirements.text = vacancy.requirements ?: "Не указаны"
        binding.vacancyConditions.text = vacancy.conditions ?: "Не указаны"
        binding.vacancySkills.text = vacancy.skills.joinToString("\n") { "• $it" }

        setupClickListeners(vacancy)
    }

    private fun setupClickListeners(vacancy: VacancyModel) {
        binding.favorite.setOnClickListener {
            lifecycleScope.launch {
                viewModel.toggleFavorite(vacancy.id, vacancy)
            }
        }

        binding.share.setOnClickListener {
            val shareContent = viewModel.prepareShareContent(vacancy)
            shareVacancy(shareContent)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favorite.setBackgroundResource(R.drawable.icon_favorite_on)
        } else {
            binding.favorite.setBackgroundResource(R.drawable.icon_favorite_off)
        }
    }

    private fun shareVacancy(shareContent: String) {
        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            putExtra(android.content.Intent.EXTRA_TEXT, shareContent)
            type = "text/plain"
        }
        startActivity(android.content.Intent.createChooser(shareIntent, "Поделиться вакансией"))
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

    private fun showError() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = true
        binding.placeholderVacancyNotFound.isVisible = false
    }

    private fun showVacancyNotFound() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = true
    }

    private fun showNoInternet() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = true
        binding.placeholderVacancyNotFound.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_VACANCY_ID = "vacancy_id"
    }
}
