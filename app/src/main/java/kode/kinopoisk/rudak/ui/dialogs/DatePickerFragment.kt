package kode.kinopoisk.rudak.ui.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day_of_month = c.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(activity, STYLE_NORMAL, listener, year, month, day_of_month)
        dialog.datePicker.tag = tag
        return dialog
    }

    companion object {

        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val frag = DatePickerFragment()
            frag.listener = listener
            return frag
        }
    }
}