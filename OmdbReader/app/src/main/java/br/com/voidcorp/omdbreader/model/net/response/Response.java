package br.com.voidcorp.omdbreader.model.net.response;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Ignore;

public class Response {

    @Ignore
    private int totalResults;

    @SerializedName("Response")
    @Ignore
    private boolean response;

    @SerializedName("Error")
    @Ignore
    private String error;

    public int getTotalResults() {
        return totalResults;
    }

    public boolean isSuccessful() {
        return response;
    }

    public String getError() {
        return error;
    }
}
