package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("appointment_detail")
    @Expose
    private String appointmentDetail;
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("confirmation_time")
    @Expose
    private String confirmationTime;
    @SerializedName("appointment_reminder")
    @Expose
    private String appointmentReminder;
    @SerializedName("appointment_reminder_time")
    @Expose
    private String appointmentReminderTime;
    @SerializedName("business_notification")
    @Expose
    private String businessNotification;
    @SerializedName("stylist_notification")
    @Expose
    private String stylistNotification;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getAppointmentDetail() {
        return appointmentDetail;
    }

    public void setAppointmentDetail(String appointmentDetail) {
        this.appointmentDetail = appointmentDetail;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(String confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public String getAppointmentReminder() {
        return appointmentReminder;
    }

    public void setAppointmentReminder(String appointmentReminder) {
        this.appointmentReminder = appointmentReminder;
    }

    public String getAppointmentReminderTime() {
        return appointmentReminderTime;
    }

    public void setAppointmentReminderTime(String appointmentReminderTime) {
        this.appointmentReminderTime = appointmentReminderTime;
    }

    public String getBusinessNotification() {
        return businessNotification;
    }

    public void setBusinessNotification(String businessNotification) {
        this.businessNotification = businessNotification;
    }

    public String getStylistNotification() {
        return stylistNotification;
    }

    public void setStylistNotification(String stylistNotification) {
        this.stylistNotification = stylistNotification;
    }


}
