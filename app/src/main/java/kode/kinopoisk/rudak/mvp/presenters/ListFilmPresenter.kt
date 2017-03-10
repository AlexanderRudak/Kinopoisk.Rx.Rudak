package kode.kinopoisk.rudak.mvp.presenters

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kode.kinopoisk.rudak.Constants
import kode.kinopoisk.rudak.KinopoiskApp
import kode.kinopoisk.rudak.ui.activities.FilmDetailActivity
import kode.kinopoisk.rudak.mvp.models.KeyValue
import kode.kinopoisk.rudak.mvp.models.Film
import kode.kinopoisk.rudak.mvp.views.ListFilmView
import kode.kinopoisk.rudak.retrofit.GetModels
import kode.kinopoisk.rudak.util.JsonParser
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


@InjectViewState
class ListFilmPresenter : MvpPresenter<ListFilmView>() {

    var srcFilms: MutableList<Film>
    var films: MutableList<Film>
    @Inject
    lateinit var app: KinopoiskApp

    lateinit var subscription: Subscription
    lateinit var observableRetrofit: Observable<String>

    init {
        KinopoiskApp.graph.inject(this)
        srcFilms = ArrayList()
        films = ArrayList()
    }

    fun setFilterCity(filterCity: KeyValue){
        app.filterCity = filterCity
        loadFilms()
    }

    fun getFilterCity(): KeyValue {
        return app.filterCity
    }

    fun setFilterGenre(filterGenre: KeyValue){
        app.filterGenre = filterGenre
        filteredFilms()
    }

    fun getFilterGenre(): KeyValue {
        return app.filterGenre
    }

    fun setFilterDate(filterDate: String){
        app.filterDate = filterDate
        loadFilms()
    }

    fun getFilterDate(): String{
        return app.filterDate
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFilms()
    }

    fun unSubscriprion(){
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe()
        }
    }

    fun loadFilms() {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.kinopoisk.cf/").build()

        val service = retrofit.create(GetModels::class.java)

        try {
            observableRetrofit = service.getFilmsList(app.filterDate, app.filterCity.key)

            subscription = observableRetrofit
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map({s ->
                        JsonParser.JsonToListFilm(s)
                    })
                    .onErrorReturn( { throwable ->
                        JsonParser.JsonToListFilm(Constants.strJsonListFilm) //FOR TESTING
                    })
                    .subscribe({ listFilm ->
                        srcFilms.clear()
                        srcFilms.addAll(listFilm)

                        filteredFilms()
                        viewState.onFilmsLoaded(films)
                    }
             )

        } catch ( e: Exception){
        }
    }

    fun filteredFilms(){
        films.clear()

        if (app.filterGenre.key.contentEquals("0")) { //equals("0")
            films.addAll(srcFilms)
        } else {
            films.addAll(filteredByGenre(srcFilms))
        }
    }


    private fun filteredByGenre(listFilm: List<Film>): List<Film> {
        val filteredFilm = ArrayList<Film>()
        for (film in listFilm) {
            if (film.genre.toUpperCase().contains(app.filterGenre.value.toUpperCase()))
                filteredFilm.add(film)
        }
        return filteredFilm
    }

    enum class SortFilmsBy : Comparator<Film> {
        RATING {
            override fun compare(lhs: Film, rhs: Film) = lhs.rating.compareTo(rhs.rating)
        },
        NAME {
            override fun compare(lhs: Film, rhs: Film) = lhs.nameRU.compareTo(rhs.nameRU)
        },
    }

    fun sortName() {
        Collections.sort(films) { lfilm, rfilm -> lfilm.nameRU.compareTo(rfilm.nameRU) }
        //films.sortedBy { SortFilmsBy.NAME }
        viewState.updateView()
    }

    fun sortRating() {
        Collections.sort(films, Collections.reverseOrder { lfilm, rfilm -> lfilm.rating.compareTo(rfilm.rating) })
        //films.sortedByDescending { SortFilmsBy.RATING}
        viewState.updateView()
    }

    fun openDetail(context: Context, film: Film) {
        val intent = Intent(context, FilmDetailActivity::class.java)
        intent.putExtra("filmID", film.id)
        intent.putExtra("filmName", film.nameRU)
        context.startActivity(intent)
    }
}