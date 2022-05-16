package com.example.ims3000.ui.fragments

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

    private val mowerId = 1
    private val xStart = mutableListOf<Float>()
    private val yStart = mutableListOf<Float>()
    private val xEnd = mutableListOf<Float>()
    private val yEnd = mutableListOf<Float>()
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
            response.message?.let { Log.d("debug", it) }
            when (response) {
                is Resource.Success -> response.data?.let { result ->
                    result.forEach { mowerLocation ->
                        Log.d("debug", mowerLocation.id.toString())
                        addCords(mowerLocation.x, mowerLocation.y)
                    }
                }
            }
        }

        Log.d("debug", "HALLO????")

        binding.drawButton.setOnClickListener {
            val drawer = MapDrawer()
            Log.d("debug", mowerCoordinates.size.toString())
            mowerCoordinates.forEachIndexed { index, coordinates ->
                Log.d("debug", index.toString())
                if (index == mowerCoordinates.size) {
                    //Skip
                } else {
                    drawer.xStart = mowerCoordinates[index].x
                    drawer.yStart = mowerCoordinates[index].y
                    drawer.xEnd = mowerCoordinates[index+1].x
                    drawer.yEnd = mowerCoordinates[index+1].y
                    Log.d("debug", mowerCoordinates[index].x.toString())
                }
            }
            //Log.d("debug", mowerCoordinates[0].x.toString())
            binding.mapCanvas.setImageDrawable(drawer)
        }
    }

    private fun fetchMowerLocationFromViewModelById(id: Int) {
        try {
            viewModel.getMowerLocation(id)
        } catch (e: Exception) {}
    }

    private fun addCords(x: Float, y: Float) {
        val item = Coordinates(x, y)
        mowerCoordinates.add(item)
    }
}

