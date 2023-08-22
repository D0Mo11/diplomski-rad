package com.domagojdragic.diplomskirad.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.domagojdragic.diplomskirad.R
import com.domagojdragic.diplomskirad.databinding.ActivityAnnotationBinding
import com.domagojdragic.diplomskirad.di.MAIN_DISPATCHER
import com.domagojdragic.diplomskirad.viewmodel.AnnotationViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.io.File

private const val OBJECT_FIRE = "FIRE"
private const val OBJECT_SMOKE = "SMOKE"

class AnnotationActivity : AppCompatActivity() {

    private val mainDispatcher = named(MAIN_DISPATCHER)
    private val mainScope: CoroutineScope = CoroutineScope(get(mainDispatcher))
    private lateinit var binding: ActivityAnnotationBinding
    private var annotationObjectType = OBJECT_FIRE
    private val annotationViewModel: AnnotationViewModel by viewModel()
    private val storageRef = Firebase.storage.reference
    private val annotationPoints = mutableListOf<PointF>()
    private var paint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 5f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnotationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomBar()
        setImage()
        handleCanvasClick()
    }

    inner class AnnotationCanvas(context: Context) : View(context) {

        init {
            binding.annotationCanvasView.setWillNotDraw(false)
            Log.d("DRAW", "Init Canvas")
        }
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            if (canvas == null) return
            Log.d("DRAW", "onDraw called")

            paint.apply {
                color = if (annotationObjectType == OBJECT_FIRE) Color.GRAY else Color.RED
            }
            val drawable = binding.annotationImage.drawable
            drawable?.let {
                it.setBounds(0, 0, width, height)
                it.draw(canvas)
            }
            for (point in annotationPoints) {
                canvas.drawPoint(point.x, point.y, paint)
            }

            for (i in 1 until annotationPoints.size) {
                val startPoint = annotationPoints[i - 1]
                val endPoint = annotationPoints[i]
                canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint)
            }
        }
    }

    private fun setImage() {
        mainScope.launch {
            annotationViewModel.imagesState.collect { images ->
                if (images.isNotEmpty()) {
                    val imageName = annotationViewModel.getImageName(images)
                    val imageRef = storageRef.child(imageName)
                    val localFile = File.createTempFile("tempImage", "jpg")
                    imageRef.getFile(localFile).addOnSuccessListener {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        binding.annotationImage.setImageBitmap(bitmap)
                    }.addOnFailureListener {
                        throw Exception("${it.message}")
                    }
                }
            }
        }
    }

    private fun setBottomBar() {
        binding.bottomBar.selectedItemId = R.id.fireClass
        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fireClass -> {
                    annotationObjectType = OBJECT_FIRE
                    true
                }
                R.id.smokeClass -> {
                    annotationObjectType = OBJECT_SMOKE
                    true
                }
                else -> false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleCanvasClick() {
        binding.annotationCanvasView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    annotationPoints.add(PointF(motionEvent.x, motionEvent.y))
                    binding.annotationCanvasView.postInvalidate()
                    Log.d("DRAW", "Point added: ${motionEvent.x}, ${motionEvent.y} ")
                }
            }
            true
        }
    }
}
