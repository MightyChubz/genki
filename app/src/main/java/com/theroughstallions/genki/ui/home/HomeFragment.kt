package com.theroughstallions.genki.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import com.theroughstallions.genki.R
import com.theroughstallions.genki.databinding.FragmentHomeBinding

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

        // Darkmode support for icons
        val constructionImageView = binding.constructionImageView
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> constructionImageView.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.ic_home_construction_dark
                )
            )
            Configuration.UI_MODE_NIGHT_NO -> constructionImageView.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.ic_home_construction
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}