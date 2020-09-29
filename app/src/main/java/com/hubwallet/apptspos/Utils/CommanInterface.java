package com.hubwallet.apptspos.Utils;

import android.view.View;

import com.hubwallet.apptspos.Utils.Models.Calendar.CalandarData;

import java.util.List;

public interface CommanInterface {
     void selectTime(View view, int position, List<CalandarData> mCalanderData);

}
