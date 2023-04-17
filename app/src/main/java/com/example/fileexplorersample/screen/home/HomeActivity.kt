package com.example.fileexplorersample.screen.home

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fileexplorersample.R
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.data.repository.FileRepository
import com.example.fileexplorersample.databinding.ActivityHomeBinding
import com.example.fileexplorersample.screen.home.fragment.FolderFragment
import com.example.fileexplorersample.screen.home.fragment.FolderViewModel
import com.example.fileexplorersample.screen.home.fragment.FolderViewModelFactory
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.pushFragment
import java.util.*

@RequiresApi(Build.VERSION_CODES.R)
class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var baseViewModel = BaseViewModel()
    private val viewModel by lazy { baseViewModel as HomeViewModel }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
//            pushFragment(
//                FolderFragment()
//            )
        } else {
            Toast.makeText(this, "Permission is denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val viewModelFactory = HomeViewModelFactory(fileRepository = FileRepository())
        baseViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        setup()
        setupAction()
        setupObserver()
    }

    private fun setup(){
        viewModel.getMimeTypeSize(this)
    }

    private fun setupAction(){

    }

    private fun setupObserver(){

    }

}