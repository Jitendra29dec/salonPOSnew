package com.hubwallet.apptspos.employes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.checkout.CheckoutFragment
import com.hubwallet.apptspos.manage_group.ManageGroupFragment

class AddEmployesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_employes_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ManageGroupFragment.newInstance())
                    .commitNow()
        }
    }
}
