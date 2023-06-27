package com.domagojdragic.diplomskirad.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.domagojdragic.diplomskirad.databinding.ActivityAnnotationBinding

class AnnotationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnnotationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnotationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
