package com.theroughstallions.genki.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theroughstallions.genki.R
import com.theroughstallions.genki.databinding.FragmentHomeBinding
import com.theroughstallions.genki.helpers.NightModeHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        NightModeHelper.changeDrawableOnNightMode(
            binding.constructionImageView,
            requireContext(),
            resources,
            R.drawable.ic_home_construction,
            R.drawable.ic_home_construction_dark
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}