package ru.practicum.android.diploma.filter.presentation.workplace.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectRegionBinding
import ru.practicum.android.diploma.filter.presentation.workplace.adapter.LocationAdapter
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.COUNTRY_ID
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.COUNTRY_NAME
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.REGION_ID
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.REGION_NAME
import ru.practicum.android.diploma.filter.presentation.workplace.fragments.WorkplaceFragment.Companion.WORKPLACE_REQUEST_KEY
import ru.practicum.android.diploma.filter.presentation.workplace.models.AreaPlaceholder
import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi
import ru.practicum.android.diploma.filter.presentation.workplace.states.LocationScreenState
import ru.practicum.android.diploma.filter.presentation.workplace.vm.SelectRegionViewModel

class SelectRegionFragment : Fragment() {
    private val viewModel: SelectRegionViewModel by viewModel()
    private val viewBinding: FragmentSelectRegionBinding get() = _viewBinding!!
    private var _viewBinding: FragmentSelectRegionBinding? = null
    private var locationAdapter: LocationAdapter? = null
    private var countryId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentSelectRegionBinding.inflate(
            inflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryId = arguments?.getInt(COUNTRY_ID, -1)
        if (countryId == -1) countryId = null
        viewModel.uploadRegions(countryId)
        initView()
        setData()
    }

    private fun setData() {
        viewModel.regions.observe(viewLifecycleOwner) { regionState ->
            when (regionState) {
                is LocationScreenState.Content -> showResult(regionState.locationUis)
                LocationScreenState.Empty -> showPlaceholder(AreaPlaceholder.Empty)
                LocationScreenState.Error -> showPlaceholder(AreaPlaceholder.Error)
                LocationScreenState.Loading -> showLoading()
                LocationScreenState.NoInternet -> showPlaceholder(AreaPlaceholder.NoInternet)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun showResult(areas: List<LocationUi>) {
        viewBinding.placeholder.root.isVisible = false
        viewBinding.progressBar.isVisible = false
        viewBinding.countryRecyclerView.isVisible = true
        locationAdapter?.let { it.setItems(areas) }
    }

    private fun showLoading() {
        viewBinding.placeholder.root.isVisible = false
        viewBinding.progressBar.isVisible = true
        viewBinding.countryRecyclerView.isVisible = false
    }

    private fun initView() {
        initBackButton()
        initList()
        initSearch()
    }

    private fun initSearch() {
        viewBinding.editText.addTextChangedListener(
            beforeTextChanged = { _: CharSequence?, _: Int, _: Int, _: Int -> },
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (!text?.toString().isNullOrEmpty()) {
                    viewBinding.etEndIcon.setImageResource(R.drawable.icon_close)
                    viewBinding.etEndIcon.setOnClickListener(etEndIconListener)
                } else {
                    viewBinding.etEndIcon.setImageResource(R.drawable.icon_search)
                    viewBinding.etEndIcon.setOnClickListener(null)
                }
                viewModel.searchRegions(text?.trim().toString(), countryId)
            },
            afterTextChanged = { _: Editable? -> },
        )
    }

    private val etEndIconListener = OnClickListener {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
        viewBinding.editText.text?.clear()
        locationAdapter?.setItems(emptyList())
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
        locationAdapter = LocationAdapter { place ->
            val countryId = place.parent?.id ?: -1
            setFragmentResult(
                WORKPLACE_REQUEST_KEY,
                bundleOf(
                    REGION_NAME to place.name,
                    REGION_ID to place.id,
                    COUNTRY_ID to countryId,
                    COUNTRY_NAME to place.parent?.name
                )
            )
            findNavController().popBackStack()
        }

        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        viewBinding.apply {
            countryRecyclerView.layoutManager = layoutManager
            countryRecyclerView.adapter = locationAdapter
        }
    }
}
