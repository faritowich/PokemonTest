package com.example.pokemontest.ui.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentDetailBinding
import com.example.pokemontest.ui.BaseFragment
import com.example.pokemontest.ui.viewmodels.MainViewModel


class DetailFragment : BaseFragment<FragmentDetailBinding>(
    FragmentDetailBinding::inflate
) {
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            heightTextView.text = args.currentItem.height
            weightTextView.text = args.currentItem.weight
            typeTextView.text = args.currentItem.type.toString()
        }

        Glide.with(requireContext())
            .load("https${args.currentItem.img.drop(4)}")
            .error(R.drawable.ic_broken_image)
            .into(binding.pokemonImageView)
    }
}