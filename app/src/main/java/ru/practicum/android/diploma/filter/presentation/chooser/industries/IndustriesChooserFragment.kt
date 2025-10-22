package ru.practicum.android.diploma.filter.presentation.chooser.industries

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
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustriesChooserBinding
import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.filter.presentation.chooser.industries.adapter.IndustriesAdapter
import ru.practicum.android.diploma.filter.presentation.chooser.industries.models.IndustriesPlaceholder
import ru.practicum.android.diploma.search.domain.model.FilterIndustry

class IndustriesChooserFragment : Fragment() {

    private var _binding: FragmentIndustriesChooserBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<IndustriesChooserViewModel>()
    private var adapter: IndustriesAdapter? = null

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

        setupToolbar()
        setupAdapter()
        setupObservers()
        setupSearchField()
        setupConfirmButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapter() {
        adapter = IndustriesAdapter(
            listener = object : IndustriesAdapter.IndustryAdapterListener {
                override fun onIndustrySelected(industry: FilterIndustry) {
                    viewModel.selectIndustry(industry)
                    updateConfirmButtonVisibility(true)
                }

                override fun onIndustryDeselected() {
                    viewModel.clearSelection()
                    updateConfirmButtonVisibility(false)
                }
            }
        )
        binding.industriesRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesChooserScreenState.Loading -> setLoadingState()
                is IndustriesChooserScreenState.Success -> setSuccessState(state.industries, state.isChosen)
                is IndustriesChooserScreenState.NoInternet -> setErrorState(IndustriesPlaceholder.NoInternet)
                is IndustriesChooserScreenState.ServerError -> setErrorState(IndustriesPlaceholder.ServerError)
                is IndustriesChooserScreenState.NoResult -> setErrorState(IndustriesPlaceholder.NoResult)
            }
        }
    }

    private fun setupSearchField() {
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
                val query = text?.toString() ?: ""
                viewModel.filterIndustries(query)
            },
        )
    }

    private fun setupConfirmButton() {
        binding.confirmButton.setOnClickListener {
            viewModel.saveSelectedIndustry()
            findNavController().popBackStack()
        }

        binding.confirmButton.isVisible = false
    }

    private fun setLoadingState() {
        binding.placeholder.root.isVisible = false
        binding.progressBar.isVisible = true
        binding.industriesRv.isVisible = false
        binding.confirmButton.isVisible = false
    }

    private fun setSuccessState(list: List<FilterIndustry>, isChosen: Boolean) {
        adapter?.setList(list)
        viewModel.getSelectedIndustry()?.let { selectedIndustry ->
            adapter?.updateSelectedIndustry(selectedIndustry)
        }

        updateConfirmButtonVisibility(isChosen)
        binding.placeholder.root.isVisible = false
        binding.progressBar.isVisible = false
        binding.industriesRv.isVisible = true
    }

    private fun setErrorState(placeholder: IndustriesPlaceholder) {
        binding.placeholder.image.setImageResource(placeholder.image)
        placeholder.text?.let { binding.placeholder.placeholderText.setText(it) }
        binding.placeholder.root.isVisible = true
        binding.progressBar.isVisible = false
        binding.industriesRv.isVisible = false
        binding.confirmButton.isVisible = false
    }

    private fun updateConfirmButtonVisibility(isVisible: Boolean) {
        binding.confirmButton.isVisible = isVisible
    }

    private val etEndIconListener = View.OnClickListener {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
        binding.editText.text?.clear()
        viewModel.filterIndustries("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }
}
