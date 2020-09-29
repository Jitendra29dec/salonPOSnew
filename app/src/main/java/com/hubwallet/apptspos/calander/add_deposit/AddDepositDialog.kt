package com.hubwallet.apptspos.calander.add_deposit

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.checkout.payment.AddDepositPaymentDialogFragment
import com.hubwallet.apptspos.customer.cust_details.CustomerDetails
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.add_deposit_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList


class AddDepositDialog : DialogFragment(), OnTextViewClickListener {
    lateinit var root: View
    var empTypeList: ArrayList<CustomerDetails> = ArrayList<CustomerDetails>()
    var distributionLis: ArrayList<String> = ArrayList<String>()
    var dateList: ArrayList<String> = ArrayList<String>()
    var startList: ArrayList<String> = ArrayList<String>()
    var endList: ArrayList<String> = ArrayList<String>()

    companion object {
        private var fragmentDialogFragment: AddDepositDialog? = null

        fun getInstance(): AddDepositDialog {
//            if (fragmentDialogFragment == null) {
                fragmentDialogFragment = AddDepositDialog()
//            }
            return fragmentDialogFragment!!
        }
    }

    lateinit var addDepositAdapter: AddDepositAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var viewModel: AddDepositViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            root = inflater.inflate(R.layout.add_deposit_dialog, container, false)
            dateList.add("")
            startList.add("")
            endList.add("")
            addDepositAdapter = AddDepositAdapter(requireContext(), dateList, startList, endList, this)
            root.dateTimeRv.layoutManager = LinearLayoutManager(requireContext())
            root.dateTimeRv.adapter = addDepositAdapter
            distributionLis.add("equal")
            distributionLis.add("percent")
            distributionLis.add("custom")
            val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item,
                    distributionLis)

            adapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item)
            root.spnDistribution.adapter = adapter
            root.addMoreTv.setOnClickListener {
                dateList.add("")
                startList.add("")
                endList.add("")
                addDepositAdapter.notifyDataSetChanged()
            }
            root.closeImg.setOnClickListener {
                dialog!!.dismiss()
            }
            root.imgBack.setOnClickListener {
                dialog!!.dismiss()
            }
            root.overdraftCb.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    root.overdraftEdt.visibility = View.VISIBLE
                } else {
                    root.overdraftEdt.visibility = View.GONE
                }
            }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        root.addDepositBtn.setOnClickListener {
            addDeposit()
        }
        viewModel = ViewModelProvider(this).get(AddDepositViewModel::class.java)
        getCustomerList()
        if (arguments != null) {
            getDepositDetails()
        }
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .80).toInt()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        root.edtDepositAmount.requestFocus()
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

    private fun addDeposit() {
        var overdue = 0
        if (root.overdraftCb.isChecked) {
            overdue = 1
        }
        var ioLimit = 0
        if (root.ioLimitCb.isChecked) {
            ioLimit = 1
        }
        var poll = 0
        if (root.shareDepositCb.isChecked) {
            poll = 1
        }
//
//        val gson = Gson()
//        val dateJson = gson.toJson(dateList)
//        Log.d("date",dateJson)
//        val startJson = gson.toJson(startList)
//        Log.d("date1",startJson)
//        val endJson = gson.toJson(endList)
//        Log.d("date2",endJson)
        val dateJson = TextUtils.join(", ", dateList)
        val startJson = TextUtils.join(", ", startList)
        val endJson = TextUtils.join(", ", endList)
        viewModel.addDeposit(empTypeList[root.spnCustomer.selectedItemPosition].customerId,
                PrefManager(requireContext()).vendorId,
                root.edtCustCount.text.toString(), root.edtDepositAmount.text.toString(), poll.toString(),
                overdue.toString(), root.overdraftEdt.text.toString(), ioLimit.toString(), root.eventNameEdt.text.toString(),
                distributionLis[root.spnDistribution.selectedItemPosition],
                root.eventNoteEdt.text.toString(), dateJson,
                startJson, endJson
        )
        viewModel.liveDataAddDeposit!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                dialog!!.dismiss()
                val fragment = AddDepositPaymentDialogFragment.getInstance(it.despositId, root.edtDepositAmount.text.toString())

                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, fragment)
                        .commit()

            }
        })


    }
    override fun onStart() {
        super.onStart()
        val window: Window = getDialog()!!.getWindow()!!
        val windowParams: WindowManager.LayoutParams = window.getAttributes()
        windowParams.dimAmount = 0.20f
        windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.setAttributes(windowParams)
    }
    private fun updateDeposit() {
        val gson = Gson()
        val dateJson = gson.toJson(dateList)
        val startJson = gson.toJson(startList)
        val endJson = gson.toJson(endList)
        val overdue = root.overdraftCb.isChecked.toString()
        val ioLimit = root.ioLimitCb.isChecked.toString()
        val poll = root.overdraftCb.isChecked.toString()
        viewModel.updateDeposit(arguments!!.getString("depositId", ""), empTypeList[root.spnCustomer.selectedItemPosition].customerId,
                PrefManager(requireContext()).vendorId,
                root.edtCustCount.text.toString(), root.edtDepositAmount.text.toString(), poll,
                overdue, root.overdraftEdt.text.toString(), ioLimit, root.eventNameEdt.text.toString(),
                distributionLis[root.spnDistribution.selectedItemPosition],
                root.eventNoteEdt.text.toString(), dateJson, startJson, endJson
        )
        viewModel.liveDataUpdateDeposit!!.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun getDepositDetails() {
        viewModel.getDepositDetails(arguments!!.getString("depositId", ""))
        viewModel.liveDataDepositDetails!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                root.edtCustCount.setText("")
                root.edtDepositAmount.setText("")

                root.eventNoteEdt.setText("")
            }
        })
    }

    override fun clickHandle(value: Int, position: Int) {
        when (value) {
            1 -> {
                val calendar = Calendar.getInstance()
                DatePickerDialog(context!!, OnDateSetListener { view, year, month, dayOfMonth ->
                    val mont = month + 1
                    dateList[position] = "$year-$mont-$dayOfMonth"
                    addDepositAdapter.notifyItemChanged(position)
                }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
            }
            2 -> {
                val calendar = Calendar.getInstance()
                TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if (hourOfDay < 9) {
                        showToast("Please select valid time")
                        return@OnTimeSetListener
                    }
                    startList[position] = "$hourOfDay:$minute"
                    addDepositAdapter.notifyItemChanged(position)

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
            3 -> {
                val calendar = Calendar.getInstance()
                TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if (hourOfDay < 9) {
                        showToast("Please select valid time")
                        return@OnTimeSetListener
                    }
                    endList[position] = "$hourOfDay:$minute"
                    addDepositAdapter.notifyItemChanged(position)

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        }
    }

    override fun remove(position: Int) {
        dateList.removeAt(position)
        startList.removeAt(position)
        endList.removeAt(position)
        addDepositAdapter.notifyItemRemoved(position)
    }
}