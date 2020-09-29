package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalendarColorData {
    @SerializedName("color_id")
    @Expose
    private String colorId;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("color_type")
    @Expose
    private String colorType;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("text_color")
    @Expose
    private String textColor;

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String  textColor) {
        this.textColor = textColor;
    }
}
