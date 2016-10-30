package br.com.voidcorp.omdbreader.controller.net.api;

import br.com.voidcorp.omdbreader.model.Film;
import br.com.voidcorp.omdbreader.model.net.response.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbAPI {

    String BASE_URL = "http://www.omdbapi.com";

    @GET("/")
    Call<SearchResponse> search(
            @Query("s") final String text,
            @Query("plot") final String mode,
            @Query("r") final String type,
            @Query("page") final int page);

    @GET("/")
    Call<Film> getFilmByID(
            @Query("i") final String id,
            @Query("plot") final String mode,
            @Query("r") final String type
    );

}
