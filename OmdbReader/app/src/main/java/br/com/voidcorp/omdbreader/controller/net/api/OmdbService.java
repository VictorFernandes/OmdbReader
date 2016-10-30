package br.com.voidcorp.omdbreader.controller.net.api;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import br.com.voidcorp.omdbreader.model.Film;
import br.com.voidcorp.omdbreader.model.net.request.Request;
import br.com.voidcorp.omdbreader.model.net.response.SearchResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OmdbService {

    private OmdbAPI mApi;

    public OmdbService() {
        initialize();
    }

    private void initialize() {

        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(20L, TimeUnit.SECONDS);
        httpBuilder.readTimeout(20L, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(20L, TimeUnit.SECONDS);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(OmdbAPI.BASE_URL)
                .client(httpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()));

        mApi = builder.build().create(OmdbAPI.class);

    }

    public Call<SearchResponse> search(final Request request, final Callback<SearchResponse> callback) {
        final Call<SearchResponse> searchCall = mApi.search(request.getSearch(), request.getPlot(), request.getResponseType(), request.getPage());
        searchCall.enqueue(callback);
        return searchCall;
    }

   public Call<Film> getFilmByID(final Request request, final Callback<Film> callback) {
       final Call<Film> filmByID = mApi.getFilmByID(request.getId(), request.getPlot(), request.getResponseType());
       filmByID.enqueue(callback);
       return filmByID;
   }

}
