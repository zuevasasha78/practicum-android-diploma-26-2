package ru.practicum.android.diploma.filter.presentation.chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.databinding.FragmentChooserBinding

class ChooserFragment : Fragment() {

    private var _binding: FragmentChooserBinding? = null
    private val binding get() = _binding!!
    private val args: ChooserFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi(args)
    }

    private fun setUi(args: ChooserFragmentArgs) {
        binding.textView.text = if(args.isTown) {
            "Town chooser"
        } else if(args.isCountry) {
            "Country chooser"
        } else if(args.isSector) {
            "Sector chooser"
        } else {
            throw Exception("ChooserFragment wrong argument")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IS_TOWN_ARG = "isTown"
        const val IS_COUNTRY_ARG = "isCountry"
        const val IS_SECTOR_ARG = "isSector"
    }
}
