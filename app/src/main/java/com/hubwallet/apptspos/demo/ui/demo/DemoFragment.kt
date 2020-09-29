package com.hubwallet.apptspos.demo.ui.demo

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hubwallet.apptspos.APis.GetNotes
import com.hubwallet.apptspos.APis.MySingleton
import com.hubwallet.apptspos.APis.UpdateNotes
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.ApiCommunicator
import com.hubwallet.apptspos.Utils.FragmentsComunicator
import com.hubwallet.apptspos.Utils.LogUtils
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.calander.AppointmentListRes
import com.hubwallet.apptspos.calander.Result
import com.hubwallet.apptspos.fragments.FragmentDialogForEventClick
import com.hubwallet.apptspos.view.week_view.OnMonthChangeListener
import com.hubwallet.apptspos.view.week_view.WeekView
import com.hubwallet.apptspos.view.week_view.WeekViewDisplayable
import java.text.SimpleDateFormat
import java.util.*

class DemoFragment : Fragment(),
        OnMonthChangeListener<Result>, View.OnClickListener, FragmentsComunicator, ApiCommunicator {

    companion object {
        fun newInstance() = DemoFragment()
    }
//test demo
    var noteDialog: AlertDialog? = null
    private var eventSelected: Result? = null
    private var popupDialogList: FragmentDialogForEventClick? = null
    var eventList: ArrayList<Result> = ArrayList<Result>()
    private lateinit var viewModel: DemoViewModel
    private val weekView: WeekView<Result> by lazyView(R.id.weekView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(DemoViewModel::class.java)
        val start = getStartDate()
        val end = getEndDate()
        weekView.setOnEventClickListener { data, rect ->

            eventSelected = data
            if (data.isCheckOut == 1) {
                Toast.makeText(requireContext(), "Appointment already checked out ! ", Toast.LENGTH_SHORT).show()
            } else if (data.id == -1L) {
                Toast.makeText(requireContext(), "Appointments are close at this time!", Toast.LENGTH_SHORT).show()

            } else {
                val bundle = Bundle()
                val e_name = data.title
                LogUtils.printLog("Selected Booking", "" + e_name)
                val name = e_name.split(", ").toTypedArray()
//        val n = name[3].split("-").toTypedArray()
//        bundle.putString("name", n[1] + " "" + name[4])
                bundle.putString("name", e_name)
                bundle.putString("service", name[0])
                popupDialogList = FragmentDialogForEventClick()
                popupDialogList!!.setArguments(bundle)
                popupDialogList!!.setTargetFragment(this, 0)
                assert(fragmentManager != null)
                popupDialogList!!.show(fragmentManager!!, "dialog")
            }


        }
        weekView.setOnEmptyViewClickListener {
            val calendar1 = Calendar.getInstance()
            if (calendar1.timeInMillis < it.getTimeInMillis()) {
                createAlert(it)
            } else {
                Toast.makeText(requireContext(), "Cant do appointment on past dates.", Toast.LENGTH_SHORT).show()
            }
        }
        getData()

    }

    private fun getStartDate(): Calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
    }

    private fun getEndDate(): Calendar = Calendar.getInstance().apply {
        val daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
        set(Calendar.DAY_OF_MONTH, daysInMonth)
        set(Calendar.HOUR_OF_DAY, 23)
    }

    private fun getData() {
        viewModel.getAppointement(PrefManager(requireContext()).vendorId)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer<AppointmentListRes> {
            if (it.status == 1) {
                eventList.addAll(it.result)
                val events = mutableListOf<WeekViewDisplayable<Result>>()

                var value = 0
                for (event in eventList) {
                    events += event
                    value += 1
//                    if (value == 360) {
//                        break
//                    }

                }
                weekView.numberOfVisibleDays = 7
                weekView.submit(events)
//                weekView.notifyDataSetChanged()
//                weekView.minDate = getStartDate()
//                weekView.maxDate = getEndDate()

            }
        })
    }

    private fun createAlert(calendar: Calendar) {
        val emptyCliclDialog = AlertDialog.Builder(requireContext(), R.style.customDialog).create()
        val v = LayoutInflater.from(requireContext()).inflate(R.layout.calender_on_empty_click_dialog, null, false)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams = lp
        emptyCliclDialog.setView(v)
        val dateTextView = v.findViewById<TextView>(R.id.dateTextViewPopUp)
        val timeTextView = v.findViewById<TextView>(R.id.timeTextviewPopup)
        val newAppointment = v.findViewById<LinearLayout>(R.id.newAppointmentLinearLayout)
        val groupAppointment = v.findViewById<LinearLayout>(R.id.groupAppointmentLinearLayout)
        val depositAppointment = v.findViewById<LinearLayout>(R.id.addDepositLinearLayout)
        val adToWishList = v.findViewById<LinearLayout>(R.id.addToWaitListLinearLayout)
        val personalTask = v.findViewById<LinearLayout>(R.id.personalTaskLinearLayout)
        val editWorkingHour = v.findViewById<LinearLayout>(R.id.editWorkingHourLinearLayout)
        newAppointment.setOnClickListener(this)
        groupAppointment.setOnClickListener(this)
        depositAppointment.setOnClickListener(this)
        //        adToWishList.setOnClickListener(this);
//        personalTask.setOnClickListener(this);
//        editWorkingHour.setOnClickListener(this);
        val formater = SimpleDateFormat("yyyy-MM-dd")
        val timeFormatter = SimpleDateFormat("hh:mm a")
        dateTextView.text = formater.format(calendar.time)
        timeTextView.text = timeFormatter.format(calendar.time)
        //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val wmlp: WindowManager.LayoutParams = emptyCliclDialog.getWindow()!!.getAttributes()
        wmlp.gravity = Gravity.BOTTOM
        wmlp.windowAnimations = R.style.DialogAnimation
        emptyCliclDialog.show()
    }


    override fun onMonthChange(startDate: Calendar, endDate: Calendar): List<WeekViewDisplayable<Result>> {

        return ArrayList()
    }

    override fun onClick(v: View?) {

    }


    override fun message(message: String?) {
        popupDialogList!!.dismiss()
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        if (message.equals("notes", ignoreCase = true)) {
            getNotes()
            noteDialog = AlertDialog.Builder(requireActivity()).create()
            val v = layoutInflater.inflate(R.layout.custom_notes_layout, null, false)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            v.layoutParams = lp
            noteDialog!!.setView(v)
            val wmlp: WindowManager.LayoutParams = noteDialog!!.getWindow()!!.getAttributes()
            wmlp.gravity = Gravity.CENTER
            wmlp.windowAnimations = R.style.DialogAnimation
            val cancel: Button
            val update: Button
            val noteEditText = v.findViewById<EditText>(R.id.editTextNotes)
            cancel = v.findViewById(R.id.cancelNoteButton)
            update = v.findViewById(R.id.updateNoteButton)
            cancel.setOnClickListener { noteDialog!!.dismiss() }
            update.setOnClickListener { MySingleton.getInstance(requireActivity()).addToRequestQue(UpdateNotes(PrefManager(requireActivity()).vendorId, eventSelected!!.id.toString() + "", noteEditText.getText().toString(), this).stringRequest) }
            noteDialog!!.show()
            return
        }
    }

    private fun getNotes() {
        MySingleton.getInstance(requireContext()).addToRequestQue(GetNotes(PrefManager(requireContext()).vendorId, eventSelected!!.id.toString() + "", this).stringRequest)
    }

    override fun getApiData(status: String?, response: String?) {
        Toast.makeText(requireContext(), status, Toast.LENGTH_LONG).show()
        when (status) {
            "update_note" -> noteDialog!!.dismiss()
        }
    }



}
