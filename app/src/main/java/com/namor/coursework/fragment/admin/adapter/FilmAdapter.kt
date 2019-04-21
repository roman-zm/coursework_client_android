package com.namor.coursework.fragment.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.namor.coursework.R
import com.namor.coursework.adapter.BaseAdapter
import com.namor.coursework.domain.Film

const val FILM = 1

class FilmAdapter: BaseAdapter<Film>() {
    override fun getItemViewType(position: Int) = FILM

    companion object {
        fun newInstance() = FilmAdapter().apply {
            registerRenderer(FILM) { parent: ViewGroup? ->
                val v = LayoutInflater.from(parent?.context)
                        .inflate(R.layout.film_cell_view, parent, false)

                FilmViewHolder(v)
            }
        }
    }
}