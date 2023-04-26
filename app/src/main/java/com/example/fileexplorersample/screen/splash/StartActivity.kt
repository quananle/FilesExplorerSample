package com.example.fileexplorersample.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fileexplorersample.R
import com.example.fileexplorersample.databinding.ActivityStartBinding
import com.example.fileexplorersample.screen.home.HomeActivity

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)

        setup()
    }

    private fun setup() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            navigateToHome()
        }, 2000)
    }

    private fun navigateToHome() {
        Intent(this@StartActivity, HomeActivity::class.java).apply {
            startActivity(this)
            this@StartActivity.finish()
        }
    }
}