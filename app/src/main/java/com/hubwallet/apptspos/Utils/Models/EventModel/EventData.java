package com.hubwallet.apptspos.Utils.Models.EventModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hubwallet.apptspos.calander.DepositDetails;
import com.hubwallet.apptspos.calander.Result;

import java.util.List;

public class EventData {

    @SerializedName("result")
    @Expose
    private List<Result> message = null;

    @SerializedName("deposit")
    @Expose
    private List<DepositDetails> depositDetailsList = null;

    public List<Result> getMessage() {
        return message;
    }

    public void setMessage(List<Result> message) {
        this.message = message;
    }

    public List<DepositDetails> getDepositDetailsList() {
        return depositDetailsList;
    }

    public void setDepositDetailsList(List<DepositDetails> depositDetailsList) {
        this.depositDetailsList = depositDetailsList;
    }
}