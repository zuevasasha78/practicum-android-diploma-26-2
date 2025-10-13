package ru.practicum.android.diploma.search.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.PaginationState
import ru.practicum.android.diploma.search.domain.models.SearchScreenState
import ru.practicum.android.diploma.search.presentation.adapter.VacancyAdapter
import ru.practicum.android.diploma.search.presentation.adapter.VacancyAdapterItemDecorator
import ru.practicum.android.diploma.search.presentation.models.Placeholder
import ru.practicum.android.diploma.vacancy.presentation.VacancyFragment.Companion.ARG_NAME

class SearchFragment : Fragment() {

    private val searchViewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var vacancyAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener(menuListener)

        vacancyAdapter = VacancyAdapter(adapterListener)
        binding.vacancyRv.apply {
            adapter = vacancyAdapter
            addItemDecoration(VacancyAdapterItemDecorator())
        }

        searchViewModel.screenState.observe(viewLifecycleOwner) { state ->
            setUi(state)
        }

        binding.editText.addTextChangedListener(
            beforeTextChanged = { _: CharSequence?, _: Int, _: Int, _: Int -> },
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (!text.isNullOrEmpty()) {
                    binding.etEndIcon.setImageResource(R.drawable.icon_close)
                    binding.etEndIcon.setOnClickListener(etEndIconListener)
                    searchViewModel.searchVacancyDebounce(text.toString())
                } else {
                    binding.etEndIcon.setImageResource(R.drawable.icon_search)
                    binding.etEndIcon.setOnClickListener(null)
                    searchViewModel.setScreenState(SearchScreenState.Init)
                }
            },
            afterTextChanged = { _: Editable? -> },
        )

        binding.vacancyRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val pos = layoutManager.findLastVisibleItemPosition()
                    val itemsCount = layoutManager.itemCount
                    if (pos >= itemsCount - 1 && searchViewModel.canLoadNextPage.value == true) {
                        searchViewModel.loadNextPage()
                    }
                }
            }
        })
    }

    private fun setUi(screenState: SearchScreenState) {
        when (screenState) {
            is SearchScreenState.Init -> {
                showPlaceholder(Placeholder.InitPlaceholder)
            }

            is SearchScreenState.Error -> {
                showPlaceholder(screenState.placeholder)
            }

            is SearchScreenState.Success -> {
                binding.placeholder.root.isVisible = false
                binding.vacancyRv.isVisible = true
                vacancyAdapter?.setItems(screenState.vacancyList)
                binding.searchInfo.apply {
                    isVisible = true
                    text = requireContext().resources.getQuantityString(
                        R.plurals.search_info_vacancy,
                        screenState.amount,
                        screenState.amount
                    )
                }

                when (screenState.paginationState) {
                    PaginationState.Idle -> binding.progressBar.isVisible = false
                    PaginationState.Loading -> binding.progressBar.isVisible = true
                    is PaginationState.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            getString(screenState.paginationState.message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            is SearchScreenState.Loading -> {
                setLoadingState()
            }
        }
    }

    private fun setLoadingState() {
        binding.placeholder.root.isVisible = false
        binding.vacancyRv.isVisible = false
        binding.searchInfo.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun showPlaceholder(placeholder: Placeholder) {
        binding.placeholder.apply {
            image.setImageResource(placeholder.image)
            placeholderText.text = placeholder.text?.let { getString(it) } ?: ""
            root.isVisible = true
        }

        binding.progressBar.isVisible = false
        binding.vacancyRv.isVisible = false
        if (placeholder is Placeholder.NoResult) {
            binding.searchInfo.apply {
                isVisible = true
                text = requireContext().getString(R.string.search_info_no_vacancy)
            }
        } else {
            binding.searchInfo.isVisible = false
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
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
        binding.editText.text?.clear()
        vacancyAdapter?.setItems(emptyList())
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
