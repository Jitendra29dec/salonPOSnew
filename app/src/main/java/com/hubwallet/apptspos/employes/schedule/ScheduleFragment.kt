package com.hubwallet.apptspos.employes.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.schedule_fragment.view.*


class ScheduleFragment : Fragment(), OnDayStatusChangeListener,View.OnClickListener {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var viewModel: ScheduleViewModel
    private lateinit var root: View
    private lateinit var scheduleAdapter: ScheduleAdapter
    private val list = ArrayList<DaysDetailsSchedule>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.schedule_fragment, container, false)
        root.scheduleRv.layoutManager = LinearLayoutManager(requireContext())
        setList()
        root.saveSchBtn.setOnClickListener(this)
        scheduleAdapter = ScheduleAdapter(requireContext(), list, this)
        root.scheduleRv.adapter = scheduleAdapter
        return root
    }

    private fun setList() {
        var days = DaysDetailsSchedule(false, "Monday", "", "")
        list.add(days)
        days = DaysDetailsSchedule(false, "Tuesday", "", "")
        list.add(days)
        days = DaysDetailsSchedule(false, "Wednesday", "", "")
        list.add(days)
        days = DaysDetailsSchedule(false, "Thursday", "", "")
        list.add(days)
        days = DaysDetailsSchedule(false, "Friday", "", "")
        list.add(days)
        days = DaysDetailsSchedule(false, "Saturday", "", "")
        list.add(days)
        days = DaysDetailsSchedule(false, "Sunday", "", "")
        list.add(days)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
    }

    override fun onStatusChange(isChecked: Boolean, position: Int) {
        list[position - 1].active = isChecked
        scheduleAdapter.notifyItemChanged(position)
    }

    fun saveSchedule() {
        val gson = Gson()
        val json = gson.toJson(list)
        Log.d("JsonEMPO", json)
        viewModel.addSchedule(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId", ""), json)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
        })
    }

    fun editSchedule() {
        val gson = Gson()
        val json = gson.toJson(list)
        Log.d("JsonEMPO", json)
        viewModel.editSchedule(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId", ""), json)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.saveSchBtn -> {
                if (arguments != null && arguments!!.containsKey("stylistId")) {
                    editSchedule()
                } else {
                    saveSchedule()
                }
            }

        }
    }
}
