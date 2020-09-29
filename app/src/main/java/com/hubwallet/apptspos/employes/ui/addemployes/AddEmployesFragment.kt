package com.hubwallet.apptspos.employes.ui.addemployes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.add_employes_fragment.view.*
import kotlinx.android.synthetic.main.add_employes_fragment.view.viewPager

class AddEmployesFragment : Fragment() {

    companion object {
        fun newInstance() = AddEmployesFragment()
    }

    private lateinit var viewModel: AddEmployesViewModel
    private lateinit var root: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        root = inflater.inflate(R.layout.add_employes_fragment, container, false)

        root. viewPager.adapter=EmployesPagerAdapter(requireActivity(),"")
        TabLayoutMediator(
                root.tabLayout,
                root.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.employee_details)
                }
                1 -> {
                    tab.text = getString(R.string.services)
                }
                2 -> {
                    tab.text = getString(R.string.schedule)
                }
                3 -> {
                    tab.text = getString(R.string.bio_data)
                }
                4 -> {
                    tab.text = getString(R.string.pin)
                }
            }

        }.attach()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEmployesViewModel::class.java)
    }

}
