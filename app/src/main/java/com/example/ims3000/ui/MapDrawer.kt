package com.example.ims3000.ui

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Log

class MapDrawer : Drawable() {

    private val redPaint: Paint = Paint().apply { setARGB(255, 255, 0, 0) }
    private val anotherPaint: Paint = Paint().apply { setARGB(255, 0, 255, 0) }

    private val xCords = mutableListOf<Float>()
    private val yCords = mutableListOf<Float>()

    val xStart: Float = 0.0f
    val yStart: Float = 0.0f
    val xEnd: Float = 0.0f
    val yEnd: Float = 0.0f

    fun drawLines(canvas: Canvas, xStart: Float, yStart: Float, xEnd: Float, yEnd: Float) {
        canvas.drawLine(xStart, yStart, xEnd, yEnd, anotherPaint)
    }

    override fun draw(canvas: Canvas) {
        redPaint.strokeWidth = 50F
        canvas.drawLine(200F, 200F, 600F, 500F, redPaint)
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