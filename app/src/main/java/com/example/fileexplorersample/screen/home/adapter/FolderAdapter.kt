package com.example.fileexplorersample.screen.home.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fileexplorersample.R
import com.example.fileexplorersample.databinding.ItemFolderBinding
import com.example.fileexplorersample.util.dateFormatMM_dd_yy_hh_mm
import com.example.fileexplorersample.util.isImageFile
import java.io.File
import java.util.*


class FolderAdapter(
    private val context: Context
) : RecyclerView.Adapter<FolderAdapter.ViewHolder>() {
    private var data: List<File> = listOf()
    var listener: FolderListener? = null


    fun submitList(
        data: List<File>?,
    ) {
        data?.let { d ->
            this.data = listOf()
            this.data = d
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context ,data[position], position, listener)
    }

    class ViewHolder private constructor(private val binding: ItemFolderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context ,item: File, position: Int, listener: FolderListener?) {
            if (item.exists()) {
                binding.apply {
                    tvFolderName.text = item.name
                    tvFolderDate.text = dateFormatMM_dd_yy_hh_mm(Date(item.lastModified()))

                    when {
                        item.isDirectory ->  {
                            ivFolderImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_folder))
                            tvFolderChild.visibility = View.VISIBLE
                            tvFolderChild.text = "${item.list()?.size ?: "0"} item"
                        }
                        isImageFile(item) -> {
                            val options = BitmapFactory.Options()
                            val bitmap = BitmapFactory.decodeFile(item.absolutePath, options)
                            Glide
                                .with(context)
                                .load(bitmap)
                                .circleCrop()
                                .into(binding.ivFolderImg)
                        }
                        else -> {
                            ivFolderImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_file))
                        }
                    }

                }
            }

            binding.root.setOnClickListener {
                listener?.onClick(position)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFolderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

interface FolderListener {
    fun onClick(position: Int)
}