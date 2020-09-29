package com.hubwallet.apptspos.calander.event_click

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.checkout.CheckoutFragment
import kotlinx.android.synthetic.main.fragment_event_click_dialog.view.*

class EventClickDialog : DialogFragment() {
    lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_event_click_dialog, container, false)
        root.checkoutTv.setOnClickListener {
            val fragment = CheckoutFragment.newInstance()
            fragment.arguments=arguments
            NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, fragment)
                    .commit()
            dialog!!.dismiss()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .30).toInt()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    companion object {

        @JvmStatic
        fun newInstance() = EventClickDialog()
    }
}