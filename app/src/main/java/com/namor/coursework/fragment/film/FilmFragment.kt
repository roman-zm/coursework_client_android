package com.namor.coursework.fragment.film


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

import com.namor.coursework.R
import com.namor.coursework.domain.Film
import kotlinx.android.synthetic.main.fragment_film.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val FILM = "film"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmFragment : Fragment() {
    private var film: Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            film = it.getParcelable(FILM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film?.let {
            loadFilm(it)
        }
    }

    private fun loadFilm(film: Film) {
        nameEdit.setText(film.name)
        directorEdit.setText(film.director)
        actorsEdit.setText(film.actors)
        yearEdit.setText("${film.year}")
        durationEdit.setText(film.duration)
        priceEdit.setText("${film.price}")

        deleteButton.isVisible = true
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param film Film to edit, must be null for create new one
         * @return A new instance of fragment FilmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(film: Film? = null) =
                FilmFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(FILM, film)
                    }
                }
    }
}
