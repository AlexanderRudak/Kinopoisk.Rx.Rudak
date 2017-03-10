package kode.kinopoisk.rudak.ui.adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.mvp.models.KeyValue

class KeyValueArrayAdapter(internal var context: Context,
                           internal var resource: Int,
                           internal var listPair: List<KeyValue>) :

        ArrayAdapter<KeyValue>(context, resource, listPair) {

     val inflater: LayoutInflater

    init {
        inflater = (context as AppCompatActivity).layoutInflater//LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var row: View? = convertView
        val holder: KeyValueHolder?

        if (row == null) {
            row = this.inflater.inflate(resource, parent, false)

            holder = KeyValueHolder()
            holder.tvValue = row!!.findViewById(R.id.tv_select) as TextView

            row.tag = holder
        } else {
            holder = row.tag as KeyValueHolder
        }

        val pair = listPair[position]
        holder.tvValue!!.text = pair.value

        return row
    }

    internal class KeyValueHolder {
        var tvValue: TextView? = null
    }

}
