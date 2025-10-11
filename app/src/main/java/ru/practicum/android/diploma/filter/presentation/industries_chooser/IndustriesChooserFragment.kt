package ru.practicum.android.diploma.filter.presentation.industries_chooser

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustriesChooserBinding
import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.filter.presentation.industries_chooser.adapter.IndustriesAdapter
import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.search.presentation.models.Placeholder

class IndustriesChooserFragment : Fragment() {

    private var _binding: FragmentIndustriesChooserBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<IndustriesChooserViewModel>()
    private val adapter by lazy { IndustriesAdapter() }
    private var fullList: List<FilterIndustry> = emptyList()

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
        binding.industriesRv.adapter = adapter
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesChooserScreenState.Loading -> setLoadingState()
                is IndustriesChooserScreenState.Success -> setSuccessState(state.industriesList)
                is IndustriesChooserScreenState.Error -> setErrorState(state.placeholder)
            }
        }
        binding.editText.addTextChangedListener(
            beforeTextChanged = { _: CharSequence?, _: Int, _: Int, _: Int -> },
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (!text.isNullOrEmpty()) {
                    binding.etEndIcon.setImageResource(R.drawable.icon_close)
                    binding.etEndIcon.setOnClickListener(etEndIconListener)
                } else {
                    binding.etEndIcon.setImageResource(R.drawable.icon_search)
                    binding.etEndIcon.setOnClickListener(null)
                }
            },
            afterTextChanged = { text: Editable? ->
                adapter.setList(fullList.filter {
                    it.name.startsWith(
                        text.toString(),
                        true
                    ) || it.name.contains(text.toString(), true)
                })
            },
        )
    }

    private fun setLoadingState() {
        binding.placeholder.root.isVisible = false
        binding.progressBar.isVisible = true
        binding.industriesRv.isVisible = false
    }

    private fun setSuccessState(list: List<FilterIndustry>) {
        fullList = list
        adapter.setList(list)
        binding.placeholder.root.isVisible = false
        binding.progressBar.isVisible = false
        binding.industriesRv.isVisible = true
    }

    private fun setErrorState(placeholder: Placeholder) {
        binding.placeholder.image.setImageResource(placeholder.image)
        placeholder.text?.let { binding.placeholder.placeholderText.setText(it) }
        binding.placeholder.root.isVisible = true
        binding.progressBar.isVisible = false
        binding.industriesRv.isVisible = false
    }

    private val etEndIconListener = View.OnClickListener {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
        binding.editText.text?.clear()
        adapter.setList(fullList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
