package com.namor.coursework.fragment.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.namor.coursework.MainListener
import com.namor.coursework.R
import com.namor.coursework.base.AbstractFilmListFragment
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : AbstractFilmListFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmListRecycler = recycler
        initToolbar()
    }

    private fun initToolbar() {
        val searchItem = toolbar.menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView
        if (searchView is SearchView) {
            searchView.imeOptions = EditorInfo.IME_ACTION_DONE
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filmListPresenter.onFilter(newText ?: "")
                    return false
                }
            })
        }
    }

    override fun onFilmClicked(pos: Int) {
        val film = getFilm(pos)
        film?.let {
            listener.openUserFilmFragment(film)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(listener: MainListener) =
                UserFragment().apply {
                    this.listener = listener
                }
    }
}
