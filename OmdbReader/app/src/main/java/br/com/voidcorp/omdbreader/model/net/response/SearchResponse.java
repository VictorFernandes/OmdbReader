package br.com.voidcorp.omdbreader.model.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.com.voidcorp.omdbreader.model.Film;

public class SearchResponse extends Response {

    @SerializedName("Search")
    private List<Film> results;


    public List<Film> getResults() {
        if(results == null) {
            results = new ArrayList<>();
        }
        return results;
    }
}
