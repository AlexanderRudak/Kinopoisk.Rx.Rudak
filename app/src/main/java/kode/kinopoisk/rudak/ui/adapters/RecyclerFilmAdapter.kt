package kode.kinopoisk.rudak.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.util.ArrayList
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.mvp.models.Film
import kode.kinopoisk.rudak.mvp.models.KeyValue


class RecyclerFilmAdapter(context: Context, films: MutableList<Film>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var films: MutableList<Film> = ArrayList<Film>()
    internal var headerCityClickListener: OnHeaderCityClickListener? = null
    internal var headerDateClickListener: OnHeaderDateClickListener? = null
    internal var headerGenreClickListener: OnHeaderGenreClickListener? = null
    internal var itemClickListener: OnItemClickListener? = null
    internal var picasso: Picasso
    internal var filterCity: KeyValue? = null
    internal var filterDate: String = ""
    internal var filterGenre: KeyValue? = null

    fun setCity(filterCity: KeyValue) {
        this.filterCity = filterCity
    }

    fun setDate(filterDate: String) {
        this.filterDate = filterDate
    }

    fun setGenre(filterGenre: KeyValue) {
        this.filterGenre = filterGenre
    }


    init {
        picasso = Picasso.with(context)
        this.films = films
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.film_header_item, parent, false)
            return HeaderViewHolder(v)
        } else if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
            val vh = ItemViewHolder(view)
            return vh
        }
        return null
    }

    private fun getItem(position: Int): Film {
        return films[position]
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is HeaderViewHolder) {
            if (filterCity != null)
                viewHolder.tvCity.text = filterCity!!.value
            viewHolder.tvDate.text = filterDate
            if (filterGenre != null)
                viewHolder.tvGenre.text = filterGenre!!.value

        } else if (viewHolder is ItemViewHolder) {
            viewHolder.nameRU.text = films[position - 1].nameRU
            viewHolder.genre.text = films[position - 1].genre
            picasso.load(films[position - 1].getPosterURL()).into(viewHolder.posterUrl)
            viewHolder.rating.text = films[position - 1].rating.substring(0, films[position - 1].rating.indexOf("(")).trim { it <= ' ' }
            viewHolder.country.text = films[position - 1].country + " " + films[position - 1].filmLength
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position)) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    override fun getItemCount(): Int {
        return films.size + 1
    }

    internal inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layCity: LinearLayout
        var layDate: LinearLayout
        var layGenre: LinearLayout
        var tvCity: TextView
        var tvDate: TextView
        var tvGenre: TextView

        init {
            this.layCity = view.findViewById(R.id.lay_filter_city) as LinearLayout
            this.tvCity = view.findViewById(R.id.tv_filter_city) as TextView
            this.layCity.setOnClickListener {
                if (headerCityClickListener != null)
                    headerCityClickListener!!.onHeaderCityClick()
            }
            this.layDate = view.findViewById(R.id.lay_filter_date) as LinearLayout
            this.tvDate = view.findViewById(R.id.tv_filter_date) as TextView
            this.layDate.setOnClickListener {
                if (headerDateClickListener != null)
                    headerDateClickListener!!.onHeaderDateClick()
            }
            this.layGenre = view.findViewById(R.id.lay_filter_genre) as LinearLayout
            this.tvGenre = view.findViewById(R.id.tv_filter_genre) as TextView
            this.layGenre.setOnClickListener {
                if (headerGenreClickListener != null)
                    headerGenreClickListener!!.onHeaderGenreClick()
            }
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        internal var nameRU: TextView
        internal var rating: TextView
        internal var country: TextView
        internal var genre: TextView
        internal var posterUrl: ImageView

        init {
            posterUrl = view.findViewById(R.id.iv_item_film_poster) as ImageView
            nameRU = view.findViewById(R.id.tv_item_film_name) as TextView
            genre = view.findViewById(R.id.tv_item_film_genre) as TextView
            rating = view.findViewById(R.id.tv_item_film_rating) as TextView
            country = view.findViewById(R.id.tv_item_film_country) as TextView
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val pos = adapterPosition
            if (itemClickListener != null) {
                itemClickListener!!.onItemClick(v, films[pos - 1] )
            }
        }
    }

    interface OnHeaderCityClickListener {
        fun onHeaderCityClick()
    }

    fun SetOnHeaderCityClickListener(headerCityClickListener: OnHeaderCityClickListener) {
        this.headerCityClickListener = headerCityClickListener
    }

    interface OnHeaderDateClickListener {
        fun onHeaderDateClick()
    }

    fun SetOnHeaderDateClickListener(headerDateClickListener: OnHeaderDateClickListener) {
        this.headerDateClickListener = headerDateClickListener
    }

    interface OnHeaderGenreClickListener {
        fun onHeaderGenreClick()
    }

    fun SetOnHeaderGenreClickListener(headerGenreClickListener: OnHeaderGenreClickListener) {
        this.headerGenreClickListener = headerGenreClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, film: Film)
    }

    fun SetOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    companion object {

        private val TYPE_HEADER = 0
        private val TYPE_ITEM = 1
    }


}