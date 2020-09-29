package com.hubwallet.apptspos.checkout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.checkout.add_cetificate.AddCertificateFragment
import com.hubwallet.apptspos.checkout.add_disscount.AddDisscountFragment
import com.hubwallet.apptspos.checkout.add_gift.AddGiftCardDialog
import com.hubwallet.apptspos.checkout.add_product.AddProductFragment
import com.hubwallet.apptspos.checkout.add_service.AddServiceCheckoutDialog
import com.hubwallet.apptspos.checkout.disscount_res.service.ServiceDicountDetails
import com.hubwallet.apptspos.checkout.payment.AddDepositPaymentDialogFragment
import com.hubwallet.apptspos.customer.cust_details.CustomerDetails
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.checkout_fragment.*
import kotlinx.android.synthetic.main.checkout_fragment.view.*


class CheckoutFragment : Fragment(), View.OnClickListener, RemoveListener {
    var empTypeList: ArrayList<CustomerDetails> = ArrayList<CustomerDetails>()

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var giftCerificateAdapter: GiftCerificateAdapter
    private lateinit var giftCardNumberAdapter: GiftCardNumberAdapter
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var root: View
    private lateinit var customerId: String
    private var serviceList = ArrayList<Service>()
    private var productList = ArrayList<Product>()
    private var giftCerList = ArrayList<Certificate>()
    private var giftCardList = ArrayList<GiftCard>()
    private var serviceDisscountList = ArrayList<ServiceDicountDetails>()
    private var productDisscountList = ArrayList<ServiceDicountDetails>()
    private var totalServiceCharge = 0.0
    private var totalServiceTaxAmount = 0.0
    private var totalProductCharge = 0.0
    private var totalProductTaxAmount = 0.0
    private var totalGiftCardAmount = 0.0
    private var totalTotalCertificateAmount = 0.0
    private var totalPaybleAmount = 0.0
    private var totalDisscount = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.checkout_fragment, container, false)
        root.serviceRV.layoutManager = LinearLayoutManager(requireContext())
        serviceAdapter = ServiceAdapter(requireContext(), serviceList, serviceDisscountList, this)
        root.serviceRV.adapter = serviceAdapter
        root.productRV.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(requireContext(), productList, productDisscountList, this)
        root.productRV.adapter = productAdapter
        giftCerificateAdapter = GiftCerificateAdapter(requireContext(), giftCerList, this)
        giftCardNumberAdapter = GiftCardNumberAdapter(requireContext(), giftCardList, this)
        root.giftcardRV.layoutManager = LinearLayoutManager(requireContext())
        root.giftcardRV.adapter = giftCardNumberAdapter
        root.giftcerRV.layoutManager = LinearLayoutManager(requireContext())

        root.giftcerRV.adapter = giftCerificateAdapter
        val localBroadcastManager = LocalBroadcastManager.getInstance(context!!)
        val filter = IntentFilter("CHECKOUT");
        localBroadcastManager.registerReceiver(listener, filter)
        root.addGiftCard.setOnClickListener(this)
        root.addCertificate.setOnClickListener(this)
        root.addProduct.setOnClickListener(this)
        root.addServiceButton.setOnClickListener(this)
        root.submitBtn.setOnClickListener(this)
        root.payNowTv.setOnClickListener(this)
        root.addDisscount.setOnClickListener(this)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        if (arguments != null && arguments!!.containsKey("custumerId")) {
            customerId = arguments!!.getString("custumerId")!!
            getCheckoutData(arguments!!.getString("custumerId")!!, arguments!!.getString("appimentId")!!,
                    arguments!!.getString("type")!!, arguments!!.getString("token")!!)
            getCustomerList()

        } else {
            root.addServiceButton.visibility = View.GONE
            getProductDiscount()
        }
    }

    private fun getCustomerList() {
        viewModel.getCustomerList(PrefManager(requireContext()).vendorId)
        viewModel.liveDataCustDetails!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                empTypeList.clear()
                val custustomer = CustomerDetails()
                custustomer.customerName = "Select"
                empTypeList.addAll(it.result)
                val adapter = ArrayAdapter<CustomerDetails>(requireContext(), android.R.layout.simple_spinner_item,
                        empTypeList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spnCustomer.adapter = adapter
            }
        })
    }

    private fun getCheckoutData(customerId: String, appointmentId: String, type: String, tokenNo: String) {
        var search = "calender"
        if (tokenNo.isEmpty()) {
            search = "search"
        }
        viewModel.checkoutData(customerId, appointmentId, type, tokenNo, PrefManager(requireContext()).vendorId, search)
//        viewModel.checkoutData("124", "1311", "3", "210", PrefManager(requireContext()).vendorId, search)
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                serviceList.clear()
                serviceList.addAll(it.services)
                if (serviceList.isEmpty()) {
                    root.serviceLayout.visibility = View.GONE
                } else {
                    root.serviceLayout.visibility = View.VISIBLE
                    var afterDiscountService = 0.0
                    for (service in serviceList) {
                        service.afterDisscunt = service.price
                        val totalAmount = service.price + service.tax_amount
                        service.finalAmount = totalAmount
                        totalServiceCharge += totalAmount
                        afterDiscountService += service.afterDisscunt
                        totalServiceTaxAmount += service.tax_amount
                    }
                    root.serviceTotalPrice.text = "$ $afterDiscountService"
                    root.serviceFinalAmountTv.text = "$ $totalServiceCharge"
                    root.sideServiceCharge.text = "$ $totalServiceCharge"
                    root.taxServiceTv.text = "$ $totalServiceTaxAmount"
                    root.afterDiscountService.text = "$ $afterDiscountService"
                }
                serviceAdapter.notifyDataSetChanged()
                productList.clear()
                productList.addAll(it.product)
                if (productList.isEmpty()) {
                    root.productLayout.visibility = View.GONE
                } else {
                    var productCharge = 0.0
                    for (service in productList) {
                        service.priceWithQty = service.price_retail * service.quantity
                        service.afterDisscunt = service.priceWithQty
                        val totalAmount = service.priceWithQty + service.tax_amount
                        service.finalAmount = totalAmount
                        totalProductCharge += totalAmount
                        productCharge += service.priceWithQty
                        totalProductTaxAmount += service.tax_amount
                    }
                    root.productPrice.text = productCharge.toString()
                    root.productAfterDiscount.text = productCharge.toString()
                    root.producttaxTv.text = totalProductTaxAmount.toString()
                    root.productFinalAmountTv.text = totalProductCharge.toString()
                    root.totalChargeProductSide.text = totalProductCharge.toString()
                    root.productLayout.visibility = View.VISIBLE
                }
                giftCerList.clear()
                giftCardList.clear()

                productAdapter.notifyDataSetChanged()
                if (it.gift_card.isNullOrEmpty()) {
                    root.giftCardLayout.visibility = View.GONE
                } else {
                    root.giftCardLayout.visibility = View.VISIBLE
                    giftCardList.addAll(it.gift_card)

                    for (giftCardData in giftCardList) {
                        totalGiftCardAmount += giftCardData.amount
                    }
                    root.sideGiftCard.text = "$ $totalGiftCardAmount"

                }

                if (it.certificate.isNullOrEmpty()) {
                    root.certificateLayout.visibility = View.GONE
                } else {
                    giftCerList.addAll(it.certificate)
                    for (giftCertificate in giftCerList) {
                        totalTotalCertificateAmount += giftCertificate.amount

                    }
                    root.sideGiftCertificateTv.text = "$ $totalTotalCertificateAmount"

                    root.certificateLayout.visibility = View.VISIBLE
                }
                giftCerificateAdapter.notifyDataSetChanged()
                giftCardNumberAdapter.notifyDataSetChanged()


            }
            caculateTotalAmount()
            getServiceDisscunt()

        })
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.addServiceButton -> {
                val dialog = AddServiceCheckoutDialog()
                if (arguments != null) {
                    dialog.arguments = arguments
                }
                dialog.show(NavigationActivity.fm, "")
            }
            R.id.addProduct -> {
                val dialog = AddProductFragment()
                if (arguments != null) {
                    dialog.arguments = arguments
                } else {
                    if (::customerId.isInitialized) {
                        val bundle = Bundle()
                        bundle.putString("custumerId", customerId)
                        dialog.arguments = bundle
                    } else {
                        showToast("Please select Customer")
                        return
                    }
                }
                dialog.show(NavigationActivity.fm, "")
            }
            R.id.addCertificate -> {
                val dialog = AddCertificateFragment()
                if (arguments != null) {
                    dialog.arguments = arguments
                } else {
                    if (::customerId.isInitialized) {
                        val bundle = Bundle()
                        bundle.putString("custumerId", customerId)
                        dialog.arguments = bundle
                    } else {
                        showToast("Please select Customer")
                        return
                    }

                }
                dialog.show(NavigationActivity.fm, "")
            }
            R.id.addGiftCard -> {
                val dialog = AddCertificateFragment()
                if (arguments != null) {
                    dialog.arguments = arguments
                } else {
                    if (::customerId.isInitialized) {
                        val bundle = Bundle()
                        bundle.putString("custumerId", customerId)
                        dialog.arguments = bundle
                    } else {
                        showToast("Please select Customer")
                        return
                    }

                }
                val fragment = AddGiftCardDialog()
                fragment.arguments = arguments
                fragment.show(NavigationActivity.fm, "")
            }
            R.id.submitBtn -> {
//                arguments = null
                customerId = empTypeList[root.spnCustomer.selectedItemPosition].customerId
                val bundle = Bundle()
                bundle.putString("custumerId", customerId)

                arguments = bundle

//                getCheckoutData(customerId, "0", "", "")
                getCheckoutData("117", "0", "", "")
            }
            R.id.addDisscount -> {
                AddDisscountFragment().show(NavigationActivity.fm, "")
            }
            R.id.payNowTv -> {
                if (!::customerId.isInitialized) {
                    showToast("Please Select customer")
                    return
                }
                val fragment = AddDepositPaymentDialogFragment.getInstance("16", totalPaybleAmount.toString())
                val bundle = Bundle()
                bundle.putString("custumerId", customerId)
                if (serviceList.isEmpty()) {
                    bundle.putString("appointment_id", "0")
                    bundle.putString("service_total_price", "0")
                } else {
                    val ids = TextUtils.join(",", serviceList)
                    bundle.putString("appointment_id", ids)
                    var totalAmount: Double = 0.0
                    for (service in serviceList) {
                        totalAmount += service.price
                    }
                    bundle.putString("service_total_price", totalAmount.toString())
                }
                if (arguments != null && arguments!!.containsKey("token")) {
                    bundle.putString("token", arguments!!.getString("token")!!)
                } else {
                    bundle.putString("token", "0")
                }
                if (giftCardList.isEmpty()) {
                    bundle.putString("gift_cart_id", "0")
                } else {
                    bundle.putString("gift_cart_id", giftCardList[0].card_id)
                }
                if (giftCerList.isEmpty()) {
                    bundle.putString("gift_certificate_id", "0")
                } else {
                    bundle.putString("gift_certificate_id", giftCerList[0].gift_id)
                }
                bundle.putString("tax", (totalProductTaxAmount + totalServiceTaxAmount).toString())
                bundle.putString("gift_card_value", totalGiftCardAmount.toString())
                bundle.putString("gift_card_value", totalGiftCardAmount.toString())
                bundle.putString("discountValue", disscountSideTv.text.toString())
                fragment.arguments = bundle
                NavigationActivity.fm.beginTransaction()
                        .replace(R.id.containerMain, fragment)
                        .commit()
            }

        }
    }

    val listener = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (arguments != null && arguments!!.containsKey("custumerId")) {
                customerId = arguments!!.getString("custumerId")!!
                getCheckoutData(arguments!!.getString("custumerId")!!, arguments!!.getString("appimentId")!!,
                        arguments!!.getString("type")!!, arguments!!.getString("token")!!)
//                getCustomerList()

            }
        }

    };

    override fun onRemove(id: String, type: String, position: Int) {
        viewModel.deleteCheckoutRow(type, id)
        viewModel.liveDataDelete!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                when (type) {
                    "product" -> {
                        //df
                        productList.removeAt(position)
                        productAdapter.notifyDataSetChanged()
                        if (productList.isEmpty()) {
                            root.productLayout.visibility = View.GONE
                        }
                    }
                    "service" -> {
                        serviceList.removeAt(position)
                        serviceAdapter.notifyDataSetChanged()
                        if (serviceList.isEmpty()) {
                            root.serviceLayout.visibility = View.GONE
                        }
                    }
                    "certificate" -> {
                        giftCerList.removeAt(position)
                        giftCerificateAdapter.notifyDataSetChanged()
                        if (giftCerList.isEmpty()) {
                            root.certificateLayout.visibility = View.GONE
                        }
                        for (giftCertificate in giftCerList) {
                            totalTotalCertificateAmount += giftCertificate.amount

                        }
                        root.sideGiftCertificateTv.text = "$ $totalTotalCertificateAmount"

                    }
                    "gift_card" -> {
                        giftCardList.removeAt(position)
                        giftCardNumberAdapter.notifyDataSetChanged()
                        if (giftCardList.isEmpty()) {
                            root.giftCardLayout.visibility = View.GONE
                        }
                        totalGiftCardAmount = 0.0
                    }
                }
                caculateTotalAmount()
            }

        })
    }

    override fun onSelectionChange(type: String, position: Int, totalAmount: Double, discountAmount: Double, afterDiscount: Double, selectionId: Int) {
        when (type) {
            "product" -> {
                productList[position].finalAmount = totalAmount
                productList[position].discountAmount = discountAmount
                productList[position].afterDisscunt = afterDiscount
                productList[position].selectedDisscountPosition = selectionId
                caculateTotalAmount()
            }
            "service" -> {
                serviceList[position].finalAmount = totalAmount
                serviceList[position].discountAmount = discountAmount
                serviceList[position].afterDisscunt = afterDiscount
                serviceList[position].selectedDisscountPosition = selectionId
                caculateTotalAmount()
            }

        }

    }

    fun getProductDiscount() {
        viewModel.getProductDiscount(PrefManager(requireContext()).vendorId)
        viewModel.liveDataProductDisscount!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                if (it.status == 1) {
                    productDisscountList.clear()
                    val details = ServiceDicountDetails()
                    details.discount = "Select"
                    productDisscountList.add(details)
                    productDisscountList.addAll(it.products)
                    productAdapter.notifyDataSetChanged()
                }
                getCustomerList()
                caculateTotalAmount()
            }

        })
    }

    fun getServiceDisscunt() {
        viewModel.getServiceDisscunt(PrefManager(requireContext()).vendorId)
        viewModel.liveDataServiceDisscount!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                if (it.status == 1) {
                    val details = ServiceDicountDetails()
                    details.discount = "Select"
                    serviceDisscountList.add(details)
                    serviceDisscountList.addAll(it.services)
                    serviceAdapter.notifyDataSetChanged()

                }
            }
            getProductDiscount()
        })
    }

    private fun caculateTotalAmount() {
        totalServiceTaxAmount = 0.0
        totalServiceCharge = 0.0
        totalProductTaxAmount = 0.0
        totalProductCharge = 0.0
        totalDisscount = 0.0

        var afterDiscountService = 0.0
        var serviceTotalPriceValue = 0.0
        if (serviceList.size > 0) {

            for (service in serviceList) {
                val totalAmount = service.finalAmount
                service.finalAmount = totalAmount
                totalServiceCharge += totalAmount
                serviceTotalPriceValue += service.price
                afterDiscountService += service.afterDisscunt
                totalServiceTaxAmount += service.tax_amount
                totalDisscount += service.discountAmount
            }

        }
        var productChargeafterDiscount = 0.0
        var productCharge = 0.0
        if (productList.size > 0) {

            for (service in productList) {
                val totalAmount = service.finalAmount
                service.finalAmount = totalAmount
                totalProductCharge += totalAmount
                productCharge += service.priceWithQty
                productChargeafterDiscount += service.afterDisscunt
                totalProductTaxAmount += service.tax_amount
                totalDisscount += service.discountAmount
            }


        }
        for (giftCertificate in giftCerList) {
            totalTotalCertificateAmount += giftCertificate.amount

        }
        root.sideGiftCertificateTv.text = "$ $totalTotalCertificateAmount"

        root.serviceTotalPrice.text = "$ $serviceTotalPriceValue"
        root.serviceFinalAmountTv.text = "$ $totalServiceCharge"
        root.sideServiceCharge.text = "$ $totalServiceCharge"
        root.taxServiceTv.text = "$ $totalServiceTaxAmount"
        root.afterDiscountService.text = "$ $afterDiscountService"
        root.totalChargeProductSide.text = "$ $totalProductCharge"
        root.productPrice.text = "$ $productCharge"
        root.productAfterDiscount.text = "$ $productChargeafterDiscount"
        root.producttaxTv.text = "$ $totalProductTaxAmount"
        root.productFinalAmountTv.text = "$ $totalProductCharge"
        root.disscountSideTv.text = totalDisscount.toString()
        root.sideTax.text = "$ ${totalProductTaxAmount + totalServiceTaxAmount}"
        totalPaybleAmount = totalProductCharge + totalServiceCharge + totalGiftCardAmount + totalTotalCertificateAmount
        root.totalChargableAmountTv.text = "$ $totalPaybleAmount"
    }


}
