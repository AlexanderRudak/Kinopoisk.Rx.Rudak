package kode.kinopoisk.rudak.ui.activities


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.squareup.picasso.Picasso
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.mvp.models.FilmDetail
import kode.kinopoisk.rudak.mvp.common.MvpAppCompatActivity
import kode.kinopoisk.rudak.mvp.presenters.FilmDetailPresenter
import kode.kinopoisk.rudak.mvp.views.FilmDetailView
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.content_film_detail.*


class FilmDetailActivity : MvpAppCompatActivity(),
        FilmDetailView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "FILM_DETAIL")
    lateinit var presenter: FilmDetailPresenter
    lateinit internal var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)

        picasso = Picasso.with(this)

        if(intent!=null && intent.hasExtra("filmID"))
            presenter.filmID = intent.getStringExtra("filmID")
        if(intent!=null && intent.hasExtra("filmName"))
            presenter.filmName = intent.getStringExtra("filmName")

        toolbar.title = presenter.filmName
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_film_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_seance) {
            presenter.openSeance(this@FilmDetailActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onFilmDetailLoaded(detail: FilmDetail) {
        progressBar.visibility = View.INVISIBLE
        updateView(detail)
        return
    }

    override fun updateView(filmDetail: FilmDetail) {
        if(filmDetail!=null){
            picasso.load(filmDetail.film!!.getPosterURL()).into(ivFilmPoster)
            tvFilmName.text = filmDetail.film!!.nameRU
            tvFilmGenre.text = filmDetail.film!!.genre
            tvDirector.text = filmDetail.director
            tvFilmActor.text = filmDetail.actor
            tvFilmCountry.text = filmDetail.film!!.country + ", " + filmDetail.film!!.filmLength
            tvFilmSlogan.text = filmDetail.slogan
            tvFilmDescription.text = filmDetail.description
            tvFilmTrivia.text = filmDetail.triviaData
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscriprion()
    }


}
