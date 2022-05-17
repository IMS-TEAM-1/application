package com.example.ims3000.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.ims3000.data.entities.Coordinates

class MapDrawer : Drawable() {

    private val redPaint: Paint = Paint().apply { setARGB(255, 255, 0, 0) }
    private val anotherPaint: Paint = Paint().apply { setARGB(255, 0, 255, 0) }

    var xStart: Float = 0.0f
    var yStart: Float = 0.0f
    var xEnd: Float = 0.0f
    var yEnd: Float = 0.0f
    private val mowerCoordinates = mutableListOf<Coordinates>()

    fun addCords(x: Float, y: Float) {
        val item = Coordinates(x, y)
        mowerCoordinates.add(item)
    }

    override fun draw(canvas: Canvas) {
        redPaint.strokeWidth = 25F
        mowerCoordinates.forEachIndexed { index, coordinates ->
            if (index == mowerCoordinates.size - 1) {
                //Skip
                Log.d("debug", "drawFunction: if block index value: " + index.toString())
                return
            } else {
                Log.d("debug", "drawFunction: else block index value: " + index.toString())
                canvas.drawLine(
                    mowerCoordinates[index].x,
                    mowerCoordinates[index].y,
                    mowerCoordinates[index + 1].x,
                    mowerCoordinates[index + 1].y,
                    redPaint)
                /*
                canvas.drawLine(
                    155F,
                    155F,
                    290F,
                    290F,
                    redPaint)
                */
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        // This method is required
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // This method is required
    }

    override fun getOpacity(): Int =
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        PixelFormat.OPAQUE
}