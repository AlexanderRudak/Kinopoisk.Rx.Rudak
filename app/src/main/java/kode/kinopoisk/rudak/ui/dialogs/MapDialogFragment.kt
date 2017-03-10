package kode.kinopoisk.rudak.ui.dialogs


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import kode.kinopoisk.rudak.R

class MapDialogFragment : DialogFragment(), OnMapReadyCallback {
    private var progressBar: ProgressBar? = null
    val fragment: SupportMapFragment
    private var mMap: GoogleMap? = null
    internal var cinemaName: String = ""
    internal var lat: Double? = null
    internal var lng: Double? = null

    init {
        fragment = SupportMapFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val args = arguments
        val title = args.getString("title")
        cinemaName = args.getString("cinemaName")
        lat = java.lang.Double.valueOf(args.getString("lat"))
        lng = java.lang.Double.valueOf(args.getString("lng"))

        val v = inflater!!.inflate(R.layout.map_dialog, container, false)
        val toolbar = v.findViewById(R.id.my_toolbar) as Toolbar
        toolbar.menu.clear()
        toolbar.title = title
        progressBar = v.findViewById(R.id.progress_spinner) as ProgressBar

        toolbar.setNavigationOnClickListener { dismiss() }

        progressBar!!.visibility = View.VISIBLE
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.mapView, fragment).commit()
        fragment.getMapAsync(this)

        return v
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val coord = LatLng(lat!!, lng!!)
        if(mMap!=null){
            mMap!!.addMarker(MarkerOptions().position(coord).title(cinemaName))
            mMap!!.uiSettings.isZoomControlsEnabled = true
            mMap!!.uiSettings.isMyLocationButtonEnabled = true
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 14f))
        }
        progressBar!!.visibility = View.INVISIBLE
    }
}