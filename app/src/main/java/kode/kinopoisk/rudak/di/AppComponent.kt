package kode.kinopoisk.rudak.di

import dagger.Component
import kode.kinopoisk.rudak.mvp.presenters.FilmDetailPresenter
import kode.kinopoisk.rudak.mvp.presenters.FilmSeancePresenter
import kode.kinopoisk.rudak.mvp.presenters.ListFilmPresenter

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(listFilmPresenter: ListFilmPresenter)

    fun inject(filmDetailPresenter: FilmDetailPresenter)

    fun inject(filmSeancePresenter: FilmSeancePresenter)
}