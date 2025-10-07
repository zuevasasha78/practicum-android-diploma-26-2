package ru.practicum.android.diploma.vacancy.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.utils.StringUtils
import ru.practicum.android.diploma.vacancy.domain.VacancyState

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyViewModel by viewModel()
    private val stringUtils: StringUtils by inject()
    private var currentVacancy: VacancyDetail? = null

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
        val vacancyId = arguments?.getString(ARG_NAME) ?: ""
        viewModel.loadVacancy(vacancyId)
    }

    private fun bindVacancyData(vacancy: VacancyDetail) {
        binding.vacancyName.text = vacancy.name
        binding.vacancyPayment.text = stringUtils.getSalaryString(vacancy.salary)
        binding.employerName.text = vacancy.employerName
        binding.experience.text = vacancy.experience
        binding.employmentType.text = vacancy.employment
        binding.area.text = vacancy.address ?: vacancy.area
        Glide.with(this)
            .load(vacancy.employerLogoUrl)
            .placeholder(R.drawable.empty_placeholder)
            .into(binding.employerLogo)
        binding.vacancyResponsibilities.text = vacancy.responsibilities
        binding.vacancyRequirements.text = vacancy.requirements
        binding.vacancyConditions.text = vacancy.conditions

        setupSkillsSection(vacancy)

        setupContactSection(vacancy)

        setupClickListeners(vacancy)
    }

    private fun setupSkillsSection(vacancy: VacancyDetail) {
        val hasSkills = vacancy.skills.isNotEmpty()

        binding.titleVacancySkills.isVisible = hasSkills
        binding.vacancySkills.isVisible = hasSkills

        if (hasSkills) {
            binding.vacancySkills.text = vacancy.skills
        }
    }

    private fun setupContactSection(vacancy: VacancyDetail) {
        val hasPhone = !vacancy.phone.isNullOrEmpty()
        val hasEmail = !vacancy.email.isNullOrEmpty()
        val hasAddress = !vacancy.address.isNullOrEmpty()
        val hasAnyContact = hasPhone || hasEmail || hasAddress

        binding.contacts.isVisible = hasAnyContact
        binding.address.isVisible = hasAddress
        binding.phone.isVisible = hasPhone
        binding.email.isVisible = hasEmail

        if (hasAddress) {
            binding.address.text = vacancy.address
        }

        if (hasPhone) {
            binding.phone.text = vacancy.phone
            binding.phone.setTextColor(resources.getColor(R.color.blue, null))
            binding.phone.setOnClickListener {
                makePhoneCall(vacancy.phone!!)
            }
        }

        if (hasEmail) {
            binding.email.text = vacancy.email
            binding.email.setTextColor(resources.getColor(R.color.blue, null))
            binding.email.setOnClickListener {
                sendEmail(vacancy.email!!)
            }
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun sendEmail(emailAddress: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, R.string.response_to_vacancy)
        }
        startActivity(Intent.createChooser(intent, R.string.select_mail_app.toString()))
    }

    private fun setupClickListeners(vacancy: VacancyDetail) {
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
            binding.favorite.setBackgroundResource(R.drawable.icon_favorite_red)
        } else {
            binding.favorite.setBackgroundResource(R.drawable.icon_favorite_off)
        }
    }

    private fun shareVacancy(shareContent: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareContent)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_vacancy)))
    }

    private fun showLoading() {
        binding.progressbar.isVisible = true
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = false
        binding.placeholderVacancyNoInternet.isVisible = false
    }

    private fun showContent() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = true
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = false
        binding.placeholderVacancyNoInternet.isVisible = false
    }

    private fun showError() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = true
        binding.placeholderVacancyNotFound.isVisible = false
        binding.placeholderVacancyNoInternet.isVisible = false
    }

    private fun showVacancyNotFound() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = true
        binding.placeholderVacancyNoInternet.isVisible = false
    }

    private fun showNoInternet() {
        binding.progressbar.isVisible = false
        binding.vacancyDetails.isVisible = false
        binding.placeholderServerError.isVisible = false
        binding.placeholderVacancyNotFound.isVisible = false
        binding.placeholderVacancyNoInternet.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_NAME = "vacancy_id"
    }
}
