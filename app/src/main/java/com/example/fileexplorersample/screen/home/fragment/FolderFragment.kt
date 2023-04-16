package com.example.fileexplorersample.screen.home.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
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
import com.example.fileexplorersample.util.WTF
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = FolderViewModelFactory(fileRepository = FileRepository())
        baseViewModel = ViewModelProvider(this, viewModelFactory)[FolderViewModel::class.java]
        loadFiles()
        setup()
        setupAction()
        setupObserver()
    }

    private fun setup() {
        if (parentFragmentManager.backStackEntryCount > 1) {
            binding.tvFolderName.text = path?.substringAfterLast("/")
            binding.ivBack.visibility = View.VISIBLE
        } else {
            binding.tvFolderName.text = "External Storage"
            binding.ivBack.visibility = View.GONE
        }

        folderAdapter = FolderAdapter(requireActivity())
        folderAdapter.listener = object : FolderListener {
            override fun onClick(position: Int) {
                val path = viewModel.file.value?.get(position)?.absolutePath
                path?.let {
                    if (File(it).exists() && File(it).isDirectory) {
                        requireActivity().pushFragment(
                            instance(
                                parentPath =  it
                            )
                        )
                    } else {
                        Toast.makeText(requireContext(), "Handling Open File", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.rvFolder.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = folderAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        WTF("asd")
        inflater.inflate(R.menu.file_option, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.option_sort_by_name -> Unit
            R.id.option_sort_by_type -> Unit
            R.id.option_sort_by_date -> Unit
            R.id.option_sort_by_size -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAction() {

    }

    private fun  loadFiles() {
        path?.let {filePath ->
            viewModel.getFiles(filePath)
        } ?: run {
            viewModel.getFiles()
        }

    }

    private fun setupObserver() {
        viewModel.file.observe(viewLifecycleOwner) {
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
}