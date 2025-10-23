package ru.practicum.android.diploma.filter.presentation.workplace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.filter.presentation.workplace.adapter.WorkplaceAdapter
import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceType
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
        uploadData()
        initView()
        setData()

    }

    private fun uploadData() {
        setFragmentResultListener(WORKPLACE_REQUEST_KEY) { _, bundle ->
            val countryName = bundle.getString(COUNTRY_NAME)
            val country = getLocation(countryName, bundle, COUNTRY_ID)
            val regioName = bundle.getString(REGION_NAME)
            val region = getLocation(regioName, bundle, REGION_ID)?.copy(parent = country)
            viewModel.uploadWorkplace(country = country, region = region)
        }
    }

    private fun getLocation(name: String?, bundle: Bundle, key: String): LocationUi? {
        return if (name != null) {
            val id = bundle.getInt(key, -1)
            LocationUi(id, name)
        } else {
            null
        }
    }

    private fun setData() {
        viewModel.workplaceUi.observe(viewLifecycleOwner) { places ->
            val places = listOf(
                WorkplaceType.Country(places.country),
                WorkplaceType.Region(places.region)
            )
            workplaceAdapter?.let { it.setItems(places) }
        }
    }

    private fun initView() {
        initList()
        initBackButton()
        initConfirmButton()
    }

    private fun initConfirmButton() {
        viewModel.workplaceUi.observe(viewLifecycleOwner) { workplaces ->
            if (workplaces.country != null || workplaces.region != null) {
                viewBinding.confirmButton.isVisible = true
                viewBinding.confirmButton.setOnClickListener {
                    viewModel.saveWorkplace(workplaces)
                    findNavController().popBackStack(R.id.mainFilterFragment, false)
                }
            } else {
                viewBinding.confirmButton.isVisible = false
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
            when (place) {
                is WorkplaceType.Country -> {
                    if (place.location != null) {
                        viewModel.clearCountry()
                    } else {
                        findNavController().navigate(R.id.selectCountryFragment)
                    }
                }
                is WorkplaceType.Region -> {
                    if (place.location != null) {
                        viewModel.clearRegion()
                    } else {
                        val countyId = viewModel.workplaceUi.value.country?.let { it.id } ?: -1
                        val countryName = viewModel.workplaceUi.value.country?.name
                        val args = bundleOf(
                            COUNTRY_ID to countyId,
                            COUNTRY_NAME to countryName
                        )
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
        const val WORKPLACE_REQUEST_KEY = "country"
        const val COUNTRY_NAME = "countryName"
        const val COUNTRY_ID = "countryId"
        const val REGION_ID = "regionId"
        const val REGION_NAME = "regionName"
    }
}
