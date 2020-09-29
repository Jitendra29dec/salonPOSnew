package com.hubwallet.apptspos.calander.book_multiple_appointment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.error.VolleyError
import com.android.volley.request.StringRequest
import com.google.gson.GsonBuilder
import com.hubwallet.apptspos.APis.AddCustomerPopup
import com.hubwallet.apptspos.APis.MySingleton
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.*
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryData
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryList
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateData
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateList
import com.hubwallet.apptspos.customer.cust_details.CustomerDetails
import kotlinx.android.synthetic.main.submit_multiple_fragment.view.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SubmitMultipleFragment(var stylishIdList: ArrayList<String>, var servicesIdList: ArrayList<String>, var durationList: ArrayList<String>, var timeList: ArrayList<String>, var date: String) : DialogFragment() {


    lateinit var root: View
    private lateinit var viewModel: SubmitMultipleViewModel
    var empTypeList: ArrayList<CustomerDetails> = ArrayList<CustomerDetails>()
    private var createCustomerDialog: Dialog? = null
    private var mActivity: Activity? = null
    private var countryDataList: List<CountryData>? = null
    private var stateList: List<GetStateData>? = null
    private var customProgress: CustomProgress? = null
    var apiCommunicator: ApiCommunicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity
        customProgress = CustomProgress(mActivity!!)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.submit_multiple_fragment, container, false)
        root.multipleAppobtn.setOnClickListener {
            submitAppontment()
        }
        // apiCommunicator = this
        convetDate()
        val servicecont = servicesIdList.size
        root.ACTVservicecount.text = "No.of Services  " + servicecont.toString()
        root.tvDate.text = date
        root.closeImg.setOnClickListener {
            dialog!!.dismiss()
        }
        root.imgBack.setOnClickListener {
            dialog!!.dismiss()
        }

        root.ACTVaddmore_service.setOnClickListener {
            // dialog!!.dismiss()
            BookMultipleAppointment(date,"Submitted").show(requireActivity().supportFragmentManager, "")
        }

        root.ACTVaddcustomer.setOnClickListener { v: View? ->
            addNewCustomerDialog()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .90).toInt()
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubmitMultipleViewModel::class.java)
        getCustomerList()
    }

    @SuppressLint("SimpleDateFormat")
    private fun convetDate() {
        var spf = SimpleDateFormat("dd-mm-yyy")
        val newDate: Date = spf.parse(date)
        spf = SimpleDateFormat("yyyy-mm-dd")
        date = spf.format(newDate)
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

    private fun submitAppontment() {
        val stylishIds = TextUtils.join(",", stylishIdList)
        val servicesIds = TextUtils.join(",", servicesIdList)
        val durations = TextUtils.join(",", durationList)
        val times = TextUtils.join(",", timeList)
        val listDate = ArrayList<String>()
        for (i in timeList) {
            listDate.add(date)
        }
        val dates = TextUtils.join(",", listDate)
        viewModel.addMultipleAppoitment(PrefManager(requireContext()).vendorId, dates, times, servicesIds, stylishIds, durations, root.notesEdt.text.toString(), "1")
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {

        })
    }


    @SuppressLint("InflateParams")
    private fun addNewCustomerDialog() {
        val v = LayoutInflater.from(mActivity).inflate(R.layout.add_new_customer, null, false)
        createCustomerDialog = Dialog(mActivity!!)
        createCustomerDialog!!.setContentView(v)
        val firstname: EditText
        val lastName: EditText
        val email: EditText
        val mobile: EditText
        val editCCAppointmentEmail: EditText
        val editHomePhone: EditText
        val editReferredBy: EditText
        val editCity: EditText
        val editWorkPhone: EditText
        val editAddress: EditText
        val spinCountry: Spinner
        val spinState: Spinner
        val spinGender: Spinner
        val spinPreferredContact: Spinner
        val checkSMS: CheckBox
        val checkEmail: CheckBox
        val cancelButton: Button
        val addCustomerButton: Button
        firstname = v.findViewById(R.id.firstNameField)
        lastName = v.findViewById(R.id.lastNameField)
        email = v.findViewById(R.id.emailField)
        mobile = v.findViewById(R.id.editMobileNo)
        editCCAppointmentEmail = v.findViewById(R.id.editCCAppointmentEmail)
        editHomePhone = v.findViewById(R.id.editHomePhone)
        editReferredBy = v.findViewById(R.id.editReferredBy)
        editCity = v.findViewById(R.id.editCity)
        editWorkPhone = v.findViewById(R.id.editWorkPhone)
        editAddress = v.findViewById(R.id.editAddress)
        spinCountry = v.findViewById(R.id.spinCountry)
        spinState = v.findViewById(R.id.spinState)
        spinGender = v.findViewById(R.id.spinGender)
        spinPreferredContact = v.findViewById(R.id.spinPreferredContact)
        checkSMS = v.findViewById(R.id.checkSMS)
        checkEmail = v.findViewById(R.id.checkEmail)
        cancelButton = v.findViewById(R.id.cancelButton)
        addCustomerButton = v.findViewById(R.id.addCustomerBUtton)
        cancelButton.setOnClickListener { v1: View? -> createCustomerDialog!!.dismiss() }
        spinGender.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, genderArr)
        spinPreferredContact.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, preferredContactArr)
        addCustomerButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val fname = firstname.text.toString()
                val lname = lastName.text.toString()
                val ema = email.text.toString()
                val mob = mobile.text.toString()
                if (fname.isEmpty()) {
                    sendError(firstname)
                    return
                } else if (lname.isEmpty()) {
                    sendError(lastName)
                    return
                } else if (ema.isEmpty()) {
                    sendError(email)
                    return
                } else if (!Patterns.EMAIL_ADDRESS.matcher(ema).matches()) {
                    sendValidError(email)
                    return
                } else if (editCCAppointmentEmail.text.toString().length > 0
                        && !Patterns.EMAIL_ADDRESS.matcher(editCCAppointmentEmail.text.toString()).matches()) {
                    sendValidError(editCCAppointmentEmail)
                    return
                } else if (mob.isEmpty()) {
                    sendError(mobile)
                    return
                } else if (mob.length != 10) {
                    sendValidError(mobile)
                    return
                } else if (!checkSMS.isChecked && !checkEmail.isChecked) {
                    sendError(checkSMS)
                    sendError(checkEmail)
                    return
                } else {
//                    MySingleton.getInstance(mActivity)
//                            .addToRequestQue(new AddCustomer(fname, lname, ema, mob, "", "", "", "", "", vendorID, "", apiCommunicator)
//                                    .getStringRequest());
                    customProgress!!.show()
                    MySingleton.getInstance(mActivity)
                            .addToRequestQue(AddCustomerPopup(PrefManager(requireContext()).vendorId, firstname.text.toString(),
                                    lastName.text.toString(), email.text.toString(),
                                    mobile.text.toString(), editHomePhone.text.toString(),
                                    editWorkPhone.text.toString(), editAddress.text.toString(),
                                    editCity.text.toString(),
                                    stateList!![spinState.selectedItemPosition].stateId,
                                    countryDataList!![spinCountry.selectedItemPosition].countryD,
                                    genderArr[spinGender.selectedItemPosition], (spinPreferredContact.selectedItemPosition + 1).toString(),
                                    editCCAppointmentEmail.text.toString(),
                                    editReferredBy.text.toString(),
                                    if (checkSMS.isChecked) "1" else "0",
                                    if (checkSMS.isChecked) "1" else "0",
                                    apiCommunicator).stringRequest)
                }
            }

            private fun sendError(checkBox: CheckBox) {
                checkBox.error = "Field Cant be Empty"
            }

            private fun sendError(mobile: EditText) {
                mobile.error = "Field Cant be Empty"
            }

            private fun sendValidError(mobile: EditText) {
                mobile.error = "Enter valid data."
            }
        })
        val countryRequest = StringRequest(Request.Method.POST,
                Constants.getCountry, label@ Response.Listener { response: String? ->
            var jsonObject: JSONObject? = null
            try {
                jsonObject = JSONObject(response)
                val status = jsonObject.getString("status")
                if (status.equals("1", ignoreCase = true)) {
                    countryDataList = GsonBuilder().create().fromJson(response, CountryList::class.java).result
                    val countryList = ArrayList<String>()
                    if (countryDataList != null) {
                        for (data in countryDataList!!) {
                            countryList.add(data.countryName)
                        }
                    }
                    if (countryList.size > 0) {
                        spinCountry.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, countryList)
                        spinCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                                val stateRequest: StringRequest = object : StringRequest(Method.POST, Constants.getState, Response.Listener { response ->
                                    var jsonObject: JSONObject? = null
                                    try {
                                        jsonObject = JSONObject(response)
                                        val status = jsonObject!!.getString("status")
                                        if (status.equals("1", ignoreCase = true)) {
                                            stateList = GsonBuilder().create().fromJson(response, GetStateList::class.java).result
                                            val list = ArrayList<String>()
                                            for (d in stateList!!) {
                                                list.add(d.stateName)
                                            }
                                            if (list.size > 0) {
                                                spinState.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, list)
                                            }
                                        }
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }, Response.ErrorListener { error: VolleyError -> LogUtils.printLog("STate Error", "::  " + error.message) }) {
                                    override fun getParams(): Map<String, String> {
                                        val param = HashMap<String, String>()
                                        param["country_id"] = countryDataList!!.get(position).countryD
                                        return param
                                    }
                                }
                                MySingleton.getInstance(mActivity).addToRequestQue(stateRequest)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error: VolleyError -> LogUtils.printLog("Response", error.message) })
        MySingleton.getInstance(mActivity).addToRequestQue(countryRequest)
        val layoutParams = WindowManager.LayoutParams()
        val windowAlDl = createCustomerDialog!!.window
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowAlDl!!.attributes = layoutParams
        createCustomerDialog!!.show()
    }

    companion object {
        private val genderArr = arrayOf("Male", "Female")
        private val preferredContactArr = arrayOf("Mobile", "Home", "Work")

        @JvmField
        var FCommunicator: FragmentsComunicator? = null
    }
}
