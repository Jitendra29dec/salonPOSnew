package com.hubwallet.apptspos.manage_group

import android.widget.BaseExpandableListAdapter
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hubwallet.apptspos.R

class ExpandableListAdapter(var context: Context):  BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return 3
    }
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    override fun getChildView(
            listPosition: Int,
            expandedListPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater =
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
//        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.listView)
//        expandedListTextView.text = expandedListText
        return convertView!!
    }
    override fun getChildrenCount(listPosition: Int): Int {
        return 3
    }
    override fun getGroup(listPosition: Int): Any {
//        return this.titleList[listPosition]
        return  listPosition
    }
    override fun getGroupCount(): Int {
        return 5
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun getGroupView(
            listPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup
    ): View {
        var convertView = convertView
//        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.group_view, null)
        }
//        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listView)
//        istTitleTextView.setTypeface(null, Typeface.BOLD)
//        listTitleTextView.text = listTitle
        return convertView!!
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}