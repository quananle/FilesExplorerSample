package com.example.fileexplorersample.screen.home

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.fileexplorersample.Application
import com.example.fileexplorersample.R
import com.example.fileexplorersample.util.WTF
import com.example.fileexplorersample.util.formatFileSize
import com.google.android.material.progressindicator.LinearProgressIndicator


@BindingAdapter("setSpaceUsage")
fun setSpaceUsage(view: TextView, size: Long?) {
    size?.let {
        view.visibility = View.VISIBLE
        view.text = size.formatFileSize()
    } ?: run {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setSpaceFree")
fun setSpaceFree(view: TextView, size: Long?) {
    size?.let {
        view.visibility = View.VISIBLE
        view.text = Application.appContext.getString(R.string.home_storage_free_label, size.formatFileSize())
    } ?: run {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setSpaceTotal")
fun setSpaceTotal(view: TextView, size: Long?) {
    size?.let {
        view.visibility = View.VISIBLE
        view.text = Application.appContext.getString(R.string.home_total_storage_label, size.formatFileSize())
    } ?: run {
        view.visibility = View.GONE
    }
}


@BindingAdapter("setProgressIndicator")
fun setProgressIndicator(view: LinearProgressIndicator, size: Long?) {
    size?.let {
        val sizDivider = 100000
        view.progress = (size / sizDivider).toInt()
    } ?: run {
        view.progress = 0
    }
}

@BindingAdapter("setProgressMax")
fun setProgressMax(view: LinearProgressIndicator, total: Long?) {
    total?.let {
        val sizDivider = 100000
        view.max = ((total/5) / sizDivider).toInt()
    } ?: run {
        view.progress = 1000
    }
}

@BindingAdapter("setRootProgressMax")
fun setRootProgressMax(view: LinearProgressIndicator, total: Long?) {
    total?.let {
        val sizDivider = 100000
        view.max = (total / sizDivider).toInt()
    } ?: run {
        view.progress = 1000
    }
}