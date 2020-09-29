package com.hubwallet.apptspos.employes.ui.addemployes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hubwallet.apptspos.employes.bio_data.BioDataFragment
import com.hubwallet.apptspos.employes.gallery.Gallryview
import com.hubwallet.apptspos.employes.pin.EmpPinFragment
import com.hubwallet.apptspos.employes.schedule.ScheduleFragment
import com.hubwallet.apptspos.employes.service.EmpServicesFragment

class EmployesPagerAdapter(activity: FragmentActivity, var stylistId: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("stylistId", stylistId)
        when (position) {
            0 -> {
                val fragment = EmpServicesFragment.newInstance()
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                val fragment = BioDataFragment.newInstance()
                fragment.arguments = bundle
                return fragment
                /*  val fragment = ScheduleFragment.newInstance()
                  fragment.arguments = bundle
                  return fragment*/
            }
            2 -> {
                val fragment = EmpPinFragment.newInstance()
                fragment.arguments = bundle
                return fragment
            }
            3 -> {
                val fragment = ScheduleFragment.newInstance()
                fragment.arguments = bundle
                return fragment
            }
            4 -> {
                val fragment =Gallryview.newInstance()
                fragment.arguments =bundle
                return fragment
            }

        }
        return Fragment()
    }
}