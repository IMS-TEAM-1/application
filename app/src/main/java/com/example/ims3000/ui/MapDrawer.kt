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
    private val collisionObjectCoordinates = mutableListOf<Coordinates>()
    private val xOffset = 550F
    private val yOffset = 900F
    private val xTextOffset = 70F
    private val yTextOffset = 50F
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
        // Scaling offset roughly == 80% of standard xOffset
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
        if (xMax < yMax) {
            scalingFactor = scalingOffset / yMax
        } else {
            scalingFactor = scalingOffset / xMax
        }
    }

    override fun draw(canvas: Canvas) {
        redPaint.strokeWidth = 25F
        greenPaint.strokeWidth = 25F
        bluePaint.strokeWidth = 45F

        defineScalingFactor()

        drawerCoordinates.forEachIndexed { index, coordinates ->
            Log.d("debug", "size:" + drawerCoordinates.size.toString())
            if (index == drawerCoordinates.size - 1) {
                //Skip
            } else {
                if (index == 0) {
                    // First line start at x 550 and y 900
                    canvas.drawLine(xOffset, yOffset, (drawerCoordinates[index].x * scalingFactor) + xOffset, (drawerCoordinates[index].y * scalingFactor) + yOffset, greenPaint)
                } else {
                    canvas.drawLine(
                        (drawerCoordinates[index].x * scalingFactor) + xOffset,
                        (drawerCoordinates[index].y * scalingFactor)+ yOffset,
                        (drawerCoordinates[index + 1].x * scalingFactor) + xOffset,
                        (drawerCoordinates[index + 1].y * scalingFactor) + yOffset,
                        redPaint)
                    if (drawerCoordinates[index + 1].classification != null) {
                        canvas.drawPoint(
                            (drawerCoordinates[index + 1].x * scalingFactor) + xOffset,
                            (drawerCoordinates[index + 1].y * scalingFactor) + yOffset,
                            bluePaint)
                        //Draw text last to prevent path blocking text.
                        val collisionObject = Coordinates(drawerCoordinates[index + 1].x, drawerCoordinates[index + 1].y, drawerCoordinates[index + 1].classification)
                        collisionObjectCoordinates.add(collisionObject)
                    }
                }
            }
        }
        drawCollisionObjectsMessage(canvas)
    }

    private fun drawCollisionObjectsMessage(canvas: Canvas) {
        collisionObjectCoordinates.forEachIndexed { index, coordinates ->
            canvas.drawText(
                collisionObjectCoordinates[index].classification.toString(),
                (collisionObjectCoordinates[index].x * scalingFactor) + xOffset - xTextOffset,
                (collisionObjectCoordinates[index].y * scalingFactor) + yOffset - yTextOffset,
                textPaint)
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