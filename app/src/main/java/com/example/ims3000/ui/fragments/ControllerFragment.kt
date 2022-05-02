package com.example.ims3000.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ims3000.R
import com.example.ims3000.databinding.FragmentControllerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller) {

    //lateinit var viewModel: HomeViewModel
    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root

    }

}