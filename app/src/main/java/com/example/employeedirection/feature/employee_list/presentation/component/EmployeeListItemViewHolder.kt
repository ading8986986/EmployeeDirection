package com.example.employeedirection.feature.employee_list.presentation.component

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.employeedirection.R
import com.example.employeedirection.databinding.LayoutEmployeeListItemBinding
import com.example.employeedirection.feature.employee_list.domain.model.Employee


class EmployeeListItemViewHolder (
    val viewbinding: LayoutEmployeeListItemBinding
) :RecyclerView.ViewHolder(viewbinding.root) {

    fun bind(employee: Employee) {
        val context = viewbinding.root.context

        viewbinding.employeeItem = employee

        Glide.with(context)
            .load(employee.photoUrlSmall)
            .override(viewbinding.headImage.measuredWidth)
            .placeholder(R.drawable.ic_head_image)
            .error(R.drawable.ic_error)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    viewbinding.headImage.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    viewbinding.headImage.setImageDrawable(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    viewbinding.headImage.setImageDrawable(errorDrawable)
                }

            })
    }
}