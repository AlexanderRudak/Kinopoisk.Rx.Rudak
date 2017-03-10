package kode.kinopoisk.rudak.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ListView
import java.util.ArrayList
import kode.kinopoisk.rudak.R
import kode.kinopoisk.rudak.mvp.models.KeyValue
import kode.kinopoisk.rudak.ui.adapters.KeyValueArrayAdapter
import kode.kinopoisk.rudak.util.UtilResource


class SelectDialogFragment : DialogFragment() {

    private var listener: OnSelectDialogListener? = null
    private var resource: Int = 0
    private var requestID: Int = 0

    interface OnSelectDialogListener {
        fun onSelectDialogValue(requestID: Int, selectedValue: KeyValue)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val args = arguments
        val title = args.getString("title")
        val v = inflater!!.inflate(R.layout.select_dialog, container, false)
        val toolbar = v.findViewById(R.id.my_toolbar) as Toolbar
        toolbar.menu.clear()
        toolbar.title = title

        toolbar.setNavigationOnClickListener { dismiss() }

        var kv: List<KeyValue> = ArrayList()
        kv = UtilResource.getKeyValueArrayFromStringArray(activity, "\\|", resource)

        val arrAdapter = KeyValueArrayAdapter(activity, R.layout.select_item_dialog, kv)
        val listView = v.findViewById(R.id.lvSimple) as ListView
        listView.adapter = arrAdapter

        listView.onItemClickListener = AdapterView.OnItemClickListener {
            adapter, v, position, arg3 -> // Do something...
            if (listener != null) {
                listener!!.onSelectDialogValue(requestID, listView.getItemAtPosition(position) as KeyValue)
            }
            dismiss()
        }
        return v
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    companion object {

        fun newInstance(title: String, requestID: Int, resource: Int, listener: OnSelectDialogListener): SelectDialogFragment {

            val frag = SelectDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            frag.requestID = requestID
            frag.resource = resource
            frag.listener = listener
            return frag
        }
    }

}
