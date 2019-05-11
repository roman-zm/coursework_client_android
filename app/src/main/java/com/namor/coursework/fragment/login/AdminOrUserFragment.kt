package com.namor.coursework.fragment.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.namor.coursework.MainListener
import com.namor.coursework.R
import com.namor.coursework.domain.Administrator
import com.namor.coursework.domain.User
import com.namor.coursework.fragment.CourseworkFragment
import kotlinx.android.synthetic.main.fragment_admin_or_user.*


/**
 * A simple [Fragment] subclass.
 * Use the [AdminOrUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminOrUserFragment : CourseworkFragment(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_or_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adminButton.setOnClickListener { onAdminLogin() }
        userButton.setOnClickListener { onUserLoginOrRegister() }
    }

    private fun onUserLoginOrRegister() {
        if (presenter.setHomeUrl(urlTextEdit.text)) {
            presenter.onUserLogin(loginTextEdit.text)
        }
    }

    private fun onAdminLogin() {
        if (presenter.setHomeUrl(urlTextEdit.text)) {
            presenter.onAdminLogin(loginTextEdit.text)
        }
    }

    override fun setError(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_LONG).show()
    }

    override fun openAdmin(administrator: Administrator) {
        listener.openAdminFragment(administrator)
    }

    override fun openUser(user: User) {
        listener.openUserFragment(user)
    }

    override fun registerUser(login: String) {
        listener.openRegisterFragment(login)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AdminOrUserFragment.
         */
        @JvmStatic
        fun newInstance(mainListener: MainListener) = AdminOrUserFragment().apply {
            listener = mainListener
        }
    }
}
