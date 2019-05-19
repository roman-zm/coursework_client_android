package com.namor.coursework.fragment.user.comments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import com.namor.coursework.R
import com.namor.coursework.base.MessageInputPanel
import com.namor.coursework.domain.Film
import kotlinx.android.synthetic.main.fragment_film_comments.*

private const val FILM = "film"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmCommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmCommentsFragment : MvpAppCompatFragment(), FilmCommentsView {
    // TODO: Rename and change types of parameters
    private lateinit var film: Film

    @InjectPresenter
    lateinit var presenter: FilmCommentsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val film = it.getParcelable<Film>(FILM)
            if (film != null) this.film = film
            else fragmentManager?.popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        recycler.adapter = presenter.adapter
        recycler.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (oldBottom > bottom) {
                recycler.post { recycler.scrollToPosition(presenter.adapter.itemCount - 1) }
            }
        }

        chatInputPanel.setSendButtonEnabled(true)
        chatInputPanel.setOnSendClickListener(object: MessageInputPanel.OnSendListener {
            override fun onSend(message: MessageInputPanel.IMessage) {
                presenter.sendComment(message)
            }
        })

        presenter.film = film
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun scrollToBottom() {
        recycler.post { recycler.scrollToPosition(presenter.adapter.itemCount - 1) }
    }

    override fun finish() {
        fragmentManager?.popBackStack()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param film Film to comment
         * @return A new instance of fragment FilmCommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(film: Film) =
                FilmCommentsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(FILM, film)
                    }
                }
    }
}
