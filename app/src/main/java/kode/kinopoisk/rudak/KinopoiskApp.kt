package kode.kinopoisk.rudak

import android.app.Application
import android.content.Context
import kode.kinopoisk.rudak.di.*
import kode.kinopoisk.rudak.mvp.models.KeyValue
import java.text.SimpleDateFormat
import java.util.*


class KinopoiskApp : Application() {

    var filterDate = SimpleDateFormat("dd.MM.yyyy").format(Date())
    var filterCity: KeyValue = KeyValue("490", "Калининград")
    var filterGenre: KeyValue = KeyValue("0", "Все жанры")


    companion object {
        lateinit var graph: AppComponent
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }
}