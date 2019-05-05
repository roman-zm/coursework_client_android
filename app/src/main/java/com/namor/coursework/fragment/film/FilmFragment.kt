package com.namor.coursework.fragment.film


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        chipsText.setText(film.genres.map { it.name }.toList())
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

    override fun setPredictions(predictions: Array<String>) {
        val adapter = ArrayAdapter<String>(
                context, android.R.layout.simple_dropdown_item_1line, predictions
        )
        chipsText.setAdapter(adapter)
    }

    private fun createFilmModel() = try {
        val name = nameEdit.text?.toString()
        val director = directorEdit.text?.toString()
        val year = yearEdit.text?.toString()
        val actors = actorsEdit?.text?.toString()
        val price = priceEdit?.text?.toString()
        val duration = durationEdit?.text?.toString()

        val genresSet = presenter.getGenresSet(chipsText.chipValues)

        Film(
                -1,
                nameEdit.text?.toString() ?: throw IllegalStateException(),
                "",
                directorEdit.text?.toString() ?: throw IllegalStateException(),
                yearEdit.text?.toString()?.toIntOrNull() ?: throw IllegalStateException(),
                actorsEdit?.text?.toString() ?: throw IllegalStateException(),
                priceEdit?.text?.toString()?.toDoubleOrNull() ?: throw IllegalStateException(),
                durationEdit?.text?.toString() ?: throw IllegalStateException(),
                genresSet
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
