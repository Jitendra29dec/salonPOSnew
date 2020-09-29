package com.hubwallet.apptspos.Utils.Models.PersonalTaskTypeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalTaskTypeList {
    @SerializedName("data")
    @Expose
    private List<PersonalTaskTypeData> data = null;

    public List<PersonalTaskTypeData> getData() {
        return data;
    }

    public void setData(List<PersonalTaskTypeData> data) {
        this.data = data;
    }

}
