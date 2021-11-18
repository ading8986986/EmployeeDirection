package com.example.employeedirection.feature.employee_list.presentation.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.employeedirection.R
import com.example.employeedirection.databinding.LayoutEmployeeListItemBinding
import com.example.employeedirection.feature.employee_list.domain.model.Employee
import com.example.employeedirection.feature.employee_list.presentation.component.EmployeeListItemViewHolder

class EmployeeListAdapter :
    RecyclerView.Adapter<EmployeeListItemViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var employeesList: List<Employee>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListItemViewHolder {
        val viewBinding = LayoutEmployeeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeListItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: EmployeeListItemViewHolder, position: Int) {
        holder.bind(employeesList[position])
    }

    override fun getItemCount(): Int {
        return employeesList.size
    }
}