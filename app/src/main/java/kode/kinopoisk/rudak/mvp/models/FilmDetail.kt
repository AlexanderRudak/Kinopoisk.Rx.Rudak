package kode.kinopoisk.rudak.mvp.models

import kode.kinopoisk.rudak.mvp.models.Film


class FilmDetail {

    var film: Film? = null
    var slogan: String? = null
    var description: String? = null
    var ratingAgeLimits: String? = null
    var director: String? = null
    var actor: String? = null
    var producer: String? = null
    var triviaData: String? = null

    init {
        this.film = Film()
    }

}
