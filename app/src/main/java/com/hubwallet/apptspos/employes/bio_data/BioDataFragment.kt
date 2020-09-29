package com.hubwallet.apptspos.employes.bio_data

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.bio_data_fragment.view.*


class BioDataFragment : Fragment(), OnTextChangeListener, View.OnClickListener {

    companion object {
        fun newInstance() = BioDataFragment()
    }

    private var listExpYear = ArrayList<String>()
    private var listExpMonth = ArrayList<String>()
    private lateinit var viewModel: BioDataViewModel
    private lateinit var root: View
    private lateinit var serviceExpAdapter: ServiceExpAdapter
    private var list = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.bio_data_fragment, container, false)
        setList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
                listExpYear)

        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        root.expYearSpn.adapter = adapter

        val adapterMonth = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
                listExpMonth)

        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        root.monthExpSpn.adapter = adapterMonth
        serviceExpAdapter = ServiceExpAdapter(requireContext(), list,this)
        root.serviceExpRv.layoutManager = LinearLayoutManager(requireContext())
        root.serviceExpRv.adapter = serviceExpAdapter
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        root.btnAddMore.setOnClickListener {
            list.add("")
            root.serviceExpRv.adapter = serviceExpAdapter
//            root.parentLayout.invalidate()
        }
        root.saveEmpBtn.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        if (root.expYearSpn.selectedItemPosition == 0) {
            showToast("Please select exp Year")
            return
        }
        if (root.monthExpSpn.selectedItemPosition == 0) {
            showToast("Please select exp Month")
            return
        }
        addBioData()
    }

    private fun addBioData() {
        val gson = Gson()
        val json = gson.toJson(list)
        Log.d("JsonEMPO", json)
        viewModel.addBioData(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId",""),
                listExpYear[root.expYearSpn.selectedItemPosition], listExpMonth[root.monthExpSpn.selectedItemPosition],
                root.workLocationEdt.text.toString(), root.notesEdt.text.toString(), json)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun editBioData() {
        val gson = Gson()
        val json = gson.toJson(list)
        Log.d("JsonEMPO", json)
        viewModel.editBioData(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId",""),
                listExpYear[root.expYearSpn.selectedItemPosition], listExpMonth[root.monthExpSpn.selectedItemPosition],
                root.workLocationEdt.text.toString(), root.notesEdt.text.toString(), json)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun setList() {
        listExpYear.add("Year")
        listExpMonth.add("Month")
        for (i in 0..40) {
            if (i <= 12) {
                listExpMonth.add(i.toString())
            }
            listExpYear.add(i.toString())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BioDataViewModel::class.java)
    }

    override fun onTextChange(cat: String, postion: Int) {
        list[postion]=cat
//        serviceExpAdapter.notifyItemChanged(postion)
    }


}
