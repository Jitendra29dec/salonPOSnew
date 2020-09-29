package com.hubwallet.apptspos.employes.time_shedule

import android.view.View
import androidx.fragment.app.Fragment
import com.hubwallet.apptspos.employes.bio_data.OnTextChangeListener

class TimeShedule : Fragment(), OnTextChangeListener, View.OnClickListener {


    companion object {
        fun newInstance() = TimeShedule();
    }




    override fun onTextChange(cat: String, postion: Int) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}