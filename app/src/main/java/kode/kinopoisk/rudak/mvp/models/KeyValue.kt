package kode.kinopoisk.rudak.mvp.models


class KeyValue {
    var key: String
    var value: String


    constructor(key: String, value: String) : super() {
        this.key = key
        this.value = value
    }

    constructor() : super() {
        this.key = "0"
        this.value = ""
    }

}