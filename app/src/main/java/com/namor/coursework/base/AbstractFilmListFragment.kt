package com.namor.coursework.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.CourseworkFragment
import com.namor.coursework.fragment.admin.FilmListPresenter
import com.namor.coursework.fragment.admin.FilmListView
import com.namor.coursework.fragment.admin.adapter.FILM
import com.namor.coursework.fragment.admin.adapter.FilmAdapter
import com.namor.coursework.fragment.admin.adapter.FilmViewHolder

abstract class AbstractFilmListFragment: CourseworkFragment(), FilmListView {

    @InjectPresenter
    lateinit var filmListPresenter: FilmListPresenter

    protected val adapter = FilmAdapter.newInstance()

    protected var filmListRecycler: RecyclerView? = null
    set(value) {
        field = value
        initRecycler()
        if (adapter.items.isNullOrEmpty()) filmListPresenter.loadFilmList()
    }

    private fun initRecycler() {
        filmListRecycler?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        filmListRecycler?.adapter = adapter.apply {
            setHolderListener(FILM, object : FilmViewHolder.FilmHolderListener {
                override fun clicked(pos: Int) {
                    onFilmClicked(pos)
                }
            })
        }

    }

    abstract fun onFilmClicked(pos: Int)

    fun getFilm(pos: Int) = adapter.items.getOrNull(pos)

    override fun setFilms(films: List<Film>) {
        adapter.items = films
        adapter.notifyDataSetChanged()
    }

}