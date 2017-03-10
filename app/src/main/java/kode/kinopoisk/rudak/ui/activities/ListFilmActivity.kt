package kode.kinopoisk.rudak.ui.activities

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kode.kinopoisk.rudak.ui.adapters.SimpleDividerItemDecoration
import kode.kinopoisk.rudak.ui.dialogs.DatePickerFragment
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.ui.dialogs.SelectDialogFragment
import kode.kinopoisk.rudak.mvp.models.KeyValue
import kode.kinopoisk.rudak.ui.adapters.RecyclerFilmAdapter
import kode.kinopoisk.rudak.mvp.models.Film
import kode.kinopoisk.rudak.mvp.common.MvpAppCompatActivity
import kode.kinopoisk.rudak.mvp.presenters.ListFilmPresenter
import kode.kinopoisk.rudak.mvp.views.ListFilmView
import kotlinx.android.synthetic.main.activity_list_film.*
import kotlinx.android.synthetic.main.content_list_film.*


class ListFilmActivity : MvpAppCompatActivity(),
        RecyclerFilmAdapter.OnHeaderCityClickListener,
        SelectDialogFragment.OnSelectDialogListener,
        RecyclerFilmAdapter.OnHeaderDateClickListener,
        RecyclerFilmAdapter.OnHeaderGenreClickListener,
        DatePickerDialog.OnDateSetListener,
        RecyclerFilmAdapter.OnItemClickListener,
        ListFilmView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "LIST_FILM")
    lateinit var presenter: ListFilmPresenter
    internal var adapter: RecyclerFilmAdapter? = null
    internal var layoutManager: RecyclerView.LayoutManager? = null
    internal var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_film)

        setSupportActionBar(toolbar)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvListFilm.layoutManager = layoutManager
        //rvListFilm.setHasFixedSize(true);
        rvListFilm.addItemDecoration(SimpleDividerItemDecoration(this))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list_film, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.sort_name) {
            presenter.sortName()
            return true
        }
        if (id == R.id.sort_rating) {
            presenter.sortRating()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onHeaderCityClick() {
        val fm = supportFragmentManager
        val dialog = SelectDialogFragment.newInstance(resources.getString(R.string.title_city_dialog), 1, R.array.city, this) //new SelectDialogFragment();
        dialog.show(fm.beginTransaction(), "city")
    }

    override fun onHeaderGenreClick() {
        val fm = supportFragmentManager
        val dialog = SelectDialogFragment.newInstance(resources.getString(R.string.title_genre_dialog), 2, R.array.genre, this) //new SelectDialogFragment();
        dialog.show(fm.beginTransaction(), "genre")
    }
    fun showDateHeader(date: String) {
        adapter!!.setDate(date)
        adapter!!.notifyDataSetChanged()
    }

    fun showCityHeader(city: KeyValue) {
        adapter!!.setCity(city)
        adapter!!.notifyDataSetChanged()
    }

    fun showGenreHeader(genre: KeyValue) {
        adapter!!.setGenre(genre)
        adapter!!.notifyDataSetChanged()
    }

    override fun updateCityHeader(selectedValue: KeyValue){

    }
    override fun updateGenreHeader(selectedValue: KeyValue){

    }

    override fun onSelectDialogValue(requestID: Int, selectedValue: KeyValue) {
        if (requestID == 1 && presenter.getFilterCity().key !== selectedValue.key) {
            showCityHeader(selectedValue)
            progressBar.visibility = View.VISIBLE
            presenter.setFilterCity(selectedValue)
        }else if (requestID == 2 && presenter.getFilterGenre().key !== selectedValue.key) {
            showGenreHeader(selectedValue)
            presenter.setFilterGenre(selectedValue)
        }
    }

    override fun onHeaderDateClick() {
        val newFrag = DatePickerFragment.newInstance(this)
        newFrag.show(supportFragmentManager, "date")
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val dateSet = dayOfMonth.toString() + "." + (monthOfYear + 1).toString() + "." + year.toString()
        if (presenter.getFilterDate() !== dateSet) {
            showDateHeader(dateSet)
            progressBar.visibility = View.VISIBLE
            presenter.setFilterDate(dateSet)
         }
    }


    override fun onItemClick(view: View, film: Film) {
        presenter.openDetail(view.context, film)
    }


    override fun onFilmsLoaded(films: MutableList<Film>) {
        createAdapter(films)
        updateView()
        progressBar.visibility = View.INVISIBLE
        return
    }

    fun createAdapter(films: MutableList<Film>) {
        if (adapter==null && !films.isEmpty()) {
            adapter = RecyclerFilmAdapter(this, films)
            adapterSetDataHeader()
            adapterSetClickListener()
            rvListFilm.adapter = adapter
        }
    }

    override fun updateView() {
        rvListFilm.adapter?.notifyDataSetChanged()
    }

    private fun adapterSetDataHeader() {
        adapter?.setCity(presenter.getFilterCity())
        adapter?.setDate(presenter.getFilterDate())
        adapter?.setGenre(presenter.getFilterGenre())
    }

    private fun adapterSetClickListener() {
        adapter?.SetOnHeaderCityClickListener(this)
        adapter?.SetOnHeaderDateClickListener(this)
        adapter?.SetOnHeaderGenreClickListener(this)
        adapter?.SetOnItemClickListener(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscriprion()
    }

}
