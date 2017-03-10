package kode.kinopoisk.rudak.ui.activities

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.DatePicker
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.ui.adapters.RecyclerCinemaAdapter
import kode.kinopoisk.rudak.ui.adapters.SimpleDividerItemDecoration
import kode.kinopoisk.rudak.ui.dialogs.DatePickerFragment
import kode.kinopoisk.rudak.mvp.models.Cinema
import kode.kinopoisk.rudak.mvp.common.MvpAppCompatActivity
import kode.kinopoisk.rudak.mvp.presenters.FilmSeancePresenter
import kode.kinopoisk.rudak.mvp.views.FilmSeanceView
import kotlinx.android.synthetic.main.activity_film_seance.*

class FilmSeanceActivity : MvpAppCompatActivity(),
        DatePickerDialog.OnDateSetListener,
        RecyclerCinemaAdapter.OnMapClickListener,
        RecyclerCinemaAdapter.OnHeaderDateClickListener,
        FilmSeanceView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "FILM_SEANCE")
    lateinit var presenter: FilmSeancePresenter
    internal var layoutManager: RecyclerView.LayoutManager? = null
    internal var adapter: RecyclerCinemaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_seance)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(intent.getStringExtra("filmID")!=null)
            presenter.filmID = intent.getStringExtra("filmID")

        rvFilmSeance.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvFilmSeance.layoutManager = layoutManager
        rvFilmSeance.addItemDecoration(SimpleDividerItemDecoration(this))
    }

    override fun onCinemasLoaded(cinemas: MutableList<Cinema>){
        progressBar.visibility = View.INVISIBLE
        createAdapter(cinemas, this)
        updateView()
        return
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dateSet = dayOfMonth.toString() + "." + (monthOfYear + 1).toString() + "." + year.toString()
        if (presenter.getFilterDate() !== dateSet) {
            showDateHeader(dateSet)
            progressBar.visibility = View.VISIBLE
            presenter.setFilterDate(dateSet)
        }
    }

    private fun showDateHeader(date: String) {
        adapter?.setDate(date)
        adapter?.notifyDataSetChanged()
    }

    override fun onMapClick(cinema: Cinema) {
        presenter.openMap(this, cinema)
    }

    fun createAdapter(cinemas: MutableList<Cinema>, context: Context) {
        adapter = RecyclerCinemaAdapter(context, cinemas)
        if(adapter!=null){
            adapter!!.SetOnMapClickListener(this)
            adapter!!.SetOnHeaderDateClickListener(this)
            adapter!!.setDate(presenter.getFilterDate())
            rvFilmSeance.adapter = adapter
        }
    }

    override fun updateView() {
        rvFilmSeance.adapter.notifyDataSetChanged()
    }

    override fun onHeaderDateClick() {
        val newFrag = DatePickerFragment.newInstance(this)
        newFrag.show(supportFragmentManager, "date")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscriprion()
    }
}
