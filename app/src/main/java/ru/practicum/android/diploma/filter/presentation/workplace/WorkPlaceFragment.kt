package ru.practicum.android.diploma.filter.presentation.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceBinding
import ru.practicum.android.diploma.filter.domain.ChooserType
import ru.practicum.android.diploma.filter.presentation.chooser.ChooserFragment

class WorkPlaceFragment : Fragment() {
    private var _binding: FragmentWorkPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.townButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_workPlaceFragment_to_chooserFragment,
                bundleOf(ChooserFragment.ARG_NAME to ChooserType.TownType),
            )
        }
        binding.countryButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_workPlaceFragment_to_chooserFragment,
                bundleOf(ChooserFragment.ARG_NAME to ChooserType.CountryType),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
