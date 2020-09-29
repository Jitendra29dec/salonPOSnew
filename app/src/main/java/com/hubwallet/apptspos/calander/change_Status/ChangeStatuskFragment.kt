package com.hubwallet.apptspos.calander.change_Status

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.evetent_status_list_fragment.view.*


class ChangeStatuskFragment : DialogFragment(), StatusChangeListener {
    private lateinit var rootView: View
    private var changeStatusViewModel: ChangeStatusViewModel? = null
    private var list = ArrayList<GetData>()
    private lateinit var eventStatusAdapter: EventStatusAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.evetent_status_list_fragment, container, false)
        changeStatusViewModel = ViewModelProvider(this).get(ChangeStatusViewModel::class.java)
        eventStatusAdapter = EventStatusAdapter(requireContext(), list, this)
        rootView.eventStatusRv.layoutManager = LinearLayoutManager(requireContext())
        rootView.eventStatusRv.adapter = eventStatusAdapter
        rootView.customerNAmePopup.setText(arguments!!.getString("name"))
        getOptions()
        rootView.closeDialog.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    private fun getOptions() {
        changeStatusViewModel!!.getOptionsStatus(PrefManager(requireContext()).vendorId)
        changeStatusViewModel!!.liveDataOptions!!.observe(viewLifecycleOwner, Observer<ChangeStatusRes> {
            list.addAll(it.getData)
            eventStatusAdapter.notifyDataSetChanged()
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                ChangeStatuskFragment()
    }

    override fun onStatusChangListener(type: String, colorId: String) {
        if (type.equals("checkout", true)) {

        }
        changeStatusViewModel!!.changeStatus(type, arguments!!.getString("appimentId")!!, colorId)
        changeStatusViewModel!!.liveDataChangeStatus!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
            val i: Intent = Intent()

            targetFragment!!.onActivityResult(9, Activity.RESULT_OK, i)
            dismiss()
        })
    }
}