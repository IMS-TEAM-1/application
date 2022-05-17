package com.example.ims3000.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.example.ims3000.data.entities.Coordinates

class MapDrawer : Drawable() {

    private val redPaint: Paint = Paint().apply { setARGB(255, 255, 0, 0) }
    private val greenPaint: Paint = Paint().apply { setARGB(255, 0, 255, 0) }
    private val bluePaint: Paint = Paint().apply { setARGB(255, 0, 0, 255) }

    private val drawerCoordinates = mutableListOf<Coordinates>()

    fun addCords(x: Float, y: Float, classification: String?) {
        if (classification == null) {
            val item = Coordinates(x, y, null)
            drawerCoordinates.add(item)
        } else {
            val item = Coordinates(x, y, classification)
            drawerCoordinates.add(item)
        }
    }

    override fun draw(canvas: Canvas) {
        redPaint.strokeWidth = 25F
        greenPaint.strokeWidth = 25F
        bluePaint.strokeWidth = 50F

        drawerCoordinates.forEachIndexed { index, coordinates ->
            if (index == drawerCoordinates.size - 1) {
                //Skip
                return
            } else {
                if (index == 0) {
                    // First line start at x 550 and y 900
                    canvas.drawLine(
                        550F,
                        900F,
                        drawerCoordinates[index].x,
                        drawerCoordinates[index].y,
                        greenPaint)
                } else {
                    canvas.drawLine(
                        drawerCoordinates[index].x,
                        drawerCoordinates[index].y,
                        drawerCoordinates[index + 1].x,
                        drawerCoordinates[index + 1].y,
                        redPaint)
                    if (drawerCoordinates[index].classification == null) {
                        canvas.drawPoint(
                            drawerCoordinates[index].x,
                            drawerCoordinates[index].y,
                            bluePaint)
                    }
                }
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