
package com.hubwallet.apptspos.Utils.Models.Calendar;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CalenderResponce {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<CalandarData> mResult;
    @SerializedName("status")
    private Long mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<CalandarData> getResult() {
        return mResult;
    }

    public void setResult(List<CalandarData> result) {
        mResult = result;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
