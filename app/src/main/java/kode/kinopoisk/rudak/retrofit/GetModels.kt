package kode.kinopoisk.rudak.retrofit

import kode.kinopoisk.rudak.mvp.models.Film
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import rx.Observable;

interface GetModels {

    @GET("getTodayFilms")
    fun getFilmsList(@Query("fdate") fdate: String,
                     @Query("fcity") fcity: String)
            //: Call<ArrayList<Film>> //Observable<ArrayList<Film>>
            : Observable<String>

    //fun getFilmsList(@Query("fdate") fdate: String, @Query("fcity") fcity: String)
    //        //: Call<ArrayList<Film>> //Observable<ArrayList<Film>>
    //        : Observable<MutableList<Film>>


    @GET("getFilm")
    fun getFilmDetail(@Query("filmid") filmid: String)
            : Observable<String>


    //val getUrl = String.format("http://api.kinopoisk.cf/getSeance?filmID=%1s&cityID=%2s&date=%3s", filmID, filterCity, filterDate)
    @GET("getSeance")
    fun getFilmDetail(@Query("filmid") filmid: String,
                      @Query("fcity") fcity: String,
                      @Query("fdate") fdate: String)
            : Observable<String>
}

