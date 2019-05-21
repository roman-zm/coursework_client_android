package com.namor.coursework.fragment.user.film


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.view.plusAssign
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.chip.Chip
import com.namor.coursework.MainListener

import com.namor.coursework.R
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Genre
import com.namor.coursework.fragment.CourseworkFragment
import kotlinx.android.synthetic.main.fragment_user_film.*

private const val FILM = "film"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFilmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFilmFragment : CourseworkFragment(), UserFilmView {

    @InjectPresenter
    lateinit var presenter: UserFilmPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Film>(FILM).let {
            presenter.setFilm(it)
        }
        userRatingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    if (fromUser) presenter.onRatingChanged(rating)
                }

        commentButton.setOnClickListener { presenter.openComments() }
    }

    override fun setTitle(name: String) {
        filmTitle.text = name
    }

    @SuppressLint("SetTextI18n")
    override fun setInfo(director: String, year: Int, duration: String) {
        infoText.text = "$director, $year, $duration"
    }

    override fun setGenres(genres: Set<Genre>) {
        genres.forEach {
            chipGroup += Chip(context).apply {
                text = it.name
                setOnClickListener { _ ->
                    presenter.showInfo(it.name)
                }
            }
        }
    }

    override fun showInfo(description: String) {
        Toast.makeText(context, description, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    override fun setPrice(price: Double) {
        buyOrSeeButton.setOnClickListener {
            presenter.purchase()
            buyOrSeeButton.setOnClickListener(null)
        }
        buyOrSeeButton.text = "$price\u20BD"
    }

    @SuppressLint("SetTextI18n")
    override fun setActors(actors: String) {
        actorsText.text = "Актёры: $actors"
    }

    override fun setSeeButton() {
        buyOrSeeButton.text = "Смотреть"
        buyOrSeeButton.setOnClickListener {
            Toast.makeText(context, "Запускается плеер с фильмом", Toast.LENGTH_LONG)
                    .show()
        }
    }

    override fun setMark(mark: Float) {
        filmRatingBar.rating = mark
        ratingText.text = ((mark * 10).toInt() / 10.0f).toString()
    }

    override fun setCount(count: Int) {
        ratingCount.text = "$count"
    }

    override fun setSelfMark(mark: Int) {
        userRatingBar.rating = (mark.toFloat() * 10).toInt() / 10.0f
    }

    override fun openComments(film: Film) {
        listener.openComments(film)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment UserFilmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(mainListener: MainListener, film: Film) =
                UserFilmFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(FILM, film)
                    }
                    listener = mainListener
                }
    }
}
