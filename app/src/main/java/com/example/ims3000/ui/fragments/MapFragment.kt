package com.example.ims3000.ui.fragments

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.entities.Coordinates
import com.example.ims3000.databinding.FragmentMapBinding
import com.example.ims3000.ui.MapDrawer
import com.example.ims3000.ui.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    lateinit var viewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val mowerId = 3
    private val mowerCoordinates = mutableListOf<Coordinates>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())?.get(MapViewModel::class.java)

        fetchMowerLocationFromViewModelById(mowerId)
        viewModel.getMowerLocation.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> response.data?.let { result ->
                    result.forEach { mowerLocation ->
                        if (mowerLocation.images.isEmpty()) {
                            addCords(mowerLocation.x, mowerLocation.y, null)
                        } else {
                            addCords(mowerLocation.x, mowerLocation.y, mowerLocation.images[0]?.classification)
                        }
                    }
                }
            }
        }

        binding.drawButton.setOnClickListener {
            val drawer = MapDrawer()
            mowerCoordinates.forEachIndexed { index, coordinates ->
                if (index == mowerCoordinates.size) {
                    //Skip
                } else {
                    drawer.addCords(
                        mowerCoordinates[index].x,
                        mowerCoordinates[index].y,
                        mowerCoordinates[index].classification)
                }
            }
            binding.mapCanvas.setImageDrawable(drawer)
        }
    }

    private fun fetchMowerLocationFromViewModelById(id: Int) {
        try {
            viewModel.getMowerLocation(id)
        } catch (e: Exception) {}
    }

    private fun addCords(x: Float, y: Float, classification: String?) {
        if (classification == null) {
            val item = Coordinates(x, y, null)
            mowerCoordinates.add(item)
        } else {
            val item = Coordinates(x, y, classification)
            mowerCoordinates.add(item)
        }
    }
}

