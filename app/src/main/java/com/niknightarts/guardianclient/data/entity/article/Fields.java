package com.niknightarts.guardianclient.data.entity.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("bodyText")
    @Expose
    private String bodyText;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

}
