package kode.kinopoisk.rudak.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kode.kinopoisk.rudak.mvp.models.Cinema


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FilmSeanceView : MvpView {

    fun onCinemasLoaded(cinemas: MutableList<Cinema>)

    fun updateView()

}