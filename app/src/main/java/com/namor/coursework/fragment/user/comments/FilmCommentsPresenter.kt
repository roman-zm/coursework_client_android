package com.namor.coursework.fragment.user.comments

import android.text.format.DateUtils
import android.text.format.DateUtils.HOUR_IN_MILLIS
import android.view.LayoutInflater
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.R
import com.namor.coursework.adapter.BaseAdapter
import com.namor.coursework.adapter.diffutil.BindableViewHolder
import com.namor.coursework.adapter.diffutil.DiffUtilable
import com.namor.coursework.base.MessageInputPanel
import com.namor.coursework.domain.Comment
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.comment_view.view.*
import java.util.*

@InjectViewState
class FilmCommentsPresenter: BasePresenter<FilmCommentsView>() {
    val adapter = CommentAdapter.newInstance()

    var film: Film? = null
    set(value) {
        if (value != null) {
            field = value
            getComments()
        }
    }

    fun getComments() {
        compositeDisposable += App.service.userService?.getCommentsByFilm(film?.id ?: -1)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(::setComments, ::onError)
    }

    private fun setComments(commentList: List<Comment>) {
        adapter.items = commentList
        adapter.notifyDataSetChanged()
        viewState.scrollToBottom()
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.finish()
    }

    fun sendComment(message: MessageInputPanel.IMessage) {
        val user = App.currentUser
        user?.let {
            App.service.userService?.sendComment(
                    Comment(user = it, film = film?.id ?: -1, text = message.message)
            )?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe( {
                        getComments()
                    }, { t ->
                        t.printStackTrace()
                    } )
        }
    }
}

class CommentAdapter: BaseAdapter<Comment>() {
    override fun getItemViewType(position: Int) = 1

    companion object {
        fun newInstance() = CommentAdapter().apply {
            registerRenderer(1) {
                val view = LayoutInflater.from(it.context).inflate(
                        R.layout.comment_view, it, false
                )
                CommentViewHolder(view)
            }
        }
    }
}

class CommentViewHolder(view: View) : BindableViewHolder(view) {
    override fun bindView(model: DiffUtilable?) {
        if (model is Comment) {
            itemView.name.text = model.user.fio
            itemView.text.text = model.text
            itemView.date.text = model.date?.let {
                getTimeString(it.time)
            } ?: ""
        }
    }

    private fun getTimeString(millis: Long): String {

        return if (millis > System.currentTimeMillis()) "Только что"
        else DateUtils.getRelativeTimeSpanString(
                millis,
                System.currentTimeMillis(),
                0,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME
        ).toString()
    }

}

