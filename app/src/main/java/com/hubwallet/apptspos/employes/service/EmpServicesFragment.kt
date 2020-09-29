package com.hubwallet.apptspos.employes.service

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
import com.hubwallet.apptspos.employes.schedule.OnDayStatusChangeListener
import kotlinx.android.synthetic.main.emp_services_fragment.view.*


class EmpServicesFragment : Fragment(), OnDayStatusChangeListener {

    companion object {
        fun newInstance() = EmpServicesFragment()
    }

    private val list = ArrayList<ServiceDetails>()
    private lateinit var viewModel: EmpServicesViewModel
    lateinit var root: View
    lateinit var adapter: EmpServicesAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.emp_services_fragment, container, false)
        root.empServices.layoutManager = LinearLayoutManager(requireContext())
        adapter = EmpServicesAdapter(requireContext(), list, this)
        root.empServices.adapter = adapter
        root.saveEmpService.setOnClickListener {
         //   addEmpService()
            if (arguments != null && arguments!!.containsKey("stylistId")) {
                editEmpService()
            } else {
                addEmpService()
            }

        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmpServicesViewModel::class.java)
        getService()
    }

    private fun getService() {
        viewModel.getService(PrefManager(requireContext()).vendorId)
        viewModel.liveDataTitle!!.observe(viewLifecycleOwner, Observer<ServiceDataRes> {
            if (it.status == 1) {
                list.addAll(it.result)
                adapter.notifyDataSetChanged()
            }
        })
    }


    fun addEmpService() {
        val gson = Gson()
        val json = gson.toJson(list)
        Log.d("JsonEMPO", json)
        viewModel.addEmpService(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId", ""), json)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {

        })
    }

    fun editEmpService() {
        val gson = Gson()
        val json = gson.toJson(list)
        Log.d("JsonEMPO", json)
        viewModel.editEmpService(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId", ""), json)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)

        })
    }

    override fun onStatusChange(isChecked: Boolean, position: Int) {
        list[position - 1].active = isChecked
        adapter.notifyItemChanged(position)
    }
}
