package com.example.fileexplorersample.screen.home

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fileexplorersample.R
import com.example.fileexplorersample.databinding.ActivityHomeBinding
import com.example.fileexplorersample.screen.home.fragment.FolderFragment
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.pushFragment
import java.util.*

@RequiresApi(Build.VERSION_CODES.R)
class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            pushFragment(
                FolderFragment()
            )
        } else {
            Toast.makeText(this, "Permission is denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}