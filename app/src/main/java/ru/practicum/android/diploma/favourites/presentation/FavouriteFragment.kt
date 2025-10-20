package ru.practicum.android.diploma.favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.favourites.presentation.models.FavoritePlaceholder
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.adapter.VacancyAdapter
import ru.practicum.android.diploma.vacancy.presentation.VacancyFragment.Companion.ARG_NAME

class FavouriteFragment : Fragment() {
    private val favouriteViewModel by viewModel<FavouriteVacanciesViewModel>()
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val favouriteAdapter = VacancyAdapter { vacancy -> openVacancy(vacancy.id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.favouriteRecyclerView.adapter = favouriteAdapter
        favouriteViewModel.uploadFavoriteVacancy()
        favouriteViewModel.screenState.observe(viewLifecycleOwner) {
            renderScreen(it)
        }
    }

    private fun renderScreen(favouriteScreenState: FavouriteScreenState) {
        when (favouriteScreenState) {
            is FavouriteScreenState.Loading -> setLoadingState()
            is FavouriteScreenState.Empty -> setPlaceholder(FavoritePlaceholder.EmptyList)
            is FavouriteScreenState.Error -> setPlaceholder(FavoritePlaceholder.Error)
            is FavouriteScreenState.Content -> showResult(favouriteScreenState.vacanciesList)
        }
    }

    private fun setLoadingState() {
        binding.progressBar.isVisible = true
        binding.placeholder.root.isVisible = false
        binding.favouriteRecyclerView.isVisible = false
    }

    private fun setPlaceholder(placeholder: FavoritePlaceholder) {
        binding.progressBar.isVisible = false
        binding.favouriteRecyclerView.isVisible = false

        binding.placeholder.apply {
            image.setImageResource(placeholder.image)
            placeholderText.text = placeholder.text?.let { getString(it) } ?: ""
            root.isVisible = true
        }
    }

    private fun showResult(vacanciesList: List<Vacancy>) {
        binding.progressBar.isVisible = false
        binding.placeholder.root.isVisible = false
        favouriteAdapter.setItems(vacanciesList)
        binding.favouriteRecyclerView.isVisible = true
    }

    private fun openVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_favouriteFragment_to_vacancyFragment,
            bundleOf(ARG_NAME to vacancyId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
