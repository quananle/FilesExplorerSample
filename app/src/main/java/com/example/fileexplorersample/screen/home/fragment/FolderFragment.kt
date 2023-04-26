package com.example.fileexplorersample.screen.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fileexplorersample.R
import com.example.fileexplorersample.base.viewmodel.BaseViewModel
import com.example.fileexplorersample.data.repository.FileRepository
import com.example.fileexplorersample.databinding.FragmentFolderBinding
import com.example.fileexplorersample.screen.home.adapter.FolderAdapter
import com.example.fileexplorersample.screen.home.adapter.FolderListener
import com.example.fileexplorersample.util.pushFragment
import java.io.File


class FolderFragment: Fragment() {
    private lateinit var binding: FragmentFolderBinding
    private lateinit var folderAdapter: FolderAdapter

    private var baseViewModel = BaseViewModel()
    private val viewModel by lazy { baseViewModel as FolderViewModel }

    private val path by lazy { arguments?.getString(PARENT_PATH) }

    companion object {
        const val PARENT_PATH = "PARENT_PATH"
        fun instance(parentPath: String) : FolderFragment = FolderFragment().apply {
            arguments = bundleOf().apply {
                putString(PARENT_PATH, parentPath)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_folder, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.file_option, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.option_sort_by_name -> viewModel.sortFilesByName()
            R.id.option_sort_by_type -> viewModel.filterFilesByType()
            R.id.option_sort_by_date -> viewModel.sortFilesByDate()
            R.id.option_sort_by_size -> viewModel.sortFilesBySize()
            R.id.option_search -> openSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = FolderViewModelFactory(fileRepository = FileRepository())
        baseViewModel = ViewModelProvider(this, viewModelFactory)[FolderViewModel::class.java]
        loadFiles()
        setup()
        setupAction()
        setupObserver()
    }

    private fun  loadFiles() {
        path?.let {filePath ->
            viewModel.getFiles(filePath)
        } ?: run {
            viewModel.getFiles()
        }
    }

    private fun setup() {
        if (activity is AppCompatActivity)
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.title = path?.let {
            if (it.isNotEmpty())
                it.substringAfterLast("/")
            else
                "External Storage"
        }

        folderAdapter = FolderAdapter(requireActivity())
        folderAdapter.listener = object : FolderListener {
            override fun onClick(position: Int, mimeType: String) {
                val path = viewModel.file.value?.get(position)?.absolutePath
                path?.let {
                    if (File(it).exists() && File(it).isDirectory) {
                        requireActivity().pushFragment(
                            instance(
                                parentPath =  it
                            )
                        )
                    } else {
                        try {
                            Intent().apply {
                                action = Intent.ACTION_VIEW
                                setDataAndType(Uri.parse(it), mimeType)
                                startActivity(this)
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireActivity(),
                                "Cannot open the file",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
        binding.rvFolder.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = folderAdapter
        }
    }

    private fun setupAction() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.emptyView.setOnClickListener {
            viewModel.getFiles()
        }
    }

    private fun setupObserver() {
        viewModel.file.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.setFilesFiltered(it)
            }
        }

        viewModel.filteredFiles.observe(viewLifecycleOwner) {
            it?.let {
                if(it.isNotEmpty()) {
                    folderAdapter.submitList(it)
                    binding.rvFolder.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                } else {
                    binding.rvFolder.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                }
            } ?: run {
                binding.rvFolder.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            }
        }
    }

    private fun openSearch() {
        if (binding.etSearch.isVisible) {
            binding.etSearch.visibility = View.GONE
        }else
            binding.etSearch.visibility = View.VISIBLE

        binding.etSearch.addTextChangedListener {
            it?.let { searchKeyword ->
                viewModel.onSearch(searchKeyword.toString())
            }
        }

    }
}