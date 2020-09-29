package com.hubwallet.apptspos.checkout.add_disscount

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.fragment_add_disscount.view.*


class AddDisscountFragment : DialogFragment() {

    lateinit var root: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_disscount, container, false)
        root.disscountRadioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == R.id.customRb) {
                    root.customerLayout.visibility = View.VISIBLE
                    root.persantageLayout.visibility = View.GONE
                } else {
                    root.customerLayout.visibility = View.GONE
                    root.persantageLayout.visibility = View.VISIBLE
                }
            }

        })
        return root
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
    companion object {

        @JvmStatic
        fun newInstance() =
                AddDisscountFragment()
    }
}