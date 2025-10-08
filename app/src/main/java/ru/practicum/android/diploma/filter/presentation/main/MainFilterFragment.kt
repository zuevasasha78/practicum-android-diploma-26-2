package ru.practicum.android.diploma.filter.presentation.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainFilterBinding
import ru.practicum.android.diploma.filter.domain.ChooserType
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
        binding.toolbarFilter.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.placeEditText.setOnClickListener {
            binding.placeEditText.setText("Москва")
            binding.placeInputLayout.setEndIconDrawable(R.drawable.icon_close)
            updateHintColor(true)
            //findNavController().navigate(R.id.action_mainFilterFragment_to_workPlaceFragment)
        }

        binding.placeInputLayout.setEndIconOnClickListener {
            binding.placeEditText.setText("")
            binding.placeInputLayout.setEndIconDrawable(R.drawable.leading_icon)
            updateHintColor(false)
        }

        binding.sectorChooserButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_mainFilterFragment_to_chooserFragment,
                bundleOf(ARG_NAME to ChooserType.SectorType),
            )
        }
    }





    private fun updateHintColor(hasText: Boolean) {
        val colorResId = if (hasText) R.color.text_color else R.color.hint_color_filter_screen
        binding.placeInputLayout.defaultHintTextColor = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), colorResId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
