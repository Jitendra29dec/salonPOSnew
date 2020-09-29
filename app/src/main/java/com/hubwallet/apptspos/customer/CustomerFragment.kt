package com.hubwallet.apptspos.customer

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.employes.details.state.StateDetails
import com.hubwallet.apptspos.fragments.CustomerLIstFragment
import kotlinx.android.synthetic.main.customer_fragment.*
import kotlinx.android.synthetic.main.customer_fragment.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern.compile
import kotlin.collections.ArrayList


class CustomerFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = CustomerFragment()
    }

    private var currentTimeSelected //time selected while clicking on calendar
            : Calendar? = null
    private var mActivity: Activity? = null
    private var formater: SimpleDateFormat? = null
    private lateinit var viewModel: CustomerViewModel
    private lateinit var root: View
    var stateList: ArrayList<StateDetails> = ArrayList<StateDetails>()
    var genderList: ArrayList<String> = ArrayList<String>()
    var cardExpMonthList: ArrayList<String> = ArrayList<String>()
    var cardExpYearList: ArrayList<String> = ArrayList<String>()
    var image: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.customer_fragment, container, false)
        root.yesAllowOnlineBooking.isChecked = true
        root.saveCustBtn.setOnClickListener(this)
        root.profileImg.setOnClickListener(this)
        root.imgBack.setOnClickListener(this)
        //  val txtDate = root.findViewById<TextView>(R.id.birthdayEdt)
        //  formater = SimpleDateFormat("yyyy-MM-dd")
        if (arguments != null && arguments!!.containsKey("customerId")) {
            root.title.text = "Edit Customer"
            root.saveCustBtn.text = "Update"
        }
        prepareList()

        //  val time = currentTimeSelected
        //   root.birthdayEdt.text = dateFormat.format(time!!.time)
        val time = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        root.birthdayEdt.setOnClickListener { v: View? ->
            DatePickerDialog(mActivity!!, DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                time[Calendar.DAY_OF_MONTH] = dayOfMonth
                time[Calendar.MONTH] = month
                time[Calendar.YEAR] = year
                root.birthdayEdt.text = dateFormat.format(time.time)
            }, time[Calendar.YEAR], time[Calendar.MONTH], time[Calendar.DAY_OF_MONTH]).show()
        }

        root.anniversaryEdt.setOnClickListener { v: View? ->
            DatePickerDialog(mActivity!!, DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                time[Calendar.DAY_OF_MONTH] = dayOfMonth
                time[Calendar.MONTH] = month
                time[Calendar.YEAR] = year
                root.anniversaryEdt.text = dateFormat.format(time.time)
            }, time[Calendar.YEAR], time[Calendar.MONTH], time[Calendar.DAY_OF_MONTH]).show()
        }

        return root
    }

    private fun prepareList() {
        genderList.add("Male")
        genderList.add("FeMale")

        cardExpMonthList.add("Select Year")
        cardExpYearList.add("Select Month")
        for (i in 1..12) {
            cardExpMonthList.add(i.toString())
        }
        for (i in 2020..2030) {
            cardExpYearList.add(i.toString())
        }

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item,
                genderList)
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        root.spnGender.adapter = adapter

        val cardExpMonthAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item,
                cardExpMonthList)
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        root.expMonthSpn.adapter = cardExpMonthAdapter

        val cardExpYearAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item,
                cardExpYearList)
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item)
        root.expYearSpn.adapter = cardExpYearAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        getState()

    }

    private fun getState() {
        viewModel.getState("231")
        viewModel.liveDataState!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                stateList.add(StateDetails("-1", "SELECT"))
                stateList.addAll(it.result)
                val adapter = ArrayAdapter<StateDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        stateList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                root.spnState.adapter = adapter  //selected item will look like a spinner set from XML
                if (arguments != null && arguments!!.containsKey("customerId")) {
                    getCustById()
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val bitmap: Bitmap
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, imageUri)
                root.profileImg.setImageBitmap(bitmap)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                val byteArray = stream.toByteArray()
                image = Base64.encodeToString(byteArray, Base64.DEFAULT)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "Error Occurred please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private val emailRegex = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, CustomerLIstFragment())
                        .commit()
            }
            R.id.profileImg -> {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 102)
            }
            R.id.saveCustBtn -> {
                val firstName = root.edtFirstName.text.toString()
                if (firstName.isEmpty()) {
                    edtFirstName.error = "Please enter first name"
                    // showToast("Please enter first name")
                    return
                }
                val lastName = root.edtLastName.text.toString()
                if (lastName.isEmpty()) {
                    edtLastName.error = "Please enter last name"
                    // showToast("Please enter last name")
                    return
                }

                val phone = root.mobileEdt.text.toString()
                if (phone.isEmpty() || phone.length < 10) {
                    mobileEdt.error = "Please enter valid phone"
                    //  showToast("Please enter valid phone")
                    return
                }
                val email = root.emailIdEdt.text.toString()
                if (email.isEmpty() || !isValidEmail(email)) {
                    emailIdEdt.error = "Please enter valid email"
                    //   showToast("Please enter valid email")
                    return
                }
                if (cityEdt.text.isNullOrEmpty()) {
                    cityEdt.error = "Please enter city"
                    //   showToast("Please enter valid email")
                    return
                }

                /* if (Spinner1.getSelectedItem().toString().trim().equals("Pick one")) {
                    // Toast.makeText(CallWs.this, "Error", Toast.LENGTH_SHORT).show();
                 }*/
//        val ccEmail = root.ccEmailEdt.text.toString()
//        if (email.isEmpty()) {
//            showToast("Plase enter valid CC Email")
//            return
//        }
//        val city = root.cityEdt.text.toString()
//        if (email.isEmpty()) {
//            showToast("Plase enter valid city")
//            return
//        }
//        val zipCode = root.edtZipCode.text.toString()
//        if (email.isEmpty()) {
//            showToast("Plase enter valid zipCode")
//            return
//        }
                if (arguments != null && arguments!!.containsKey("customerId")) {
                    editCust(firstName, lastName, phone, email)

                } else {
                    addCust(firstName, lastName, phone, email)

                }
            }
        }
    }


    private fun addCust(firstName: String, lastName: String, mobileNumber: String, emailId: String) {
        var emailNotification = 0
        if (root.emailCb.isChecked) {
            emailNotification = 1
        }
        var smslNotification = 0
        if (root.smsCb.isChecked) {
            smslNotification = 1
        }
        var pushNotification = 0
        if (root.pushCb.isChecked) {
            pushNotification = 1
        }
        var allowOnlineBooking = "Yes"
        if (root.noAllowOnlineBooking.isChecked) {
            allowOnlineBooking = "No"
        }

        /* anniversaryEdt.text = dateFormat.format(time!!.time)
         anniversaryEdt.setOnClickListener { v: View? ->
             DatePickerDialog(mActivity!!, DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                 time[Calendar.DAY_OF_MONTH] = dayOfMonth
                 time[Calendar.MONTH] = month
                 time[Calendar.YEAR] = year
                 anniversaryEdt.text = dateFormat.format(time.time)
             }, time[Calendar.YEAR], time[Calendar.MONTH], time[Calendar.DAY_OF_MONTH]).show()
         }*/

        viewModel.addCustomer(PrefManager(requireContext()).vendorId, firstName, lastName, emailId, mobileNumber, root.addressEdt.text.toString(),
                root.edtZipCode.text.toString(), root.cityEdt.text.toString(), stateList[root.spnState.selectedItemPosition].stateId,
                root.ccEmailEdt.text.toString(), root.birthdayEdt.text.toString(), image, genderList[root.spnGender.selectedItemPosition],
                root.emeNameEdt.text.toString(), root.relationEdt.text.toString(), root.emeContactNumber.text.toString(), root.emeContactNumber.text.toString(),
                root.anniversaryEdt.text.toString(), "", root.occupationEdt.text.toString(), emailNotification.toString(), smslNotification.toString(),
                pushNotification.toString(), allowOnlineBooking, root.cardHolderName.text.toString(), root.cardNumber.text.toString(), root.idCvv.text.toString(),
                cardExpMonthList[root.expMonthSpn.selectedItemPosition], cardExpYearList[root.expYearSpn.selectedItemPosition]
        )
        viewModel.liveDataAddCust!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, CustomerLIstFragment())
                        .commit()
//
            }
        })
    }


    private fun editCust(firstName: String, lastName: String, mobileNumber: String, emailId: String) {
        var emailNotification = 0
        if (root.emailCb.isChecked) {
            emailNotification = 1
        }
        var smslNotification = 0
        if (root.smsCb.isChecked) {
            smslNotification = 1
        }
        var pushNotification = 0
        if (root.pushCb.isChecked) {
            pushNotification = 1
        }
        var allowOnlineBooking = "Yes"
        if (root.noAllowOnlineBooking.isChecked) {
            allowOnlineBooking = "No"
        }
        viewModel.editCustomer(PrefManager(requireContext()).vendorId, arguments!!.getString("customerId")!!, firstName, lastName, emailId, mobileNumber, root.addressEdt.text.toString(),
                root.edtZipCode.text.toString(), root.cityEdt.text.toString(), stateList[root.spnState.selectedItemPosition].stateId,
                root.ccEmailEdt.text.toString(), root.birthdayEdt.text.toString(), image, genderList[root.spnGender.selectedItemPosition],
                root.emeNameEdt.text.toString(), root.relationEdt.text.toString(), root.emeContactNumber.text.toString(), root.emeContactNumber.text.toString(),
                root.anniversaryEdt.text.toString(), "", root.occupationEdt.text.toString(), emailNotification.toString(), smslNotification.toString(),
                pushNotification.toString(), allowOnlineBooking, root.cardHolderName.text.toString(), root.cardNumber.text.toString(), root.idCvv.text.toString(),
                cardExpMonthList[root.expMonthSpn.selectedItemPosition], cardExpYearList[root.expYearSpn.selectedItemPosition]
        )
        viewModel.liveDataEditCust!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, CustomerLIstFragment())
                        .commit()
//
            }
        })
    }

    private fun getCustById() {
        viewModel.getCustDetails(arguments!!.getString("customerId")!!)
        viewModel.liveDataCustDetails!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                val result = it.result
                if (result != null) {
                    root.edtFirstName.setText(result.firstname)
                    root.edtLastName.setText(result.lastname)
                    root.emailIdEdt.setText(result.email)
                    root.mobileEdt.setText(result.mobile)
                    /* if ( result.mobile!= null ){
                         root.editMobileNo.setText(result.mobile)
                     }*/
                    root.edtZipCode.setText(result.zipcode)
                    root.addressEdt.setText(result.address)
                    root.cityEdt.setText(result.city)
                    root.birthdayEdt.setText(result.birthday)
                    root.emeNameEdt.setText(result.emergency_contact)
                    root.relationEdt.setText(result.emergency_relation)
                    root.emeContactNumber.setText(result.emergency_contact_no)
                    root.anniversaryEdt.setText(result.anniversary)
                    root.occupationEdt.setText(result.occupation)
                    root.iouLimit.setText(result.iou_limit)
                    root.cardHolderName.setText(result.card_holder_name)
                    root.cardNumber.setText(result.card_number)
                    root.idCvv.setText(result.cvv)


                    for (i in stateList.indices) {
                        if (stateList.get(i).stateId.equals(result.stateId, ignoreCase = true)) {
                            spnState.setSelection(i)
                        }
                    }
                    for (i in 0 until expMonthSpn.getCount()) {
                        if (expMonthSpn.getItemAtPosition(i).toString().equals(result.expiry_month)) {
                            expMonthSpn.setSelection(i)
                        }
                    }
                    for (i in 0 until expYearSpn.getCount()) {
                        if (expYearSpn.getItemAtPosition(i).toString().equals(result.expiry_year)) {
                            expYearSpn.setSelection(i)
                        }
                    }

                }
            }
        })
    }
}

