package com.hubwallet.apptspos.calander.add_deposit

interface OnTextViewClickListener {
    //1 date 2 start time 3 end time
    fun clickHandle(value: Int, position: Int)
    fun remove(position: Int)
    fun onItemSelectListener(action:Int, spnPosition: Int, position: Int) {

    }

    fun onDurationChange(position: Int, duration: String) {

    }
}