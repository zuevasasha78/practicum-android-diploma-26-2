package ru.practicum.android.diploma.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.SearchScreenState

class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()
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
        binding.toolBar.apply {
            inflateMenu(R.menu.search_toolbar_menu)
            setOnMenuItemClickListener(menuListener)
        }
        searchViewModel.getScreenState().observe(viewLifecycleOwner) {
            setUi(it)
        }
        binding.vacancyButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment)
        }
    }

    private fun setUi(screenState: SearchScreenState) {
        when (screenState) {
            is SearchScreenState.Init -> {
                binding.initPlaceholder.isVisible = true
            }

            else -> Unit
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
