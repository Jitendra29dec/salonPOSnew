package com.hubwallet.apptspos.Utils;


import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData;

public interface GroupAppointmentInterface {
    void groupAppointmentCustomerData(GetCustomerData data, String message);

    void groupListPos(int pos, String message);

    void emptyCustomerListNotify();


}
