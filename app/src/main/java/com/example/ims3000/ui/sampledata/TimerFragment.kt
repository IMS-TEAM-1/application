package com.example.ims3000.ui.sampledata

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.ims3000.R
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.Save_button)
        val timerButton = view.findViewById<Button>(R.id.Time_button)
        val dateButton = view.findViewById<Button>(R.id.Date_button)
        val dateText = view.findViewById<TextView>(R.id.Date_TextView)
        val timeText = view.findViewById<TextView>(R.id.Time_TextView)


        val selectedTime = Calendar.getInstance()
        var date = Format.format(selectedTime.time).toString()
        dateButton?.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                selectedTime.set(Calendar.YEAR, year)
                selectedTime.set(Calendar.MONTH, month)
                selectedTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                dateText!!.text = date
            },
                selectedTime.get(Calendar.YEAR),
                selectedTime.get(Calendar.MONTH),
                selectedTime.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = System.currentTimeMillis()
            datePicker.show()
        }

        var time = timeFormat.format(selectedTime.time).toString()
        timerButton?.setOnClickListener{
            val timePicker = TimePickerDialog(
                requireContext(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    selectedTime.set(Calendar.SECOND, 0)
                    timeText!!.text = time
                },
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE), false
            )
            timePicker.show()
        }
    }

    companion object {
        internal var Format = SimpleDateFormat("dd MMM, YYYY", Locale.US)
        internal var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}