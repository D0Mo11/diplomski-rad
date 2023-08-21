package com.domagojdragic.diplomskirad.view.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class AnnotationActivity : AppCompatActivity() {

    private val mainDispatcher = named(MAIN_DISPATCHER)
    private val mainScope: CoroutineScope = CoroutineScope(get(mainDispatcher))
    private lateinit var binding: ActivityAnnotationBinding
    private val annotationViewModel: AnnotationViewModel by viewModel()
    private val storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnotationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setImage()
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
}
