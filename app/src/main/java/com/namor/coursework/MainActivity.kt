package com.namor.coursework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.namor.coursework.domain.Administrator
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.User
import com.namor.coursework.fragment.admin.AdminFragment
import com.namor.coursework.fragment.film.FilmFragment
import com.namor.coursework.fragment.login.AdminOrUserFragment
import com.namor.coursework.fragment.user.UserFragment
import com.namor.coursework.fragment.user.film.UserFilmFragment
import com.namor.coursework.fragment.user.register.RegisterFragment

class MainActivity : AppCompatActivity(), MainListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, AdminOrUserFragment.newInstance(this))
                .commit()

    }

    override fun openAdminFragment(administrator: Administrator) {
        App.currentAdmin = administrator
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdminFragment.newInstance(this))
                .commit()
    }

    override fun openFilmFragment(film: Film?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FilmFragment.newInstance(film))
                .addToBackStack(null)
                .commit()
    }

    override fun openUserFragment(user: User) {
        App.currentUser = user
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UserFragment.newInstance(this))
                .commit()
    }

    override fun openRegisterFragment(login: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment.newInstance(login))
                .addToBackStack(null)
                .commit()
    }

    override fun openUserFilmFragment(film: Film) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UserFilmFragment.newInstance(film))
                .addToBackStack(null)
                .commit()
    }
}

interface MainListener {
    fun openAdminFragment(administrator: Administrator)
    fun openFilmFragment(film: Film?)
    fun openUserFragment(user: User)
    fun openRegisterFragment(login: String)
    fun openUserFilmFragment(film: Film)
}
