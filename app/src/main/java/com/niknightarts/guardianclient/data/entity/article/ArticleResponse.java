package com.niknightarts.guardianclient.data.entity.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleResponse {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
