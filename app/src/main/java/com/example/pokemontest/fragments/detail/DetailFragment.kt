package com.example.pokemontest.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.pokemontest.R
import com.example.pokemontest.databinding.FragmentDetailBinding
import com.example.pokemontest.databinding.FragmentListBinding
import com.example.pokemontest.viewmodel.MainViewModel


class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        binding.heightTextView.setText(args.currentItem.height.toString())
        binding.weightTextView.setText(args.currentItem.weight.toString())
        binding.typeTextView.setText(args.currentItem.type.toString())

        return binding.root
    }

}