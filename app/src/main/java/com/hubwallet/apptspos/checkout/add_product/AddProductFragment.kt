package com.hubwallet.apptspos.checkout.add_product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.common.StringUtils
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.checkout.add_service.PurchageListener
import kotlinx.android.synthetic.main.add_product_fragment.view.*

class AddProductFragment : DialogFragment(), PurchageListener {

    companion object {
        fun newInstance() = AddProductFragment()
    }

    private lateinit var viewModel: AddProductViewModel
    private lateinit var root: View
    private var list = ArrayList<Product>()
    private var productList = ArrayList<Product>()
    private var productId = ArrayList<String>()
    private var qtyList = ArrayList<String>()
    private lateinit var productAdapter: ProductAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.add_product_fragment, container, false)
        productAdapter = ProductAdapter(requireContext(), productList, this)
        root.productRv.layoutManager = LinearLayoutManager(requireContext())
        root.productRv.adapter = productAdapter
        root.addProduct.setOnClickListener {
            productId.clear()
            qtyList.clear()
            for (item in list) {
                if (item.purchageQuatity.trim().isNotEmpty()) {
                    productId.add(item.productId)
                    qtyList.add(item.purchageQuatity)
                }
            }
            addProduct()
        }
        root.btnCancel.setOnClickListener {
            dismiss()
        }
        root.searchBtn.setOnClickListener {
            productList.clear()
            productList.addAll(list.filter { it.productName.contains(root.edtSearch.text, false) })
            productAdapter.notifyDataSetChanged()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .80).toInt()
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
        getProduct()
    }

    private fun getProduct() {
        viewModel.getProduct(PrefManager(requireContext()).vendorId)
        viewModel.liveDataAdd.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                list.addAll(it.product)
                productList.addAll(it.product)
                productAdapter.notifyDataSetChanged()
            }
        })

    }

    override fun onChange(quentity: String, position: Int, productId: String) {
        for (product in list) {
            if (product.productId.equals(productId)) {
                product.purchageQuatity = quentity
            }
        }
    }

    private fun addProduct() {
        val id = android.text.TextUtils.join(",", productId);
        val qty = android.text.TextUtils.join(",", qtyList);
        viewModel.addProdcut(id, qty, arguments!!.getString("custumerId")!!)
        viewModel.liveDataAddProdct.observe(viewLifecycleOwner, Observer {
            dismiss()
            val filter = Intent("CHECKOUT");
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(filter)
        })
    }
}