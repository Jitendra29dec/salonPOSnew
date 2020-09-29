package com.hubwallet.apptspos.calander.book_multiple_appointment

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hubwallet.apptspos.Localdatabase.Servicedatasa

import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistData
import com.hubwallet.apptspos.Utils.Models.StylistModel.StylistDatum
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.calander.add_deposit.OnTextViewClickListener
import com.hubwallet.apptspos.demo.ui.demo.showToast
import com.hubwallet.apptspos.employes.service.ServiceDataRes
import com.hubwallet.apptspos.employes.service.ServiceDetails
import kotlinx.android.synthetic.main.book_multiple_appoiment.view.*
import java.util.*
import kotlin.collections.ArrayList

class BookMultipleAppointment(var date: String, var multiplebooking: String) : DialogFragment(), OnTextViewClickListener {
    lateinit var adapter: MultipleAppointmentAdapter
    var serviceList = ArrayList<ServiceDetails>()
    private val stylistData = ArrayList<StylistDatum>()
    private val stylishIdList = ArrayList<String>()
    private val servicesIdList = ArrayList<String>()
    private val durationList = ArrayList<String>()
    private val servicedatasata = ArrayList<Servicedatasa>()
    private val servicedatasataer = ArrayList<Servicedatasa>()
    lateinit var viewModel: MultipleAppointmentViewModel
    lateinit var root: View


    override fun onStart() {
        super.onStart()
        val window: Window = getDialog()!!.getWindow()!!
        val windowParams: WindowManager.LayoutParams = window.getAttributes()
        windowParams.dimAmount = 0.20f
        windowParams.flags = windowParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.setAttributes(windowParams)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        root = layoutInflater.inflate(R.layout.book_multiple_appoiment, container, false)
        root.servicesRv.layoutManager = LinearLayoutManager(requireContext())
        val prefManager = PrefManager(requireContext());
        adapter = MultipleAppointmentAdapter(requireContext(), serviceList, stylistData,servicedatasataer, this)
        adapter.timeList.add("")
        servicesIdList.add("")
        durationList.add("")
        stylishIdList.add("")
        if (multiplebooking.equals("Submitted")) {
            servicedatasataer.addAll(prefManager.getList("Abc"));
            for(x in 0 until servicedatasataer.size){
                adapter.timeList.add(servicedatasataer.get(x).stylistid)
                servicesIdList.add("")
                durationList.add("")
                stylishIdList.add("")
            }
           // adapter = MultipleAppointmentAdapter(requireContext(), serviceList, stylistData,servicedatasataer, this)
            adapter.notifyItemInserted(adapter.timeList.size - 1)
            root.servicesRv.adapter = adapter
        } else {
            root.servicesRv.adapter = adapter
        }



        root.addServiceButton.setOnClickListener {
            adapter.timeList.add("")
            servicesIdList.add("")
            durationList.add("")
            stylishIdList.add("")
            adapter.notifyItemInserted(adapter.timeList.size - 1)
        }
        viewModel = ViewModelProvider(this).get(MultipleAppointmentViewModel::class.java)


        root.nextBtn.setOnClickListener {
            dialog!!.dismiss()
            for (x in 0 until stylishIdList.size) {
                servicedatasata.add(Servicedatasa(stylishIdList.get(x), durationList.get(x), servicesIdList.get(x)))
                prefManager.setList("Abc", servicedatasata)
                // db. .insertAll(Servicedata(x,servicesIdList.get(x),stylishIdList.get(x),"50",durationList.get(x),"3:50"))
                // db.TodoDao().insertAll(Servicedata(x,servicesIdList.get(x),stylishIdList.get(x),"50",durationList.get(x),"3:50"))

            }

            SubmitMultipleFragment(stylishIdList, servicesIdList, durationList, adapter.timeList, date.replace("/", "-")).show(requireActivity().supportFragmentManager, "")
        }
        root.closeImg.setOnClickListener {
            dialog!!.dismiss()
        }
        root.imgBack.setOnClickListener {
            dialog!!.dismiss()
        }

        getService()
        return root
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .96).toInt()
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun getService() {
        viewModel.getService(PrefManager(requireContext()).vendorId)
        viewModel.liveDataTitle!!.observe(viewLifecycleOwner, Observer<ServiceDataRes> {
            if (it.status == 1) {
                serviceList.addAll(it.result)
                getStylist()
            }
        })
    }

    private fun getStylist() {
        viewModel.getStylist(PrefManager(requireContext()).vendorId)
        viewModel.liveDataStyliest!!.observe(viewLifecycleOwner, Observer<StylistData> {
            if (it.status == 1) {
                stylistData.addAll(it.stylistData!!)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun clickHandle(value: Int, position: Int) {

        val calendar = Calendar.getInstance()
        TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (hourOfDay < 9) {
                showToast("Please select valid time")
                return@OnTimeSetListener
            }
            adapter.timeList[position] = "$hourOfDay:$minute"
            adapter.notifyItemChanged(position)

        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    override fun remove(position: Int) {
        adapter.timeList.removeAt(position)
        if (adapter.timeList.size == 0) {
            adapter.timeList.add("")
            adapter.notifyDataSetChanged()
            return
        }
        adapter.notifyItemRemoved(position)
    }

    override fun onItemSelectListener(action: Int, spnPosition: Int, position: Int) {
        if (action == 1) {
            servicesIdList[position] = serviceList[spnPosition].serviceId
            durationList[position] = serviceList[spnPosition].duration
        } else {
            stylishIdList[position] = stylistData[spnPosition].stylistId
        }
    }

    override fun onDurationChange(position: Int, duration: String) {
        durationList[position] = duration
    }

}