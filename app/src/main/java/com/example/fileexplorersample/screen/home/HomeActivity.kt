package com.example.fileexplorersample.screen.home

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fileexplorersample.R
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.common.Constants
import com.example.fileexplorersample.common.MimeTypeIdentify
import com.example.fileexplorersample.data.repository.FileRepository
import com.example.fileexplorersample.databinding.ActivityHomeBinding
import com.example.fileexplorersample.screen.home.fragment.FolderFragment
import com.example.fileexplorersample.screen.home.fragment.FolderViewModel
import com.example.fileexplorersample.screen.home.fragment.FolderViewModelFactory
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.formatFileSize
import com.example.fileexplorersample.util.pushFragment
import java.util.*

class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private var baseViewModel = BaseViewModel()
    private val viewModel by lazy { baseViewModel as HomeViewModel }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.getMimeTypeSize(this)
            viewModel.getRootFolderInfo(this)
        } else {
            Toast.makeText(this, "Permission is denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount >= 1) {
            binding.content.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
        } else {
            binding.content.visibility = View.VISIBLE
            binding.container.visibility = View.GONE
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

    }

    private fun setupAction(){
        binding.containerRootInfo.setOnClickListener {
            navigateToFolderFragment()
        }

        binding.containerImageUsage.setOnClickListener {
            navigateToFolderFragment("/storage/emulated/0/Pictures")
        }

        binding.containerAudioUsage.setOnClickListener {
            navigateToFolderFragment("/storage/emulated/0/Music")
        }

        binding.containerDocumentUsage.setOnClickListener {
            navigateToFolderFragment("/storage/emulated/0/documents")
        }

        binding.containerVideoUsage.setOnClickListener {
            navigateToFolderFragment("/storage/emulated/0/Movies")
        }

        binding.containerOtherUsage.setOnClickListener {
            navigateToFolderFragment()
        }
    }

    private fun navigateToFolderFragment(path: String = "") {
        binding.content.visibility = View.GONE
        binding.container.visibility = View.VISIBLE
        pushFragment(
            FolderFragment.instance(
                path
            )
        )
    }

    private fun setupObserver(){
        viewModel.mimeTypeSize.observe(this){
            it?.let { mimeTypSizes ->
                binding.imageSize = mimeTypSizes[MimeTypeIdentify.IMAGES]
                binding.videoSize = mimeTypSizes[MimeTypeIdentify.VIDEOS]
                binding.audioSize = mimeTypSizes[MimeTypeIdentify.AUDIO]
                binding.documentSize = mimeTypSizes[MimeTypeIdentify.DOCUMENTS]
                binding.others = mimeTypSizes[MimeTypeIdentify.OTHERS]
            }
        }

        viewModel.rootInfo.observe(this) {
            it?.let { rootInfo ->
                binding.rootTotalSpace = rootInfo[Constants.ROOT_TOTAL_SPACE]
                binding.rootFreeSpace = rootInfo[Constants.ROOT_FREE_SPACE]
            }
        }
    }

}