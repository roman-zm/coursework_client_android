package com.namor.coursework.service

import com.namor.coursework.domain.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface AdminService {
    @POST("films")
    fun addFilm(@Body film: Film): Completable

    @PATCH("films/{id}")
    fun updateFilm(@Path("id") id: Int, @Body film: Film): Completable

    @DELETE("films/{id}")
    fun deleteFilm(@Path("id") id: Int): Completable

    @GET("films?size=200")
    fun getFilmList(): Single<Page<Film>>

    @GET("administrators/{login}")
    fun getAdmin(@Path("login") login: String): Single<Administrator>

    @GET("genres")
    fun getGenres(): Single<Page<Genre>>

    @GET("filmGenres/search/deleteAllByFilm")
    fun deleteFilmGenres(@Query("film") film: Int): Completable
}

interface UserService {

    @GET("users/{login}")
    fun getUser(@Path("login") login: String): Single<User>

    @POST("users")
    fun addUser(@Body user: User): Completable

    @GET("purchases/search/getByFilmAndUser")
    fun getPurchase(
            @Query("film") film: Int,
            @Query("user") user: String
    ): Completable

    @POST("purchases")
    fun addPurchase(@Body purchase: Purchase): Completable

    @GET("ratings/search/getByUserAndFilm")
    fun getRating(
            @Query("user") user: String,
            @Query("film") film: Int
    ): Single<Rating>

    @POST("ratings")
    fun changeRating(@Body rating: Rating): Completable

    @GET("ratings/search/countAllByFilm")
    fun getRatingCount(@Query("film") film: Int): Single<Int>

    @GET("ratings/search/getAverageForFilm")
    fun getAverageRating(@Query("film") film: Int): Single<Float>

    @GET("comments/search/getAllByFilmOrderByDate")
    fun getCommentsByFilm(@Query("film") film: Int): Single<Page<Comment>>

    @POST("comments")
    fun sendComment(@Body comment: Comment): Completable
}