package kode.kinopoisk.rudak.mvp.models



class Cinema {

    var id: String = ""
    var address: String = ""
    var lon: String = ""
    var lat: String = ""
    var cinemaName: String = ""
    var seance: Array<String?> = kotlin.arrayOfNulls(0)
    var seance3D: Array<String?> = kotlin.arrayOfNulls(0) //arrayOf<String>()

    constructor() {

    }

    constructor(id: String, address: String, lon: String, lat: String, cinemaName: String, seance: Array<String?>, seance3D: Array<String?>) {
        this.id = id
        this.address = address
        this.lon = lon
        this.lat = lat
        //this.posterURL = String.format("http://st.kp.yandex.net/images/film_iphone/iphone360_%1$s.jpg",id);
        this.cinemaName = cinemaName
        this.seance = seance
        this.seance3D = seance3D
    }
}
