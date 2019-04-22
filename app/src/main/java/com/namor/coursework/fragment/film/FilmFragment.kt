package com.namor.coursework.fragment.film


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import com.namor.coursework.R
import com.namor.coursework.domain.Film
import kotlinx.android.synthetic.main.fragment_admin_or_user.view.*
import kotlinx.android.synthetic.main.fragment_film.*
import java.lang.IllegalStateException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val FILM = "film"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmFragment : MvpAppCompatFragment(), FilmEditView {

    @InjectPresenter
    lateinit var presenter: FilmEditPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val film = it.getParcelable<Film?>(FILM)
            presenter.loadFilm(film)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getInfo()
    }

    override fun loadFilm(film: Film) {
        nameEdit.setText(film.name)
        directorEdit.setText(film.director)
        actorsEdit.setText(film.actors)
        yearEdit.setText("${film.year}")
        durationEdit.setText(film.duration)
        priceEdit.setText("${film.price}")

        deleteButton.isVisible = true

        deleteButton.setOnClickListener { presenter.deleteFilm() }
        doneButton.setOnClickListener { presenter.uploadFilm(createFilmModel()) }
    }

    override fun initForNewFilm() {
        deleteButton.isVisible = false
        doneButton.setOnClickListener { presenter.uploadFilm(createFilmModel()) }
    }

    override fun showError(message: String?) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun close() {
        activity?.onBackPressed()
    }

    private fun createFilmModel() = try {
        Film(
                -1,
                nameEdit.text?.toString() ?: throw IllegalStateException(),
                "",
                directorEdit.text?.toString() ?: throw IllegalStateException(),
                yearEdit.text?.toString()?.toIntOrNull() ?: throw IllegalStateException(),
                actorsEdit?.text?.toString() ?: throw IllegalStateException(),
                priceEdit?.text?.toString()?.toDoubleOrNull() ?: throw IllegalStateException(),
                durationEdit?.text?.toString() ?: throw IllegalStateException()
        )
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        null
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
