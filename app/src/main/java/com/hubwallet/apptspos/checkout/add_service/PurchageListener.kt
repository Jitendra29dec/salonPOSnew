package com.hubwallet.apptspos.checkout.add_service

import java.text.FieldPosition

interface PurchageListener {
    fun onChange(quentity:String,position:Int,productId:String)
}