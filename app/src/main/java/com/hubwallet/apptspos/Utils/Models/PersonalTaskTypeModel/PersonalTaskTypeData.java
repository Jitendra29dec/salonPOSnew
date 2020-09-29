package com.hubwallet.apptspos.Utils.Models.PersonalTaskTypeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalTaskTypeData {
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
