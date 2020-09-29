package com.hubwallet.apptspos.Utils.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppSettingData {
    @SerializedName("screen_id")
    @Expose
    private String screenId;
    @SerializedName("wallpaper")
    @Expose
    private String wallpaper;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("video_time_interval")
    @Expose
    private String videoTimeInterval;

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTimeInterval() {
        return videoTimeInterval;
    }

    public void setVideoTimeInterval(String videoTimeInterval) {
        this.videoTimeInterval = videoTimeInterval;
    }
}
