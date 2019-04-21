package com.namor.coursework.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.namor.coursework.MainListener
import com.namor.coursework.R
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.CourseworkFragment
import com.namor.coursework.fragment.admin.adapter.FILM
import com.namor.coursework.fragment.admin.adapter.FilmAdapter
import com.namor.coursework.fragment.admin.adapter.FilmViewHolder
import kotlinx.android.synthetic.main.fragment_admin.*

class AdminFragment : CourseworkFragment(), AdminView {

    @InjectPresenter
    lateinit var presenter: AdminPresenter

    private val adapter = FilmAdapter.newInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        presenter.loadFilmList()
    }

    private fun initRecycler() {
        recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter.apply {
            setHolderListener(FILM, object: FilmViewHolder.FilmHolderListener {
                override fun clicked(pos: Int) {
                    onFilmClicked(pos)
                }
            })
        }
    }

    private fun onFilmClicked(pos: Int) {
        val film = adapter.items.getOrNull(pos)
        listener.openFilmFragment(film)
    }

    override fun setFilms(films: List<Film>) {
        adapter.items = films
        adapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AdminFragment.
         */
        @JvmStatic
        fun newInstance(mainListener: MainListener) =
                AdminFragment().apply {
                    listener = mainListener
                }
    }
}
