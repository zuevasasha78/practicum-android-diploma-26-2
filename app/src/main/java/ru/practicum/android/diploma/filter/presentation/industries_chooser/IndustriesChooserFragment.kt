package ru.practicum.android.diploma.filter.presentation.industries_chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustriesChooserBinding

class IndustriesChooserFragment: Fragment() {

    private var _binding: FragmentIndustriesChooserBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<IndustriesChooserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentIndustriesChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
