package kode.kinopoisk.rudak.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kode.kinopoisk.rudak.mvp.models.FilmDetail


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FilmDetailView : MvpView {

        fun onFilmDetailLoaded(detail: FilmDetail)

        fun updateView(detail: FilmDetail)



}