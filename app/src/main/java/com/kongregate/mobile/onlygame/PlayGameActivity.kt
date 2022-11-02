package com.kongregate.mobile.onlygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.kongregate.mobile.R
import com.kongregate.mobile.databinding.ActivityPlayGameBinding

class PlayGameActivity : AppCompatActivity() {
    private var _binding: ActivityPlayGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}