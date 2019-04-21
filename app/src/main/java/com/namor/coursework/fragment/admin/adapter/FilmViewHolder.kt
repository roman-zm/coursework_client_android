package com.namor.coursework.fragment.admin.adapter

import android.view.View
import com.namor.coursework.adapter.diffutil.BindableViewHolder
import com.namor.coursework.adapter.diffutil.DiffUtilable
import com.namor.coursework.adapter.diffutil.ViewHolderListener
import com.namor.coursework.domain.Film
import kotlinx.android.synthetic.main.film_cell_view.view.*

class FilmViewHolder(v: View) : BindableViewHolder(v) {
    private var listener: FilmHolderListener? = null

    init {
        itemView.setOnClickListener {
            listener?.clicked(adapterPosition)
        }
    }

    override fun bindView(model: DiffUtilable?) {
        if (model is Film) {
            itemView.name.text = model.name
            itemView.director.text = model.director
            itemView.year.text = "${model.year}"
            itemView.duration.text = model.duration
        }
    }

    override fun setHolderListener(holderListener: ViewHolderListener?) {
        super.setHolderListener(holderListener)
        if (holderListener is FilmHolderListener)
            this.listener = holderListener
    }

    interface FilmHolderListener: ViewHolderListener {
        fun clicked(pos: Int)
    }
}
