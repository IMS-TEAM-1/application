package com.example.ims3000.ui.fragments

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.api.util.Resource
import com.example.ims3000.databinding.FragmentMapBinding
import com.example.ims3000.ui.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    lateinit var viewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val redPaint: Paint = Paint().apply { setARGB(255, 255, 0, 0) }

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

        val canvas = Canvas()

        binding.drawButton.setOnClickListener {
            binding.mapCanvas.draw(canvas)
        }
    }

    fun draw(canvas: Canvas) {
        // Get the drawable's bounds
        val width: Int = binding.mapCanvas.maxWidth
        val height: Int = binding.mapCanvas.maxHeight
        val radius: Float = Math.min(width, height).toFloat() / 2f

        // Draw a red circle in the center
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, redPaint)
    }

    fun setAlpha(alpha: Int) {
        // This method is required
    }

    fun setColorFilter(colorFilter: ColorFilter?) {
        // This method is required
    }

    fun getOpacity(): Int =
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        PixelFormat.OPAQUE

}

