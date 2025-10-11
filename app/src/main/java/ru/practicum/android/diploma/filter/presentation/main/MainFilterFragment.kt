package ru.practicum.android.diploma.filter.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainFilterBinding
import ru.practicum.android.diploma.filter.domain.model.ChooserType
import ru.practicum.android.diploma.filter.presentation.chooser.ChooserFragment.Companion.ARG_NAME

class MainFilterFragment : Fragment() {
    private var _binding: FragmentMainFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.placeChooserButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFilterFragment_to_workPlaceFragment)
        }
        binding.sectorChooserButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFilterFragment_to_chooserFragment,
                bundleOf(ARG_NAME to ChooserType.SectorType),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
