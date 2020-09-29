package com.hubwallet.apptspos.employes.schedule

interface OnDayStatusChangeListener {
    fun onStatusChange(isChecked:Boolean,position:Int)
}