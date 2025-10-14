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
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.filter.domain.WorkplaceType
import ru.practicum.android.diploma.filter.presentation.workplace.adapter.WorkplaceAdapter
import ru.practicum.android.diploma.filter.presentation.workplace.vm.WorkplaceViewModel

class WorkplaceFragment : Fragment() {

    private val viewBinding: FragmentWorkplaceBinding get() = _viewBinding!!
    private var _viewBinding: FragmentWorkplaceBinding? = null
    private var workplaceAdapter: WorkplaceAdapter? = null
    private val viewModel: WorkplaceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentWorkplaceBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countyValue = arguments?.getString(COUNTRY_NAME)
        val regionValue = arguments?.getString(REGION_NAME)
        viewModel.uploadWorkplace(countyValue, regionValue)
        initView()
        setData()

    }

    private fun setData() {
        viewModel.workplace.observe(viewLifecycleOwner) { places ->
            workplaceAdapter?.let { it.setItems(places) }
        }
    }

    private fun initView() {
        initList()
        initBackButton()
        initConfirmButton()
    }

    private fun initConfirmButton() {
        viewModel.workplace.observe(viewLifecycleOwner) { workplaces ->
            val country = workplaces.find { it.type == WorkplaceType.COUNTRY }?.value
            val region = workplaces.find { it.type == WorkplaceType.REGION }?.value

            val countryId = arguments?.getString(COUNTRY_ID)
            val regionId = arguments?.getString(REGION_ID)
            val placeId = if (regionId != null) {
                regionId
            } else {
                countryId
            }
            if (country != null || region != null) {
                viewBinding.confirmButton.isVisible = true
            } else {
                viewBinding.confirmButton.isVisible = false
            }
            viewBinding.confirmButton.setOnClickListener {
                viewModel.updateWorkplace(country, region, placeId)
                findNavController().popBackStack(R.id.mainFilterFragment, false)
            }
        }
    }

    private fun initBackButton() {
        viewBinding.toolbar.setOnClickListener {
            findNavController().popBackStack(R.id.mainFilterFragment, false)
        }
    }

    private fun initList() {
        workplaceAdapter = WorkplaceAdapter { place ->
            when (place.type) {
                WorkplaceType.COUNTRY -> {
                    if (place.value != null) {
                        viewModel.clearCountry()
                    } else {
                        findNavController().navigate(R.id.selectCountryFragment)
                    }
                }
                WorkplaceType.REGION -> {
                    if (place.value != null) {
                        viewModel.clearRegion()
                    } else {
                        val countyValue = arguments?.getString(COUNTRY_NAME)
                        val args = bundleOf(COUNTRY_NAME to countyValue)
                        findNavController().navigate(R.id.selectRegionFragment, args)
                    }
                }
            }
        }
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        viewBinding.apply {
            workplaceRecyclerView.layoutManager = layoutManager
            workplaceRecyclerView.adapter = workplaceAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        const val COUNTRY_NAME = "countryName"
        const val COUNTRY_ID = "countryId"
        const val REGION_ID = "regionId"
        const val REGION_NAME = "regionName"
    }
}
