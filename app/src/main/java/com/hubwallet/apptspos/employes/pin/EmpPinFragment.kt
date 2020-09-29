package com.hubwallet.apptspos.employes.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.emp_pin_fragment.view.*
import java.util.*


class EmpPinFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = EmpPinFragment()
    }

    private lateinit var root: View
    private lateinit var viewModel: EmpPinViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.emp_pin_fragment, container, false)
        root.saveEmpPinBtn.setOnClickListener(this)
        root.genratePinBtn.setOnClickListener(this)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmpPinViewModel::class.java)
    }

    private fun addEmpPin(pin: String) {
        viewModel.saveEmpPin(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId",""), pin)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun editEmpPin(pin: String) {
        viewModel.editEmpPin(PrefManager(requireContext()).vendorId, arguments!!.getString("stylistId",""), pin)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
        showToast(it.message)
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.genratePinBtn -> {
                val r = Random()
                val randomNumber = java.lang.String.format("%04d", r.nextInt(1001))
                root.pinEdt.setText(randomNumber)
            }
            R.id.saveEmpPinBtn -> {
                val pin = root.pinEdt.text.toString()
                if (pin.length < 4) {
                    showToast("Please enter valid PIN")
                    return
                }
                if (arguments != null && arguments!!.containsKey("stylistId")){
                    editEmpPin(pin)
                }else{
                    addEmpPin(pin)
                }
            }
        }
    }

}
