package kode.kinopoisk.rudak.util

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.UnsupportedEncodingException
import java.util.ArrayList

import kode.kinopoisk.rudak.mvp.models.Cinema
import kode.kinopoisk.rudak.mvp.models.Film
import kode.kinopoisk.rudak.mvp.models.FilmDetail


object JsonParser {


    fun JsonToListFilm(strJson: String): List<Film> {
        val films = ArrayList<Film>()
        if (strJson === "") {
            return films
        }
        try {
            var dataJsonObj: JSONObject? = null

            //val strJsonUTF = String(strJson.toByteArray(charset("UTF-8")), "UTF-8")
            val strJsonUTF = String(strJson.toByteArray(Charsets.UTF_8), Charsets.UTF_8)
            dataJsonObj = JSONObject(strJsonUTF)
            val filmsArr = dataJsonObj.getJSONArray("filmsData")

            for (i in 0..filmsArr.length() - 1) {
                val filmObj = filmsArr.getJSONObject(i)
                films.add(Film(
                        filmObj.getString("id"),
                        filmObj.getString("nameRU"),
                        filmObj.getString("year"),
                        filmObj.getString("rating"),
                        filmObj.getString("filmLength"),
                        filmObj.getString("country"),
                        filmObj.getString("genre")))
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return films
    }

    fun JsonToFilmDetail(strJson: String): FilmDetail {
        val filmDetail = FilmDetail()

        try {
            var dataJsonObj: JSONObject? = null
            //val strJsonUTF = String(strJson.toByteArray(charset("UTF-8")), "UTF-8")
            val strJsonUTF = String(strJson.toByteArray(Charsets.UTF_8), Charsets.UTF_8)
            dataJsonObj = JSONObject(strJsonUTF)

            val film = filmDetail.film
            film!!.id = dataJsonObj.getString("filmID")
            film.nameRU = dataJsonObj.getString("nameRU")
            film.year = dataJsonObj.getString("year")
            film.filmLength = dataJsonObj.getString("filmLength")
            film.country = dataJsonObj.getString("country")
            film.genre = dataJsonObj.getString("genre")
            film.setPosterURL(dataJsonObj.getString("filmID"))

            filmDetail.slogan = dataJsonObj.getString("slogan")
            filmDetail.description = dataJsonObj.getString("description")
            filmDetail.ratingAgeLimits = dataJsonObj.getString("ratingAgeLimits")
            filmDetail.triviaData = dataJsonObj.getString("triviaData")

            val creators = dataJsonObj.getJSONArray("creators")
            val directors = creators.getJSONArray(0)
            val actors = creators.getJSONArray(1)

            var director = "Директоры: "
            for (i in 0..directors.length() - 1) {

                director = director + directors.getJSONObject(i).getString("nameRU") + ";"
            }
            filmDetail.director = director

            var actor = "Актеры: "
            for (i in 0..actors.length() - 1) {

                actor = actor + actors.getJSONObject(i).getString("nameRU") + ";"
            }
            filmDetail.actor = actor

        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return filmDetail
    }


    fun JsonToListCinema(strJson: String): List<Cinema> {
        val cinemas = ArrayList<Cinema>()

        try {
            var dataJsonObj: JSONObject? = null
            //val strJsonUTF = String(strJson.toByteArray(charset("UTF-8")), "UTF-8")
            val strJsonUTF = String(strJson.toByteArray(Charsets.UTF_8), Charsets.UTF_8)
            dataJsonObj = JSONObject(strJsonUTF)
            val cinemaArr = dataJsonObj.getJSONArray("items")

            for (i in 0..cinemaArr.length() - 1) {
                val filmObj = cinemaArr.getJSONObject(i)

                var seances = arrayOfNulls<String>(0)
                //var seances = arrayOf<String>()
                if (filmObj.has("seance")) {
                    val seanceArr = filmObj.getJSONArray("seance")
                    seances = arrayOfNulls<String>(seanceArr.length())

                    for (s in 0..seanceArr.length() - 1) {
                        seances[s] = seanceArr.getString(s)
                    }
                }

                var seances3D = arrayOfNulls<String>(0)
                if (filmObj.has("seance3D")) {
                    val seanceArr = filmObj.getJSONArray("seance3D")
                    seances3D = arrayOfNulls<String>(seanceArr.length())
                    for (s in 0..seanceArr.length() - 1) {
                        seances3D[s] = seanceArr.getString(s)
                    }
                }
                cinemas.add(Cinema(
                        filmObj.getString("cinemaID"),
                        filmObj.getString("address"),
                        filmObj.getString("lon"),
                        filmObj.getString("lat"),
                        filmObj.getString("cinemaName"),
                        seances,
                        seances3D))
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return cinemas
    }

}
