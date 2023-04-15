package com.example.fileexplorersample.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fileexplorersample.repository.HomeRepository
import com.example.fileexplorersample.R
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var viewModel = BaseViewModel()
    private val mViewModel by lazy { viewModel as HomeViewModel }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = HomeViewModelFactory(homeRepository = HomeRepository())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        setup()
        loadFiles()
        observerViewModel()
    }

    private fun setup() {

    }

    @SuppressLint("SdCardPath")
    private fun  loadFiles() {
        mViewModel.getFiles("/sdcard")
    }

    private fun observerViewModel() {
        mViewModel.file.observe(this) {
            it?.let {
                Log.e("WTF", "$it")
            }
        }
    }
}