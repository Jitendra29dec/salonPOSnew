package com.hubwallet.apptspos.checkout.add_cetificate

import android.app.DatePickerDialog
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
import androidx.recyclerview.widget.GridLayoutManager
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.checkout.add_cetificate.template_dat.TemplateData
import com.hubwallet.apptspos.checkout.add_cetificate.templet_res.Template
import com.hubwallet.apptspos.customer.cust_details.CustomerDetails
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.employes.service.ServiceDetails
import kotlinx.android.synthetic.main.add_certificate_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList

class AddCertificateFragment : DialogFragment(), SelectionListener {

    companion object {
        fun newInstance() = AddCertificateFragment()
    }

    var empTypeList: ArrayList<CustomerDetails> = ArrayList<CustomerDetails>()
    private lateinit var viewModel: AddCertificateViewModel
    lateinit var root: View
    lateinit var tampletAdapter: TampletAdapter
    var templetList = ArrayList<Template>()
    var typeList = ArrayList<String>()
    var imageList = ArrayList<TemplateData>()
    var selectedPosition = 0
    var name = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.add_certificate_fragment, container, false)
        tampletAdapter = TampletAdapter(requireContext(), imageList, this)
        root.templateRv.layoutManager = GridLayoutManager(requireContext(), 3)
        root.templateRv.adapter = tampletAdapter
        typeList.add("Complementary")
        typeList.add("Custom")
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item,
                typeList)

        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        root.spnType.adapter = adapter
        root.spnType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 1) {
                    root.cashAmountTv.visibility = View.VISIBLE
                    root.amountHeading.visibility = View.VISIBLE
                    root.cashAmountTv.setText("")
                } else {
                    root.cashAmountTv.visibility = View.GONE
                    root.amountHeading.visibility = View.GONE
                    root.cashAmountTv.setText("0")
                }
            }

        }
        root.btnBookAppointment.setOnClickListener {
            val customerDetails = empTypeList[root.spnCustomer.selectedItemPosition]
            name = "${customerDetails.customerName}-${customerDetails.email}"
            addCertificate()
        }

        root.btnCancel.setOnClickListener {
            dismiss()
        }
        root.imgClose.setOnClickListener {
            dismiss()
        }

        root.issueEdt.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val mont = month + 1
                root.issueEdt.text = "$year-$mont-$dayOfMonth"

            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        }
        root.expireDateEdt.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val mont = month + 1
                root.expireDateEdt.text = "$year-$mont-$dayOfMonth"

            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        }
        return root
    }

    private fun addCertificate() {
        val type = root.spnType.selectedItemPosition + 1
        val serviceDetails = serviceList[root.spnService.selectedItemPosition]
        viewModel.addCertificate(arguments!!.getString("custumerId")!!, root.certiNumber.text.toString(), serviceList[root.spnService.selectedItemPosition].serviceId, type.toString(),
                root.issueEdt.text.toString(), root.cashAmountTv.text.toString(), "", name, "", "", root.messageEdt.text.toString(), imageList[selectedPosition].templateId, "", imageList[selectedPosition].id
                , PrefManager(requireContext()).vendorId, PrefManager(requireContext()).logiId
        )
        viewModel.liveDataAdd.observe(viewLifecycleOwner, Observer {
            val filter = Intent("CHECKOUT");
            dismiss()
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(filter)
        })
    }

    private fun getCustomerList() {
        viewModel.getCustomerList(PrefManager(requireContext()).vendorId)
        viewModel.liveDataCustDetails!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                empTypeList.addAll(it.result)
                val adapter = ArrayAdapter<CustomerDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        empTypeList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spnCustomer.adapter = adapter
            }
        })
    }

    private var serviceList = ArrayList<ServiceDetails>()
    private fun getService() {
        viewModel.getService(PrefManager(requireContext()).vendorId)
        viewModel.liveDataTitle!!.observe(viewLifecycleOwner, Observer<ServiceDataRes> {
            if (it.status == 1) {
                serviceList.addAll(it.result)

                val adapter = ArrayAdapter<ServiceDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        serviceList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spnService.adapter = adapter

            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddCertificateViewModel::class.java)
        getTemplate()
        getService()
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .70).toInt()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun getTemplate() {
        viewModel.getTemplate("1")

        viewModel.liveDateTemplates.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                templetList.addAll(it.template)
                val adapter = ArrayAdapter<Template>(requireContext(), android.R.layout.simple_spinner_item,
                        templetList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spnTamplet.adapter = adapter
                root.spnTamplet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        getTemplateData(templetList[position].templateId)
                    }

                }
            }
            getCustomerList()
        })
    }

    private fun getTemplateData(id: String) {
        viewModel.getTemplateData(id)

        viewModel.liveDateTemplatesData.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                imageList.clear()
                imageList.addAll(it.templateData)
                tampletAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onSelection(position: Int) {
        if (selectedPosition >= 0) {
            imageList[selectedPosition].isChecked = false
        }
        selectedPosition = position
        imageList[position].isChecked = true
        tampletAdapter.notifyDataSetChanged()
    }


}