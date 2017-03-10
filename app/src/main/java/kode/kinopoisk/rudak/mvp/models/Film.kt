package kode.kinopoisk.rudak.mvp.models


class Film {

    var id: String? = null
    var nameRU: String = ""
    var year: String? = null
    var rating: String = ""
    private var posterURL: String? = null
    var filmLength: String? = null
    var country: String? = null
    var genre: String = ""

    constructor() {

    }

    constructor(id: String, nameRU: String, year: String, rating: String, filmLength: String, country: String, genre: String) {
        this.id = id
        this.nameRU = nameRU
        this.year = year
        this.rating = rating
        this.posterURL = String.format("http://st.kp.yandex.net/images/film_iphone/iphone360_%1\$s.jpg", id)
        this.filmLength = filmLength
        this.country = country
        this.genre = genre
    }

    fun getPosterURL(): String? {
        return posterURL
    }

    fun setPosterURL(id: String) {
        this.posterURL = String.format("http://st.kp.yandex.net/images/film_iphone/iphone360_%1\$s.jpg", id)
    }


}
