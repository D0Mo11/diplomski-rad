package com.domagojdragic.diplomskirad.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.domagojdragic.diplomskirad.R
import com.domagojdragic.diplomskirad.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        setUser()
        handleOverflow()
        handleButton()
    }

    private fun handleButton() {
        binding.btnGenerate.setOnClickListener {
            val intent = Intent(this, AnnotationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleOverflow() {
        binding.overflowToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.signOut -> {
                    MaterialAlertDialogBuilder(this)
                        .setMessage(getString(R.string.supporting_text))
                        .setPositiveButton(getString(R.string.accept)) { _, _ ->
                            firebaseAuth.signOut()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton(getString(R.string.decline)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUser() {
        val user = firebaseAuth.currentUser
        if (user != null) {
            binding.tvName.text = user.email.toString()
        }
    }
}
