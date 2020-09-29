package com.hubwallet.apptspos.base

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.PrefManager
import kotlinx.android.synthetic.main.dialog_pin.*
import org.json.JSONObject


open class BaseActivity : AppCompatActivity() {
    val myHandler = Handler();
    val myRunnable = Runnable() {
        kotlin.run {
            if (warningTime == PrefManager(this).getIntValue(PrefManager.LOCK_TIME)) {
                Toast.makeText(this, "Screen Lock Enable", Toast.LENGTH_LONG).show()
            }
            warningTime -= 1

            showDialog()
        }
    }
//    Business5%
    val myHandlerWarning = Handler();
    val myRunnableWarning = Runnable() {
        kotlin.run {
                Toast.makeText(this, "Screen Lock Enable", Toast.LENGTH_LONG).show()

        }
    }
    var warningTime = 0
    lateinit var dialog: Dialog
    lateinit var viewModel: BaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BaseViewModel::class.java)

    }

    open fun showDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_pin)
        dialog.btnContinue.setOnClickListener {
            val pin = dialog.pinEditText.text.toString()
            if (pin.length < 4) {
                Toast.makeText(this, "Enter Valid Pin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            checkPin(pin)
        }
        dialog.show()
    }

    fun checkPin(pin: String) {
        viewModel.checkPin(pin)
        viewModel.pinLiveData!!.observe(this, Observer {
            if (it.status == 1) {
                dialog.dismiss()
                if (PrefManager(this).getIntValue(PrefManager.ACTIVITY_SCREEN_lOCK) == 1) {
                    myHandler.removeCallbacks(myRunnable);
                    myHandler.postDelayed(myRunnable, PrefManager(this).getIntValue(PrefManager.LOCK_TIME) * 1000L);
                    warningTime = PrefManager(this).getIntValue(PrefManager.WARNING_TIME)
                    myHandlerWarning.removeCallbacks(myRunnableWarning);
                    myHandlerWarning.postDelayed(myRunnableWarning, PrefManager(this).getIntValue(PrefManager.WARNING_TIME) * 1000L);
                }
            }
        })
    }

    fun screenLockTime() {
        viewModel.screenLockTime(PrefManager(this).vendorId)
        viewModel.screenLockTimeData!!.observe(this, Observer {
            if (it.status == 1) {
                val manager = PrefManager(this)
                manager.saveInt(PrefManager.ACTIVITY_SCREEN_lOCK, it.active)
                manager.saveInt(PrefManager.LOCK_TIME, it.locktime)
                manager.saveInt(PrefManager.WARNING_TIME, it.warningTime)
                if (it.active == 1) {
                    warningTime = PrefManager(this).getIntValue(PrefManager.WARNING_TIME)
                    myHandler.removeCallbacks(myRunnable);
                    myHandler.postDelayed(myRunnable, (it.locktime * 1000).toLong())
                    warningTime = PrefManager(this).getIntValue(PrefManager.WARNING_TIME)
                    myHandlerWarning.removeCallbacks(myRunnableWarning);
                    myHandlerWarning.postDelayed(myRunnableWarning, PrefManager(this).getIntValue(PrefManager.WARNING_TIME) * 1000L);
                }

            }
        })
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (PrefManager(this).getIntValue(PrefManager.ACTIVITY_SCREEN_lOCK) == 1) {
            myHandler.removeCallbacks(myRunnable);
            myHandler.postDelayed(myRunnable, PrefManager(this).getIntValue(PrefManager.LOCK_TIME) * 1000L);
            warningTime = PrefManager(this).getIntValue(PrefManager.WARNING_TIME)
            myHandlerWarning.removeCallbacks(myRunnableWarning);
            myHandlerWarning.postDelayed(myRunnableWarning, PrefManager(this).getIntValue(PrefManager.WARNING_TIME) * 1000L);
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler.removeCallbacks(myRunnable);
    }
}