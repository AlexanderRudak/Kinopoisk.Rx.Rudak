package kode.kinopoisk.rudak.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kode.kinopoisk.rudak.mvp.models.KeyValue
import kode.kinopoisk.rudak.mvp.models.Film


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ListFilmView : MvpView {

    fun onFilmsLoaded(films: MutableList<Film>)

    fun updateView()

    fun updateCityHeader(selectedValue: KeyValue)

    fun updateGenreHeader(selectedValue: KeyValue)

}