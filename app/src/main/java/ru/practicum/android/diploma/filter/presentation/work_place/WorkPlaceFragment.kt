package ru.practicum.android.diploma.filter.presentation.work_place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.presentation.chooser.ChooserFragment.Companion.IS_COUNTRY_ARG
import ru.practicum.android.diploma.filter.presentation.chooser.ChooserFragment.Companion.IS_TOWN_ARG

class WorkPlaceFragment : Fragment() {

    private var _binding: FragmentWorkPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.townButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_workPlaceFragment_to_chooserFragment,
                bundleOf(IS_TOWN_ARG to true)
            )
        }
        binding.countryButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_workPlaceFragment_to_chooserFragment,
                bundleOf(IS_COUNTRY_ARG to true)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
