package kode.kinopoisk.rudak.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.util.ArrayList
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.mvp.models.Cinema


class RecyclerCinemaAdapter(internal var context:

                            Context, cinemas: MutableList<Cinema>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cinemas:MutableList<Cinema> = ArrayList<Cinema>()
    internal var filterDate: String = ""
    private var mapClickListener: OnMapClickListener? = null
    internal var headerDateClickListener: OnHeaderDateClickListener? = null

    fun setDate(filterDate: String) {
        this.filterDate = filterDate
    }


    init {
        this.cinemas = cinemas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if (viewType == TYPE_HEADER) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.cinema_header_item, parent, false)
            return HeaderViewHolder(v)
        } else if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.cinema_item, parent, false)
            val vh = ItemViewHolder(view)
            return vh
        }
        return null
    }

    private fun getItem(position: Int): Cinema {
        return cinemas[position]
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is HeaderViewHolder) {
            viewHolder.tvDate.text = filterDate
        } else if (viewHolder is ItemViewHolder) {
            viewHolder.name.text = cinemas[position - 1].cinemaName
            viewHolder.address.text = cinemas[position - 1].address

            val seanceArr = cinemas[position - 1].seance
            val adapter = ArrayAdapter(context, R.layout.seance_item, R.id.tv_item_seance, seanceArr)
            viewHolder.seance.adapter = adapter

            val seance3DArr = cinemas[position - 1].seance
            val adapter3D = ArrayAdapter(context, R.layout.seance_item, R.id.tv_item_seance, seance3DArr)
            viewHolder.seance3D.adapter = adapter
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
        return cinemas.size + 1
    }

    internal inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var layDate: LinearLayout
        var tvDate: TextView

        init {
            this.layDate = view.findViewById(R.id.lay_filter_date) as LinearLayout
            this.tvDate = view.findViewById(R.id.tv_filter_date) as TextView

            this.layDate.setOnClickListener {
                if (headerDateClickListener != null)
                    headerDateClickListener!!.onHeaderDateClick()
            }
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var name: TextView
        internal var address: TextView
        internal var ivMap: ImageView
        internal var seance: GridView
        internal var seance3D: GridView

        init {
            this.name = view.findViewById(R.id.tv_cinema_name) as TextView
            this.address = view.findViewById(R.id.tv_cinema_address) as TextView
            this.ivMap = view.findViewById(R.id.iv_map) as ImageView
            this.seance = view.findViewById(R.id.gv_cinema_seance) as GridView
            this.seance3D = view.findViewById(R.id.gv_cinema_seance_3d) as GridView

            this.ivMap.setOnClickListener {
                if (mapClickListener != null)
                    mapClickListener!!.onMapClick(cinemas[adapterPosition - 1])
            }
        }
    }

    interface OnMapClickListener {
        fun onMapClick(cinema: Cinema)
    }

    fun SetOnMapClickListener(mapClickListener: OnMapClickListener) {
        this.mapClickListener = mapClickListener
    }

    interface OnHeaderDateClickListener {
        fun onHeaderDateClick()
    }

    fun SetOnHeaderDateClickListener(headerDateClickListener: OnHeaderDateClickListener) {
        this.headerDateClickListener = headerDateClickListener
    }

    companion object {

        private val TYPE_HEADER = 0
        private val TYPE_ITEM = 1
    }
}
