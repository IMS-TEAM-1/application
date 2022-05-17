package com.example.ims3000.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.ims3000.data.entities.Coordinates
import kotlin.math.absoluteValue

class MapDrawer : Drawable() {

    private val redPaint: Paint = Paint().apply { setARGB(255, 255, 0, 0) }
    private val greenPaint: Paint = Paint().apply { setARGB(255, 0, 255, 0) }
    private val bluePaint: Paint = Paint().apply { setARGB(255, 0, 0, 255) }
    private val textPaint: Paint = Paint().apply {
        textSize = 80F
    }

    private val drawerCoordinates = mutableListOf<Coordinates>()
    private val xMiddle = 550F
    private val yMiddle = 900F
    private var scalingFactor = 10F

    fun addCords(x: Float, y: Float, classification: String?) {
        if (classification == null) {
            val item = Coordinates(x, y, null)
            drawerCoordinates.add(item)
        } else {
            val item = Coordinates(x, y, classification)
            drawerCoordinates.add(item)
        }
    }

    private fun defineScalingFactor() {
        // Scaling offset roughly == 90% of standard xOffset
        val scalingOffset = 450F
        var xMax = 0F
        var yMax = 0F

        for (each in drawerCoordinates) {
            if (each.x.absoluteValue > xMax) {
                xMax = each.x.absoluteValue
            }
            if (each.y.absoluteValue > yMax) {
                yMax = each.y.absoluteValue
            }
        }
        Log.d("scaling", xMax.toString() + "     " + yMax.toString())
        if (xMax < yMax) {
            scalingFactor = scalingOffset / yMax
        } else {
            scalingFactor = scalingOffset / xMax
        }
        Log.d("scaling", scalingFactor.toString())
    }

    override fun draw(canvas: Canvas) {
        redPaint.strokeWidth = 25F
        greenPaint.strokeWidth = 25F
        bluePaint.strokeWidth = 30F

        defineScalingFactor()

        drawerCoordinates.forEachIndexed { index, coordinates ->
            Log.d("debug", "size:" + drawerCoordinates.size.toString())
            if (index == drawerCoordinates.size - 1) {
                //Skip
                return
            } else {
                if (index == 0) {
                    // First line start at x 550 and y 900
                    canvas.drawLine(
                        xMiddle,
                        yMiddle,
                        (drawerCoordinates[index].x * scalingFactor) + xMiddle,
                        (drawerCoordinates[index].y * scalingFactor) + yMiddle,
                        greenPaint)
                } else {
                    canvas.drawLine(
                        (drawerCoordinates[index].x * scalingFactor) + xMiddle,
                        (drawerCoordinates[index].y * scalingFactor)+ yMiddle,
                        (drawerCoordinates[index + 1].x * scalingFactor) + xMiddle,
                        (drawerCoordinates[index + 1].y * scalingFactor) + yMiddle,
                        redPaint)
                    if (drawerCoordinates[index + 1].classification != null) {
                        canvas.drawPoint(
                            (drawerCoordinates[index + 1].x * scalingFactor) + xMiddle,
                            (drawerCoordinates[index + 1].y * scalingFactor)+ yMiddle,
                            bluePaint)
                        canvas.drawText(
                                drawerCoordinates[index + 1].classification.toString(),
                            (drawerCoordinates[index + 1].x * scalingFactor) + xMiddle - 70F,
                                (drawerCoordinates[index + 1].y * scalingFactor)+ yMiddle - 50F,
                            textPaint,

                        )
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