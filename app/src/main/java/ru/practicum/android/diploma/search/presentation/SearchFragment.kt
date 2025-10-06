package ru.practicum.android.diploma.search.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.SearchScreenState
import ru.practicum.android.diploma.search.presentation.adapter.VacancyAdapter
import ru.practicum.android.diploma.search.presentation.adapter.VacancyAdapterItemDecorator
import ru.practicum.android.diploma.utils.Utils.setImageTop
import ru.practicum.android.diploma.vacancy.presentation.VacancyFragment.Companion.ARG_NAME

class SearchFragment : Fragment() {

    private val searchViewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener(menuListener)

        searchViewModel.getScreenState().observe(viewLifecycleOwner) {
            setUi(it)
        }

        binding.vacancyRv.apply {
            adapter = VacancyAdapter(adapterListener)
            addItemDecoration(VacancyAdapterItemDecorator())
        }

        binding.editText.addTextChangedListener(
            beforeTextChanged = { _: CharSequence?, _: Int, _: Int, _: Int -> },
            onTextChanged = { p0: CharSequence?, _: Int, _: Int, _: Int ->
                if (!p0.isNullOrEmpty()) {
                    binding.etEndIcon.setImageResource(R.drawable.icon_close)
                    binding.etEndIcon.setOnClickListener(etEndIconListener)
                    searchViewModel.searchVacancyDebounce(p0.toString())
                } else {
                    binding.etEndIcon.setImageResource(R.drawable.icon_search)
                    binding.etEndIcon.setOnClickListener(null)
                    searchViewModel.setScreenState(SearchScreenState.Init)
                }
            },
            afterTextChanged = { _: Editable? -> },
        )
    }

    private fun setUi(screenState: SearchScreenState) {
        when (screenState) {
            is SearchScreenState.Init -> {
                setInitState()
            }

            is SearchScreenState.NoInternet -> {
                setNoInternetState()
            }

            is SearchScreenState.Error -> {
                setErrorState()
            }

            is SearchScreenState.Success -> {
                binding.placeholder.isVisible = false
                binding.vacancyRv.apply {
                    (adapter as VacancyAdapter).setItems(screenState.vacancyList)
                    isVisible = true
                }
                binding.searchInfo.apply {
                    isVisible = true
                    text = requireContext().resources.getQuantityString(
                        R.plurals.search_info_vacancy,
                        screenState.amount,
                        screenState.amount
                    )
                }
                binding.progressBar.isVisible = false
            }

            is SearchScreenState.Loading -> {
                setLoadingState()
            }
        }
    }

    private fun setLoadingState() {
        binding.placeholder.isVisible = false
        binding.vacancyRv.isVisible = false
        binding.searchInfo.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun setInitState() {
        binding.placeholder.apply {
            setImageTop(getDrawable(requireContext(), R.drawable.search_screen_init_placeholder))
            text = ""
            isVisible = true
        }
        binding.progressBar.isVisible = false
        binding.vacancyRv.isVisible = false
        binding.searchInfo.isVisible = false
    }

    private fun setNoInternetState() {
        binding.placeholder.apply {
            setImageTop(getDrawable(requireContext(), R.drawable.no_internet_placeholder))
            text = requireContext().getString(R.string.no_internet_placeholder_text)
            isVisible = true
        }
        binding.vacancyRv.isVisible = false
        binding.progressBar.isVisible = false
        binding.searchInfo.isVisible = false
    }

    private fun setErrorState() {
        binding.placeholder.apply {
            setImageTop(getDrawable(requireContext(), R.drawable.no_result_placeholder))
            text = requireContext().getString(R.string.no_search_result_placeholder_text)
            isVisible = true
        }
        binding.vacancyRv.isVisible = false
        binding.progressBar.isVisible = false
        binding.searchInfo.apply {
            isVisible = true
            text = requireContext().getString(R.string.search_info_no_vacancy)
        }
    }

    private val menuListener = OnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.filter_button -> {
                findNavController().navigate(R.id.action_searchFragment_to_mainFilterFragment)
                true
            }

            else -> false
        }
    }

    private val etEndIconListener = OnClickListener {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
        binding.editText.text?.clear()
        (binding.vacancyRv.adapter as VacancyAdapter).setItems(emptyList())
    }

    private val adapterListener = VacancyAdapter.VacancyClickListener {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment,
            bundleOf(ARG_NAME to it.id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
