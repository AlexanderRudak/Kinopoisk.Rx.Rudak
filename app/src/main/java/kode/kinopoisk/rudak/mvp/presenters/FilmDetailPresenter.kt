package kode.kinopoisk.rudak.mvp.presenters

import android.app.Activity
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kode.kinopoisk.rudak.Constants
import kode.kinopoisk.rudak.KinopoiskApp
import kode.kinopoisk.rudak.ui.activities.FilmSeanceActivity
import kode.kinopoisk.rudak.mvp.models.FilmDetail
import kode.kinopoisk.rudak.mvp.views.FilmDetailView
import kode.kinopoisk.rudak.retrofit.GetModels
import kode.kinopoisk.rudak.util.JsonParser
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


@InjectViewState
class FilmDetailPresenter  : MvpPresenter<FilmDetailView>(){

    @Inject
    lateinit var app: KinopoiskApp
    internal var filmDetail: FilmDetail
    internal var filmID: String
    internal var filmName: String

    lateinit var subscription: Subscription
    lateinit var observableRetrofit: Observable<String>//Observable<MutableList<Film>>

    init {
        KinopoiskApp.graph.inject(this)
        filmDetail = FilmDetail()
        filmID = ""
        filmName = ""
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFilmDetail()
    }

    fun unSubscriprion(){
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe()
        }
    }

    fun loadFilmDetail() {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.kinopoisk.cf/").build()

        val service = retrofit.create(GetModels::class.java)

        try {
            observableRetrofit = service.getFilmDetail(filmID)

            subscription = observableRetrofit
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map({stsJson ->
                        JsonParser.JsonToFilmDetail(stsJson)
                    })
                    .onErrorReturn( { throwable ->
                        JsonParser.JsonToFilmDetail(Constants.strJsonFilmDetail) //FOR TESTING
                    })
                    .subscribe({ detail ->
                        filmDetail = detail
                        viewState.onFilmDetailLoaded(filmDetail)
                    })
        } catch ( e: Exception){
        }
    }

    fun openSeance(activity: Activity) {
         val intent = Intent(activity, FilmSeanceActivity::class.java)
        intent.putExtra("filmID", filmID)
        activity.startActivity(intent)
    }

}