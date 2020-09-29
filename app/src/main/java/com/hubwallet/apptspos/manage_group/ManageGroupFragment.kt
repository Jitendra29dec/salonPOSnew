package com.hubwallet.apptspos.manage_group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hubwallet.apptspos.R
import kotlinx.android.synthetic.main.fragment_manage_group.view.*


class ManageGroupFragment : Fragment() {

    lateinit var root: View
    lateinit var expandableListAdapter: ExpandableListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_manage_group, container, false)
        expandableListAdapter = ExpandableListAdapter(requireContext())
        root.expandableList.setAdapter(expandableListAdapter)
        return root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ManageGroupFragment()
    }
}