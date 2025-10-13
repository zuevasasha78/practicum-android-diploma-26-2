package ru.practicum.android.diploma.filter.presentation.workplace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.filter.domain.WorkplaceType
import ru.practicum.android.diploma.filter.presentation.main.MainFilterFragment.Companion.COUNTRY_RESULT_KEY
import ru.practicum.android.diploma.filter.presentation.main.MainFilterFragment.Companion.PLACE_REQUEST_KEY
import ru.practicum.android.diploma.filter.presentation.main.MainFilterFragment.Companion.REGION_RESULT_KEY
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
        initView()
        val countyValue = arguments?.getString(COUNTRY_NAME)
        val regionValue = arguments?.getString(REGION_NAME)
        viewModel.uploadWorkplace(regionValue, countyValue)
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
    }

    private fun initBackButton() {
        viewBinding.toolbar.setOnClickListener {
            setFragmentResult(
                PLACE_REQUEST_KEY,
                bundleOf(
                    COUNTRY_RESULT_KEY to viewModel.countyValue,
                    REGION_RESULT_KEY to viewModel.regionValue
                )
            )
            findNavController().popBackStack(R.id.mainFilterFragment, false)
        }
    }

    private fun initList() {
        workplaceAdapter = WorkplaceAdapter { place ->
            when (place.type) {
                WorkplaceType.COUNTRY -> {
                    if (viewModel.countyValue != null) {
                        viewModel.clearCountry()
                    } else {
                        findNavController().navigate(R.id.selectCountryFragment)
                    }
                }
                WorkplaceType.REGION -> {
                    if (viewModel.regionValue != null) {
                        viewModel.clearRegion()
                    } else {
                        val countryName = arguments?.getString(COUNTRY_NAME)
                        val args = bundleOf(COUNTRY_NAME to countryName)
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
        const val REGION_NAME = "regionName"
    }
}
