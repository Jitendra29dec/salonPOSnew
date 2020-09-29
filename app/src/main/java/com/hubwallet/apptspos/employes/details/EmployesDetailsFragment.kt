package com.hubwallet.apptspos.employes.details

import android.app.Activity
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import com.hubwallet.apptspos.employes.details.emp_type.EmpTypeDetails
import com.hubwallet.apptspos.employes.details.state.StateDetails
import com.hubwallet.apptspos.employes.details.title.EmpTitle
import com.hubwallet.apptspos.employes.ui.addemployes.EmployesPagerAdapter
import com.hubwallet.apptspos.fragments.StylishFragment
import kotlinx.android.synthetic.main.employes_details_fragment.*
import kotlinx.android.synthetic.main.employes_details_fragment.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class EmployesDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = EmployesDetailsFragment()
    }

    private lateinit var viewModel: EmployesDetailsViewModel
    private lateinit var root: View
    var titleList: ArrayList<EmpTitle> = ArrayList<EmpTitle>()
    var empTypeList: ArrayList<EmpTypeDetails> = ArrayList<EmpTypeDetails>()
    var stateList: ArrayList<StateDetails> = ArrayList<StateDetails>()
    var imageString = ""
    var stylistId = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.employes_details_fragment, container, false)
        val stylishid = arguments!!.getString("stylistId")
        if (stylishid != null) {
            root.viewPager.adapter = EmployesPagerAdapter(requireActivity(), stylishid)
        } else {
            root.viewPager.adapter = EmployesPagerAdapter(requireActivity(), "0")
        }

        root.profileImg.setOnClickListener(this)
        root.saveEmpBtn.setOnClickListener(this)
        root.imgBack.setOnClickListener(this)
        TabLayoutMediator(
                root.tabLayout,
                root.viewPager
        ) { tab, position ->
            when (position) {

                0 -> {
                    tab.text = getString(R.string.services)
                }
                1 -> {
                    tab.text = getString(R.string.bio_data)
                    //  tab.text = getString(R.string.schedule)
                }
                2 -> {
                    tab.text = getString(R.string.pin)
                }
                3 -> {
                    tab.text = getString(R.string.workshedule);
                }
                4 -> {
                    tab.text = getString(R.string.gallery)
                }

            }

        }.attach()

        if (arguments != null && arguments!!.containsKey("stylistId")) {
            // root.title.text = "Edit Employee"
            // root.saveCustBtn.text = "Update"
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmployesDetailsViewModel::class.java)
        getType()
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
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "Error Occurred please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.profileImg -> {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 102)
            }

            R.id.saveEmpBtn -> {
                if (arguments != null && arguments!!.containsKey("stylistId")) {
                    validateEditEmpData()
                } else {
                    validateAddEmpData()
                }

            }
            R.id.imgBack -> {
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerEmpMenu, StylishFragment())
                        .commit()
            }
        }
    }

    private fun getType() {
        viewModel.getEmployeeType(PrefManager(requireContext()).vendorId)
        viewModel.liveDataType!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                empTypeList.addAll(it.result)
                val adapter = ArrayAdapter<EmpTypeDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        empTypeList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.empTypeSpinner.adapter = adapter

            }
            getTitle()
        })
    }

    private fun getState() {
        viewModel.getState("231")
        viewModel.liveDataState!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                stateList.add(StateDetails("-1", "SELECT"))
                stateList.addAll(it.result)
                val adapter = ArrayAdapter<StateDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        stateList)
                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.statSpn.adapter = adapter  //selected item will look like a spinner set from XML
                if (arguments != null && arguments!!.containsKey("stylistId")) {
                    getStyListById()
                }
            }
        })
    }

    private fun getTitle() {
        viewModel.getEmployeeTitle(PrefManager(requireContext()).vendorId)
        viewModel.liveDataTitle!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                titleList.addAll(it.result)
                val adapter = ArrayAdapter<EmpTitle>(requireContext(), android.R.layout.simple_spinner_item,
                        titleList)
                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.nameTitleSpn.adapter = adapter  //selected item will look like a spinner set from XML

            }
            getState()
        })
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    private fun validateAddEmpData() {
        if (root.firstNameEdt.text.toString().isEmpty()) {
            root.firstNameEdt.error = "Please enter first name"
            return
            // showToast("Please enter valid first name")
        }
        if (root.lastNameEdt.text.toString().isEmpty()) {
            root.lastNameEdt.error = "Please enter last name"
            //  showToast("Please enter valid last name")
            return
        }
        if (root.edtEmail.text.toString().isEmpty() || !isValidEmail(root.edtEmail.text.toString())) {
            root.edtEmail.error = "Please enter valid email"
            //  showToast("Please enter valid email")
            return
        }
        if (root.edtPhone.text.toString().length < 10) {
            root.edtPhone.error = "Please enter valid phone number"
            //   showToast("Please enter valid phone number")
            root.edtPhone.requestFocus()
            return
        }
        /* if (root.altPhoneEdt.text.toString().isEmpty()) {
             showToast("Please enter valid alternative phone number")
             root.altPhoneEdt.requestFocus()
             return
         }*/
        if (root.edtCity.text.toString().isEmpty()) {
            root.edtCity.error = "Please enter city"
            // showToast("Please enter valid city")
            return

        }
        /*  if (root.addressEdt.text.toString().isEmpty()) {
            root.addressEdt.error ="Please enter valid address"
              //  showToast("Please enter valid address")
              return
          }*/
        val empTypeId = empTypeList[root.empTypeSpinner.selectedItemPosition].empTypeId
        val empTitleId = titleList[root.nameTitleSpn.selectedItemPosition].titleId
        val empStateId = stateList[root.statSpn.selectedItemPosition].stateId
        addEmp(PrefManager(requireContext()).vendorId,
                empTypeId,
                empTitleId,
                root.firstNameEdt.text.toString(),
                root.lastNameEdt.text.toString(),
                root.edtEmail.text.toString(),
                root.edtPhone.text.toString(),
                root.altPhoneEdt.text.toString(),
                empStateId,
                root.edtCity.text.toString(),
                root.addressEdt.text.toString(),
                imageString
        )

    }

    private fun validateEditEmpData() {
        if (root.firstNameEdt.text.toString().isEmpty()) {
            root.firstNameEdt.error = "Please enter first name"
            //showToast("Please enter valid first name")
            return
        }
        if (root.lastNameEdt.text.toString().isEmpty()) {
            root.lastNameEdt.error = "Please enter last name"
            // showToast("Please enter valid last name")
            return
        }
        if (root.edtEmail.text.toString().isEmpty() || !isValidEmail(root.edtEmail.text.toString())) {
            root.edtEmail.error = "Please enter valid email"
            // showToast("Please enter valid email")
            return
        }
        if (root.edtPhone.text.toString().length < 10) {
            showToast("Please enter valid phone number")
            root.edtPhone.requestFocus()
            return
        }

        if (root.edtCity.text.toString().isEmpty()) {
            root.edtCity.error = "Please enter city"
            // showToast("Please enter valid city")
            return

        }
        /* if (root.addressEdt.text.toString().isEmpty()) {
             showToast("Please enter valid address")
             return
         }*/
        val empTypeId = empTypeList[root.empTypeSpinner.selectedItemPosition].empTypeId
        val empTitleId = titleList[root.nameTitleSpn.selectedItemPosition].titleId
        val empStateId = stateList[root.statSpn.selectedItemPosition].stateId

        editEmp(PrefManager(requireContext()).vendorId,
                empTypeId,
                empTitleId,
                root.firstNameEdt.text.toString(),
                root.lastNameEdt.text.toString(),
                root.edtEmail.text.toString(),
                root.edtPhone.text.toString(),
                root.altPhoneEdt.text.toString(),
                empStateId,
                root.edtCity.text.toString(),
                root.addressEdt.text.toString(),
                imageString, arguments!!.getString("stylistId")!!
        )
    }

    private fun addEmp(vendorId: String,
                       emp_type_id: String,
                       title_id: String,
                       firstname: String,
                       lastname: String,
                       email: String,
                       phone: String,
                       alternate_phone: String,
                       state_id: String,
                       city: String,
                       address: String,
                       photo: String) {
        viewModel.addEmployee(vendorId, emp_type_id, title_id, firstname, lastname, email, phone, alternate_phone, state_id, city, address, photo)
        viewModel.liveDataAddEmp!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
            if (it.status == 1) {
                stylistId = it.stylistId
                root.viewPager.adapter = EmployesPagerAdapter(requireActivity(), stylistId)
                NavigationActivity.fm.beginTransaction().replace(R.id.containerEmpMenu, StylishFragment()).commit()
            }

        })
    }

    private fun editEmp(vendorId: String,
                        emp_type_id: String,
                        title_id: String,
                        firstname: String,
                        lastname: String,
                        email: String,
                        phone: String,
                        alternate_phone: String,
                        state_id: String,
                        city: String,
                        address: String,
                        photo: String,
                        stylist_id: String) {
        viewModel.editEmployee(vendorId, emp_type_id, title_id, firstname, lastname, email, phone, alternate_phone, state_id, city, address, photo, stylist_id)
        viewModel.liveDataEditEmp!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
            if (it.status == 1) {
                stylistId = it.stylistId
                root.viewPager.adapter = EmployesPagerAdapter(requireActivity(), stylistId)
                NavigationActivity.fm.beginTransaction().replace(R.id.containerEmpMenu, StylishFragment()).commit()
            }

        })
    }

    private fun getStyListById() {
        viewModel.getEmpDetails(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId")!!)
        viewModel.liveDataEmpDetails!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                val result = it.result
                if (result != null) {
                    root.firstNameEdt.setText(result.firstname)
                    root.lastNameEdt.setText(result.lastname)
                    root.edtEmail.setText(result.email)
                    root.edtPhone.setText(result.phone)
                    root.edtCity.setText(result.city)
                    root.addressEdt.setText(result.address)

                    for (i in stateList.indices) {
                        if (stateList.get(i).stateId.equals(result.state_id, ignoreCase = true)) {
                            statSpn.setSelection(i)
                        }
                    }
                }

            }
        })
    }
}
