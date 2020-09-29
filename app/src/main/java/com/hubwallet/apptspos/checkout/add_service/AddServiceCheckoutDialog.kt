package com.hubwallet.apptspos.checkout.add_service

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistData
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistDatum
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.employes.service.ServiceDetails
import kotlinx.android.synthetic.main.add_service_checkout_dialog_fragment.view.*
import kotlinx.android.synthetic.main.multiple_appointment.view.*
import java.util.*
import kotlin.collections.ArrayList

class AddServiceCheckoutDialog : DialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = AddServiceCheckoutDialog()
    }

    private lateinit var viewModel: AddServiceCheckoutDialogViewModel
    private lateinit var root: View
    private var serviceList = ArrayList<ServiceDetails>()
    private var stylistData = ArrayList<StylistDatum>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.add_service_checkout_dialog_fragment, container, false)
        root.dateEditText.setOnClickListener(this)
        root.timeTextview.setOnClickListener(this)
        root.btnCancel.setOnClickListener(this)
        root.imgClose.setOnClickListener(this)
        root.btnBookAppointment.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.dateEditText -> {
                datePiker()
            }
            R.id.timeTextview -> {
                timePiker()
            }
            R.id.btnCancel -> {
                dismiss()
            }
            R.id.imgClose -> {
                dismiss()
            }
            R.id.btnBookAppointment -> {
                addService()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .60).toInt()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddServiceCheckoutDialogViewModel::class.java)
        getService()
    }

    private fun getService() {
        viewModel.getService(PrefManager(requireContext()).vendorId)
        viewModel.liveDataTitle!!.observe(viewLifecycleOwner, Observer<ServiceDataRes> {
            if (it.status == 1) {
                serviceList.addAll(it.result)

                val adapter = ArrayAdapter<ServiceDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        serviceList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spinService.adapter = adapter
                root.spinService.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        root.amountServiceTextView.text = serviceList[position].price.toString()
                        root.duration.text = serviceList[position].duration.toString()

                    }

                }

                getStylist()
            }
        })
    }

    private fun getStylist() {
        viewModel.getStylist(PrefManager(requireContext()).vendorId)
        viewModel.liveDataStyliest!!.observe(viewLifecycleOwner, Observer<StylistData> {
            if (it.status == 1) {
                stylistData.addAll(it.stylistData!!)
                val adapter = ArrayAdapter<StylistDatum>(requireContext(), android.R.layout.simple_spinner_item,
                        stylistData)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spinStylist.adapter = adapter

            }
        })
    }

    private fun addService() {
        val serviceDetails = serviceList[root.spinService.selectedItemPosition]
        val stylistDatum = stylistData[root.spinStylist.selectedItemPosition]
        viewModel.checkoutAddService(arguments!!.getString("custumerId")!!, root.dateEditText.text.toString(), arguments!!.getString("token")!!, PrefManager(requireContext()).vendorId,
                arguments!!.getString("type")!!, root.timeTextview.text.toString(), serviceDetails.duration, serviceDetails.serviceId, stylistDatum.stylistId, serviceDetails.price)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                dialog!!.dismiss()
                val filter = Intent("CHECKOUT");
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(filter)
            }
        })
    }

    private fun timePiker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (hourOfDay < 9) {
                showToast("Please select valid time")
                return@OnTimeSetListener
            }
            root.timeTextview.text = "$hourOfDay:$minute"

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private fun datePiker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val mont = month + 1
            root.dateEditText.text = "$year-$mont-$dayOfMonth"
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
    }


}