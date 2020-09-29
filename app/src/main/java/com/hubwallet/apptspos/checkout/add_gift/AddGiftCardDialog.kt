package com.hubwallet.apptspos.checkout.add_gift

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.checkout.add_cetificate.SelectionListener
import com.hubwallet.apptspos.checkout.add_cetificate.TampletAdapter
import com.hubwallet.apptspos.checkout.add_cetificate.template_dat.TemplateData
import com.hubwallet.apptspos.checkout.add_cetificate.templet_res.Template
import kotlinx.android.synthetic.main.add_certificate_fragment.view.*
import kotlinx.android.synthetic.main.add_gift_card_dialog_fragment.view.*
import kotlinx.android.synthetic.main.add_gift_card_dialog_fragment.view.btnCancel
import kotlinx.android.synthetic.main.add_gift_card_dialog_fragment.view.imgClose
import kotlinx.android.synthetic.main.add_gift_card_dialog_fragment.view.issueEdt
import kotlinx.android.synthetic.main.add_gift_card_dialog_fragment.view.spnTamplet
import kotlinx.android.synthetic.main.add_gift_card_dialog_fragment.view.templateRv
import java.util.*
import kotlin.collections.ArrayList

class AddGiftCardDialog : DialogFragment(), SelectionListener {

    companion object {
        fun newInstance() = AddGiftCardDialog()
    }

    lateinit var tampletAdapter: TampletAdapter
    var templetList = ArrayList<Template>()
    var typeList = ArrayList<String>()
    var imageList = ArrayList<TemplateData>()
    var selectedPosition = 0
    private lateinit var viewModel: AddGiftCardDialogViewModel
    private lateinit var root: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.add_gift_card_dialog_fragment, container, false)
        tampletAdapter = TampletAdapter(requireContext(), imageList, this)
        root.templateRv.layoutManager = GridLayoutManager(requireContext(), 3)
        root.templateRv.isNestedScrollingEnabled = false
        root.templateRv.adapter = tampletAdapter
        root.btnAddGiftCard.setOnClickListener {
            addGiftCard()
        }
        root.issueEdt.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val mont = month + 1
                root.issueEdt.text = "$year-$mont-$dayOfMonth"

            }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        }
        root.btnCancel.setOnClickListener {
            dismiss()
        }
        root.imgClose.setOnClickListener {
            dismiss()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (width * .70).toInt()
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddGiftCardDialogViewModel::class.java)
        getTemplate()
    }

    private fun getTemplate() {
        viewModel.getTemplate("1")

        viewModel.liveDateTemplates.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                templetList.addAll(it.template)
                val adapter = ArrayAdapter<Template>(requireContext(), android.R.layout.simple_spinner_item,
                        templetList)

                adapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item)
                root.spnTamplet.adapter = adapter
                root.spnTamplet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        getTemplateData(templetList[position].templateId)
                    }

                }
            }
        })
    }

    private fun getTemplateData(id: String) {
        viewModel.getTemplateData(id)

        viewModel.liveDateTemplatesData.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                imageList.clear()
                imageList.addAll(it.templateData)
                tampletAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun addGiftCard() {

        viewModel.addGiftCard(arguments!!.getString("custumerId")!!, root.cardNumbeerEdt.text.toString(), root.issueEdt.toString(), root.buyersNameField.toString(), root.buyersEmaildField.toString(),
                root.phoneEdt.toString(), root.messageField.toString(), root.amountEdt.toString(), "", imageList[selectedPosition].id, PrefManager(requireContext()).vendorId, PrefManager(requireContext()).logiId)
        viewModel.liveDateTAddCard.observe(viewLifecycleOwner, Observer {
            dialog!!.dismiss()
            val filter = Intent("CHECKOUT");
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(filter)
        })
    }

    override fun onSelection(position: Int) {
        if (selectedPosition >= 0) {
            imageList[selectedPosition].isChecked = false
        }
        selectedPosition = position
        imageList[position].isChecked = true
        tampletAdapter.notifyDataSetChanged()
    }

}