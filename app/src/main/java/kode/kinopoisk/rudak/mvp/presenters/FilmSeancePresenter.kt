package kode.kinopoisk.rudak.mvp.presenters


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kode.kinopoisk.rudak.Constants
import kode.kinopoisk.rudak.KinopoiskApp
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.ui.dialogs.MapDialogFragment
import kode.kinopoisk.rudak.mvp.models.Cinema
import kode.kinopoisk.rudak.mvp.models.KeyValue
import kode.kinopoisk.rudak.mvp.views.FilmSeanceView
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
class FilmSeancePresenter : MvpPresenter<FilmSeanceView>() {

    internal var cinemas: MutableList<Cinema>
    internal var filmID: String
    private var filterDate: String
    private var filterCity: KeyValue
    @Inject
    lateinit var app: KinopoiskApp

    lateinit var subscription: Subscription
    lateinit var observableRetrofit: Observable<String>//Observable<MutableList<Film>>


    init {
        KinopoiskApp.graph.inject(this)
        cinemas = ArrayList()
        filmID = ""
        filterDate = app.filterDate
        filterCity = app.filterCity
    }

    fun setFilterDate(filterDate: String){
        this.filterDate = filterDate
        loadSeances()
    }

    fun getFilterDate(): String{
        return this.filterDate
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadSeances()
    }

    fun unSubscriprion(){
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe()
        }
    }

    fun loadSeances() {
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
                    .map({strJson ->
                        JsonParser.JsonToListCinema(strJson)
                    })
                    .onErrorReturn( { throwable ->
                        JsonParser.JsonToListCinema(Constants.strJsonFilmSeance) //FOR TESTING
                    })
                    .subscribe({ listSeance ->
                        cinemas.clear()
                        cinemas.addAll(listSeance)
                        viewState.onCinemasLoaded(cinemas)
                    })
        } catch ( e: Exception){
        }
    }

    fun openMap(activity: AppCompatActivity, cinema: Cinema) {
        val args = Bundle()
        args.putString("title", activity.resources.getString(R.string.title_map_dialog))
        args.putString("cinemaName", cinema.cinemaName)
        args.putString("lat", cinema.lat)
        args.putString("lng", cinema.lon)

        val actionbarDialog = MapDialogFragment()
        actionbarDialog.arguments = args
        actionbarDialog.show(activity.supportFragmentManager, "map_dialog")
    }

}