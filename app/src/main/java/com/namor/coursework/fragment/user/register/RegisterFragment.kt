package com.namor.coursework.fragment.user.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter

import com.namor.coursework.R
import com.namor.coursework.base.TextChangedListener
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LOGIN = "login"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : MvpAppCompatFragment(), RegisterUserView {

    private var login: String = ""

    @InjectPresenter
    lateinit var presenter: RegisterUserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(LOGIN) ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneButton.setOnClickListener { onDoneClicked() }

        val textWatcher = TextChangedListener { checkInput() }
        arrayOf(fioInput, emailInput, loginInput)
                .forEach { it.editText?.addTextChangedListener(textWatcher) }

        loginInput.editText?.setText(login)

        setIsValid(false)
        setEmailError(false)
        setFioError(false)
    }

    private fun onDoneClicked() {
        val login = loginInput.editText?.text.toString()
        val fio = fioInput.editText?.text.toString()
        val email = emailInput.editText?.text.toString()
        val sex = if (switch1.isChecked) 'm' else 'ж'

        presenter.register(login, fio, email, sex)
    }

    override fun back() {
        activity?.onBackPressed()
    }

    override fun setFioError(fioValid: Boolean) {
        fioInput.error = when (fioValid) {
            false -> "Введите имя"
            true -> null
        }
    }

    override fun setEmailError(emailValid: Boolean) {
        emailInput.error = when (emailValid) {
            false -> "Введите корректный email"
            true -> null
        }
    }

    override fun setLoginError(loginValid: Boolean) {
        loginInput.error = when (loginValid) {
            false -> "Логин занят"
            true -> null
        }
    }

    override fun setIsValid(inputValid: Boolean) {
        doneButton.isClickable = inputValid
        doneButton.alpha = when (inputValid) {
            true -> 1.0F
            false -> 0.5F
        }
    }

    private fun checkInput() {
        val login = loginInput.editText?.text.toString()
        presenter.checkLogin(login)

        val fio = fioInput.editText?.text.toString()
        presenter.checkFio(fio)

        val email = emailInput.editText?.text.toString()
        presenter.checkEmail(email)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param login user login.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(login: String) =
                RegisterFragment().apply {
                    arguments = Bundle().apply {
                        putString(LOGIN, login)
                    }
                }
    }
}
