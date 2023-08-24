package com.domagojdragic.diplomskirad.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class AnnotationCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : View(context, attrs, defStyle) {

    private var annotationObjectType = AnnotationObjectType.FIRE
    private var saveShape = false
    private val annotationPointsFire = mutableListOf<PointF>()
    private val annotationPointsSmoke = mutableListOf<PointF>()
    private var annotationFireShapes = mutableListOf<List<PointF>>()
    private var annotationSmokeShapes = mutableListOf<List<PointF>>()
    private val firePaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 8f
        isAntiAlias = true
    }
    private val smokePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 8f
        isAntiAlias = true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        drawFirePoints(canvas, firePaint)
        drawSmokePoints(canvas, smokePaint)
        drawFireLines(canvas, firePaint)
        drawSmokeLines(canvas, smokePaint)
        drawFireShape(canvas, firePaint)
        drawSmokeShape(canvas, smokePaint)

        if (saveShape) {
            when (annotationObjectType) {
                AnnotationObjectType.FIRE -> saveFireShape()
                AnnotationObjectType.SMOKE -> saveSmokeShape()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (annotationObjectType == AnnotationObjectType.FIRE) {
                        annotationPointsFire.add(PointF(event.x, event.y))
                    }
                    if (annotationObjectType == AnnotationObjectType.SMOKE) {
                        annotationPointsSmoke.add(PointF(event.x, event.y))
                    }
                    invalidate()
                }
            }
        }
        return true
    }

    private fun drawFirePoints(canvas: Canvas, paint: Paint) {
        for (point in annotationPointsFire) {
            canvas.drawPoint(point.x, point.y, paint)
        }
    }

    private fun drawSmokePoints(canvas: Canvas, paint: Paint) {
        for (point in annotationPointsSmoke) {
            canvas.drawPoint(point.x, point.y, paint)
        }
    }

    private fun drawFireLines(canvas: Canvas, paint: Paint) {
        for (i in 1 until annotationPointsFire.size) {
            val startPoint = annotationPointsFire[i - 1]
            val endPoint = annotationPointsFire[i]
            canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint)
        }
    }

    private fun drawSmokeLines(canvas: Canvas, paint: Paint) {
        for (i in 1 until annotationPointsSmoke.size) {
            val startPoint = annotationPointsSmoke[i - 1]
            val endPoint = annotationPointsSmoke[i]
            canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint)
        }
    }

    private fun drawFireShape(canvas: Canvas, paint: Paint) {
        annotationFireShapes.forEach { points ->
            for (i in 1 until points.size) {
                val startPoint = points[i - 1]
                val endPoint = points[i]
                canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint)
            }
        }
    }

    private fun drawSmokeShape(canvas: Canvas, paint: Paint) {
        annotationSmokeShapes.forEach { points ->
            for (i in 1 until points.size) {
                val startPoint = points[i - 1]
                val endPoint = points[i]
                canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint)
            }
        }
    }

    private fun saveFireShape() {
        annotationFireShapes.add(annotationPointsFire.toList())
        annotationPointsFire.clear()
        saveShape = false
        invalidate()
    }

    private fun saveSmokeShape() {
        annotationSmokeShapes.add(annotationPointsSmoke.toList())
        annotationPointsSmoke.clear()
        saveShape = false
        invalidate()
    }

    fun updateObjectType(objectType: AnnotationObjectType) {
        annotationObjectType = objectType
        invalidate()
    }

    fun saveCurrentShape() {
        saveShape = true
        invalidate()
    }

    fun getFireShapes(): List<List<PointF>> = annotationFireShapes.toList()
    fun getSmokeShapes(): List<List<PointF>> = annotationSmokeShapes.toList()

    enum class AnnotationObjectType {
        FIRE, SMOKE;
    }
}
