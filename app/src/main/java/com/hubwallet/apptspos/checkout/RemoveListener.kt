package com.hubwallet.apptspos.checkout

interface RemoveListener {
    fun onRemove(id: String, type: String, position: Int)
    fun onSelectionChange(type: String, position: Int, totalAmount: Double, discountAmount: Double, afterDiscount: Double,selectionId:Int)
}