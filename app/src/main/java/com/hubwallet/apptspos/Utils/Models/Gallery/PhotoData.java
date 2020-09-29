package com.hubwallet.apptspos.Utils.Models.Gallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//commit
public class PhotoData {
        @SerializedName("gallery_id")
        @Expose
        private String galleryId;
        @SerializedName("photo")
        @Expose
        private String photo;

        public String getGalleryId() {
            return galleryId;
        }

        public void setGalleryId(String galleryId) {
            this.galleryId = galleryId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

    }

