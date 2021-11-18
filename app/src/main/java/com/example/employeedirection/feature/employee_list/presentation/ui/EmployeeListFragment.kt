package com.example.employeedirection.feature.employee_list.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.employeedirection.R
import com.example.employeedirection.databinding.FragmentEmployeeListBinding
import com.example.employeedirection.feature.employee_list.presentation.component.EmployeeListAdapter
import com.example.employeedirection.feature.employee_list.presentation.viewmodel.EmployeeListViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeListFragment : Fragment() {

    private lateinit var viewBinding: FragmentEmployeeListBinding
    private val viewModel: EmployeeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerView.adapter = EmployeeListAdapter()

        addObservers()
        viewModel.loadValidEmployList()

    }

    private fun addObservers() {
        viewModel.employeeList.observe(viewLifecycleOwner) {
            (viewBinding.recyclerView.adapter as EmployeeListAdapter).employeesList = it
        }

        viewModel.loadingViewVisibility.observe(viewLifecycleOwner) {
            viewBinding.loadingView.isVisible = it
        }

        viewModel.errorEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Snackbar.make(viewBinding.root, it, LENGTH_LONG).show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.load_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.valid -> {
                viewModel.loadValidEmployList()
                true
            }
            R.id.mal -> {
                viewModel.loadMalEmployList()
                true
            }
            R.id.empty -> {
                viewModel.loadEmptyEmployList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}