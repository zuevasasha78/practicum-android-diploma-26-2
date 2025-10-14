package ru.practicum.android.diploma.filter.presentation.workplace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectCountryBinding
import ru.practicum.android.diploma.filter.domain.Workplace
import ru.practicum.android.diploma.filter.presentation.workplace.AreaScreenState
import ru.practicum.android.diploma.filter.presentation.workplace.adapter.WorkplaceAdapter
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.COUNTRY_ID
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.COUNTRY_NAME
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.REGION_ID
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.REGION_NAME
import ru.practicum.android.diploma.filter.presentation.workplace.models.AreaPlaceholder
import ru.practicum.android.diploma.filter.presentation.workplace.vm.SelectCountryViewModel

class SelectCountryFragment : Fragment() {

    private val viewModel: SelectCountryViewModel by viewModel()
    private val viewBinding: FragmentSelectCountryBinding get() = _viewBinding!!
    private var _viewBinding: FragmentSelectCountryBinding? = null
    private var workplaceAdapter: WorkplaceAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentSelectCountryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uploadCountry()
        initView()
        viewModel.countries.observe(viewLifecycleOwner) { countryState ->
            when (countryState) {
                is AreaScreenState.Content -> showResult(countryState.areas)
                AreaScreenState.Empty -> showPlaceholder(AreaPlaceholder.Empty)
                AreaScreenState.Error -> showPlaceholder(AreaPlaceholder.Error)
                AreaScreenState.Loading -> showLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun showResult(areas: List<Workplace>) {
        viewBinding.placeholder.root.isVisible = false
        viewBinding.progressBar.isVisible = false
        viewBinding.countryRecyclerView.isVisible = true
        workplaceAdapter?.let { it.setItems(areas) }
    }

    private fun showLoading() {
        viewBinding.placeholder.root.isVisible = false
        viewBinding.progressBar.isVisible = true
        viewBinding.countryRecyclerView.isVisible = false
    }

    private fun initView() {
        initBackButton()
        initList()
    }

    private fun initBackButton() {
        viewBinding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showPlaceholder(placeholder: AreaPlaceholder) {
        viewBinding.progressBar.isVisible = false
        viewBinding.countryRecyclerView.isVisible = false

        viewBinding.placeholder.apply {
            image.setImageResource(placeholder.image)
            placeholderText.text = placeholder.text?.let { getString(it) } ?: ""
            root.isVisible = true
        }
    }

    private fun initList() {
        workplaceAdapter = WorkplaceAdapter { place ->
            val args = bundleOf(
                COUNTRY_NAME to place.value,
                COUNTRY_ID to place.id.toString(),
                REGION_NAME to null,
                REGION_ID to null,
            )
            findNavController().navigate(R.id.workplaceFragment, args)
        }

        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        viewBinding.apply {
            countryRecyclerView.layoutManager = layoutManager
            countryRecyclerView.adapter = workplaceAdapter
        }
    }
}
