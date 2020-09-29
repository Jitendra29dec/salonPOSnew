package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTemplateData {
    @SerializedName("template_id")
    @Expose
    private String templateId;
    @SerializedName("template_name")
    @Expose
    private String templateName;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
