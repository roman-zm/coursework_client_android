package com.namor.coursework.service

import com.namor.coursework.domain.Administrator
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Page
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface AdminService {
    @POST("films")
    fun addFilm(@Body film: Film): Single<Film>

    @PATCH("films/{id}")
    fun updateFilm(@Path("id") id: Int, @Body film: Film): Single<Film>

    @DELETE("films/{id}")
    fun deleteFilm(@Path("id") id: Int)

    @GET("films")
    fun getFilmList(): Single<Page<Film>>

    @GET("administrators/{login}")
    fun getAdmin(@Path("login") login: String): Single<Administrator>
}

interface UserService {

}