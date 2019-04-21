package com.namor.coursework.adapter.diffutil

import android.view.View

abstract class PayloadableViewHolder(itemView: View) : BindableViewHolder(itemView) {
    open fun bindView(model: DiffUtilable, payloads: List<Any>) {
        if (payloads.isEmpty()) bindView(model)
    }
}