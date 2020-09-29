package com.hubwallet.apptspos.checkout.payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import com.hubwallet.apptspos.demo.ui.demo.showToast
import com.hubwallet.apptspos.fragments.CalenderFragment
import kotlinx.android.synthetic.main.add_deposit_payment_dialog.view.*
import java.text.DecimalFormat


class AddDepositPaymentDialogFragment : Fragment(), View.OnClickListener {
    lateinit var root: View
    var clickAction = -1

    companion object {
        var fragmentDialogFragment: AddDepositPaymentDialogFragment? = null
        var totalAmountPrice: String = ""
        var IdValue: String = ""
        fun getInstance(depositId: String, totalAmount: String): AddDepositPaymentDialogFragment {
            if (fragmentDialogFragment == null) {
                fragmentDialogFragment = AddDepositPaymentDialogFragment()

            }
            val bundle = Bundle()
            bundle.getString("Id", depositId)
            bundle.getString("totalAmount", totalAmount)
            IdValue = depositId
            totalAmountPrice = totalAmount
//            fragmentDialogFragment!!.arguments = bundle
//            fragmentDialogFragment!!.arguments = bundle
            return fragmentDialogFragment!!
        }
    }



    private var totalDueAmountValue = 0.0
    private var rewardPoints = 0.0
    private var coupon = 0.0
    private var cardPrice = 0.0
    private var certificatePrice = 0.0
    lateinit var viewModel: PaymentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.add_deposit_payment_dialog, container, false)
        root.totalDepositAmount.text = totalAmountPrice
        root.totalDueAmount.text = totalAmountPrice
        root.balanceDue.text = totalAmountPrice

        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        root.totalDepositAmount.setOnClickListener(this)
        root.cashAmountTv.setOnClickListener(this)
        root.caseTipAmount.setOnClickListener(this)
        root.totalDueAmount.setOnClickListener(this)
        root.creditAmountTv.setOnClickListener(this)
        root.iouLimit.setOnClickListener(this)
        root.rewardAmountTv.setOnClickListener(this)
        root.collectButton.setOnClickListener(this)
        root.dallerFiveBtn.setOnClickListener(this)
        root.tenDollerBtn.setOnClickListener(this)
        root.buttonDollarTwenty.setOnClickListener(this)
        root.buttonTwentyFirveDoller.setOnClickListener(this)
        root.thirtyDollerBtn.setOnClickListener(this)
        root.buttonExact.setOnClickListener(this)

        root.oneTv.setOnClickListener(this)
        root.twoTv.setOnClickListener(this)
        root.threeTv.setOnClickListener(this)
        root.fourTv.setOnClickListener(this)
        root.fiveTv.setOnClickListener(this)
        root.sixTv.setOnClickListener(this)
        root.sevenTv.setOnClickListener(this)
        root.eightTv.setOnClickListener(this)
        root.nineTv.setOnClickListener(this)
        root.doubleZeroTv.setOnClickListener(this)
        root.zeroTv.setOnClickListener(this)
        root.delTv.setOnClickListener(this)
        root.applyCouponCode.setOnClickListener(this)
        root.collectButton.setOnClickListener(this)

        root.giftCardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                checkNumber("cart", p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        root.certificateNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                checkNumber("certificate", p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        if (arguments != null) {
            getReward(arguments!!.getString("custumerId")!!)

        }
        return root
    }

    fun setShorcutAmount(amount: String) {
        when (clickAction) {
            1 -> {

                root.cashAmountTv.text = amount
            }
            2 -> {

                root.iouLimit.text = amount
            }
            3 -> {

                root.rewardAmountTv.text = amount
            }
            4 -> {

                root.creditAmountTv.text = amount
            }
            5 -> {

                root.caseTipAmount.text = amount
            }
        }
        totalDueCaculation()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.buttonExact -> {
                setShorcutAmount(totalAmountPrice.toString())
            }
            R.id.thirtyDollerBtn -> {
                setShorcutAmount("30.00")
            }
            R.id.buttonTwentyFirveDoller -> {
                setShorcutAmount("25.00")
            }
            R.id.buttonDollarTwenty -> {
                setShorcutAmount("20.00")
            }
            R.id.tenDollerBtn -> {
                setShorcutAmount("10.00")
            }

            R.id.dallerFiveBtn -> {
                setShorcutAmount("5.00")
            }
            R.id.collectButton -> {
                if (arguments == null) {
                    updateStatus()
                } else {
                    payNow()
                }
            }

            R.id.cashAmountTv -> {
                root.cashAmountTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.edt_bg_gray)
                root.creditAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.rewardAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.iouLimit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                clickAction = 1
            }
            R.id.iouLimit -> {
                clickAction = 2
                root.iouLimit.background = ContextCompat.getDrawable(requireContext(), R.drawable.edt_bg_gray)
                root.cashAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.rewardAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.creditAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }
            R.id.rewardAmountTv -> {
                clickAction = 3
                root.rewardAmountTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.edt_bg_gray)
                root.cashAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.creditAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.iouLimit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }
            R.id.creditAmountTv -> {
                clickAction = 4
                root.creditAmountTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.edt_bg_gray)
                root.cashAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.rewardAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.iouLimit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            R.id.caseTipAmount -> {
                clickAction = 5
                root.caseTipAmount.background = ContextCompat.getDrawable(requireContext(), R.drawable.edt_bg_gray)
                root.creditAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.cashAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.rewardAmountTv.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                root.iouLimit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

            R.id.oneTv -> {
                addText("1")
            }

            R.id.twoTv -> {
                addText("2")
            }

            R.id.threeTv -> {
                addText("3")
            }

            R.id.fourTv -> {
                addText("4")
            }

            R.id.fiveTv -> {
                addText("5")
            }
            R.id.sixTv -> {
                addText("6")
            }
            R.id.sevenTv -> {
                addText("7")
            }
            R.id.eightTv -> {
                addText("8")
            }
            R.id.nineTv -> {
                addText("9")
            }
            R.id.doubleZeroTv -> {
                addZero("00")
            }
            R.id.zeroTv -> {
                addZero("0")
            }
            R.id.delTv -> {
                remove()
            }
            R.id.applyCouponCode -> {
                val couponEdt = root.couponEdt.text.toString()
                checkCuppon(couponEdt)
            }

        }
    }


    private fun remove() {
        when (clickAction) {
            1 -> {
                val length = (root.cashAmountTv.text.length) - 1
                if (length <= 0) {
                    root.cashAmountTv.text = ""
                    return
                }
                var string = root.cashAmountTv.text.substring(0, length)
                Log.d("delete", string)
                string = string.replace(".", "", false);
                Log.d("delete1", string)
                var double = string.toDouble()
                Log.d("delete2", string)
                double /= 100
                Log.d("delete3", string)
                double = DecimalFormat("##.##").format(double).toDouble()
                Log.d("delete4", string)
                root.cashAmountTv.text = String.format("%.2f", double)

                totalDueCaculation()
            }
            2 -> {
                val length = (root.iouLimit.text.length) - 1
                if (length <= 0) {
                    root.iouLimit.text = ""
                    return
                }
                var string = root.iouLimit.text.substring(0, length)

                string = string.replace(".", "", false);
                var double = string.toDouble()
                double /= 100
                root.iouLimit.text = DecimalFormat("##.##").format(double).toString()
                totalDueCaculation()
            }
            3 -> {

                val length = (root.rewardAmountTv.text.length) - 1
                if (length <= 0) {
                    root.rewardAmountTv.text = ""
                    return
                }
                var string = root.rewardAmountTv.text.substring(0, length)

                string = string.replace(".", "", false);
                var double = string.toDouble()
                double /= 100
                root.rewardAmountTv.text = DecimalFormat("##.##").format(double).toString()
                totalDueCaculation()
            }
            4 -> {
                val length = (root.creditAmountTv.text.length) - 1
                if (length <= 0) {
                    root.creditAmountTv.text = ""
                    return
                }
                var string = root.creditAmountTv.text.substring(0, length)

                string = string.replace(".", "", false);
                var double = string.toDouble()
                double /= 100
                root.creditAmountTv.text = DecimalFormat("##.##").format(double).toString()
                totalDueCaculation()
            }
            5 -> {
                val length = (root.caseTipAmount.text.length) - 1
                if (length <= 0) {
                    root.creditAmountTv.text = ""
                    return
                }
                var string = root.caseTipAmount.text.substring(0, length)

                string = string.replace(".", "", false);
                string = string.replace("$", "", false);
                var double = string.toDouble()
                double /= 100
                root.caseTipAmount.text = DecimalFormat("##.##").format(double).toString()
                totalDueCaculation()
            }
        }
    }

    private fun addZero(value: String) {
        when (clickAction) {
            1 -> {
                var string = "${root.cashAmountTv.text}"
                var double = string.toDouble()

                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    if (value.equals("0")) {
                        double *= 10
                    } else {
                        double *= 100
                    }
                    double /= 100
//                    double /= 100
                } else {
                    double /= 100
                }
                root.cashAmountTv.text = String.format("%.2f", double)
                totalDueCaculation()
            }
            2 -> {
                var string = "${root.iouLimit.text}"
                var double = string.toDouble()

                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    if (value.equals("0")) {
                        double *= 10
                    } else {
                        double *= 100
                    }
                    double /= 100
//                    double /= 100
                } else {
                    double /= 100
                }
                root.iouLimit.text = String.format("%.2f", double)
                totalDueCaculation()
            }
            3 -> {
                var string = "${root.rewardAmountTv.text}"
                var double = string.toDouble()

                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    if (value.equals("0")) {
                        double *= 10
                    } else {
                        double *= 100
                    }
                    double /= 100
//                    double /= 100
                } else {
                    double /= 100
                }
                root.rewardAmountTv.text = String.format("%.2f", double)
                totalDueCaculation()
            }
            4 -> {
                var string = "${root.creditAmountTv.text}"
                var double = string.toDouble()

                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    if (value.equals("0")) {
                        double *= 10
                    } else {
                        double *= 100
                    }
                    double /= 100
//                    double /= 100
                } else {
                    double /= 100
                }
                root.creditAmountTv.text = String.format("%.2f", double)
                totalDueCaculation()
            }
            5 -> {
                var string = "${root.caseTipAmount.text}"
                string = string.replace("$", "", false);
                var double = string.toDouble()

                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    if (value.equals("0")) {
                        double *= 10
                    } else {
                        double *= 100
                    }
                    double /= 100
//                    double /= 100
                } else {
                    double /= 100
                }
                root.caseTipAmount.text = String.format("%.2f", double)
                totalDueCaculation()
            }
        }
    }

    private fun addText(value: String) {
        when (clickAction) {
            1 -> {
                var string = "${root.cashAmountTv.text}$value"
                var double = string.toDouble()
                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    double /= 100
//                    double /= 100
                } else {
                    double /= 100
                }
                root.cashAmountTv.text = String.format("%.2f", double)
                totalDueCaculation()
            }
            2 -> {
                var string = "${root.iouLimit.text}$value"
                var double = string.toDouble()
                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    double /= 100
                } else {
                    double /= 100
                }
                root.iouLimit.text = double.toString()
                totalDueCaculation()
            }
            3 -> {
                var string = "${root.rewardAmountTv.text}$value"
                var double = string.toDouble()
                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    double /= 100
                } else {
                    double /= 100
                }
                root.rewardAmountTv.text = double.toString()
                totalDueCaculation()
            }
            4 -> {
                var string = "${root.creditAmountTv.text}$value"
                var double = string.toDouble()
                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    double /= 100
                } else {
                    double /= 100
                }
                root.creditAmountTv.text = double.toString()
                totalDueCaculation()
            }
            5 -> {
                var string = "${root.caseTipAmount.text}$value"
                string = string.replace("$", "", false);

                var double = string.toDouble()
                if (string.contains(".")) {
                    string = string.replace(".", "", false);
                    double = string.toDouble()
                    double /= 100
                } else {
                    double /= 100
                }
                root.caseTipAmount.text = double.toString()
                totalDueCaculation()
            }
        }
    }

    private fun updateStatus() {
        viewModel.updateDeposit(depositId = IdValue)
        viewModel.liveDataUpdateDepositStatus!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
//            fragmentDialogFragment = null
//            NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, CalenderFragment())
            NavigationActivity.fm.beginTransaction()
                    .replace(R.id.containerMain, CalenderFragment())
                    .commit()

        })
    }

    private fun getReward(customerId: String) {
        viewModel.getReward(customerId)
        viewModel.liveDataCustReward!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                rewardPoints = it.amount
                root.rewardHeading.text = "Reward $$rewardPoints"
            }
        })
    }

    private fun checkCuppon(couponCode: String) {
        viewModel.checkCuppon(couponCode)
        viewModel.liveDataCoupon!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                coupon = it.amount
                root.couponHeading.text = "Coupon $${it.amount}"
                totalDueCaculation()
            }
        })
    }

    private fun checkNumber(type: String, number: String) {
        viewModel.checkNumber(type, number)
        viewModel.liveDataCheckNumber!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                if (type.equals("cart")) {
                    cardPrice = it.amount
                    root.giftCardHeading.text = "Gift Card $${it.amount}"
                } else {
                    certificatePrice = it.amount
                    root.ceritficateHeading.text = "Certificate $${it.amount}"
                }
                totalDueCaculation()
            }
        })
    }

    private fun totalDueCaculation() {
        var totalEnterAmount = 0.0
        if (root.cashAmountTv.text.isNotEmpty()) {
            val cashamoutnStr = root.cashAmountTv.text.toString()
            val doubleCash = cashamoutnStr.toDouble()
            totalEnterAmount += doubleCash
        }
        if (root.creditAmountTv.text.isNotEmpty()) {
            val cashamoutnStr = root.creditAmountTv.text.toString()
            val doubleCash = cashamoutnStr.toDouble()
            totalEnterAmount += doubleCash
        }
        if (root.rewardAmountTv.text.isNotEmpty()) {
            val cashamoutnStr = root.rewardAmountTv.text.toString()
            val doubleCash = cashamoutnStr.toDouble()
            totalEnterAmount += doubleCash
        }
        if (root.iouLimit.text.isNotEmpty()) {
            val cashamoutnStr = root.iouLimit.text.toString()
            val doubleCash = cashamoutnStr.toDouble()
            totalEnterAmount += doubleCash
        }
        totalEnterAmount += coupon
//        totalEnterAmount += rewardPoints
        totalEnterAmount += cardPrice
        totalEnterAmount += certificatePrice
        totalDueAmountValue = totalAmountPrice.toDouble() - totalEnterAmount
        root.balanceDue.text = totalDueAmountValue.toString()
    }

    private fun payNow() {

        viewModel.orderNow(arguments!!.getString("custumerId")!!, arguments!!.getString("appointment_id")!!, arguments!!.getString("token")!!,
                "1", arguments!!.getString("tax")!!, root.caseTipAmount.text.toString(), root.cashAmountTv.text.toString(),
                root.creditAmountTv.text.toString(), root.giftCardNumber.text.toString(), root.certificateNumber.text.toString(), arguments!!.getString("gift_cart_id")!!,
                arguments!!.getString("gift_certificate_id")!!, "", "", "", arguments!!.getString("discountValue")!!,
                cardPrice.toString(), root.giftCardNumber.text.toString(), root.giftCardSelectedValue.text.toString(), "0", totalAmountPrice.toString(), arguments!!.getString("gift_certificate_id")!!,
                arguments!!.getString("gift_cart_id")!!, arguments!!.getString("service_total_price")!!, PrefManager(requireContext()).vendorId)
        viewModel.liveDataOrderNow!!.observe(viewLifecycleOwner, Observer {
            if (it.status == 1) {
                val fragment = CalenderFragment()
                val navigationActivity = requireActivity() as NavigationActivity
                navigationActivity.curretItem = 1
                NavigationActivity.fm.beginTransaction().replace(R.id.containerMain, fragment).commit()
            }
        })
    }
}