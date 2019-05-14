package com.namor.coursework.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.namor.coursework.MainListener
import com.namor.coursework.R
import com.namor.coursework.base.AbstractFilmListFragment
import kotlinx.android.synthetic.main.fragment_admin.*

class AdminFragment : AbstractFilmListFragment() {

//    @InjectPresenter
//    override lateinit var filmListPresenter: FilmListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmListRecycler = recycler
        addButton.setOnClickListener { listener.openFilmFragment(null) }
    }

//    private fun initRecycler() {
//        recycler.layoutManager =
//                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        recycler.adapter = adapter.apply {
//            setHolderListener(FILM, object: FilmViewHolder.FilmHolderListener {
//                override fun clicked(pos: Int) {
//                    onFilmClicked(pos)
//                }
//            })
//        }
//    }

    override fun onFilmClicked(pos: Int) {
        val film = getFilm(pos)
        listener.openFilmFragment(film)
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
