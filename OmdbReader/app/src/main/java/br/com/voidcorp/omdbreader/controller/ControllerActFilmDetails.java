package br.com.voidcorp.omdbreader.controller;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orm.SugarRecord;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.model.Film;
import br.com.voidcorp.omdbreader.model.net.request.Request;
import br.com.voidcorp.omdbreader.view.IView;
import br.com.voidcorp.omdbreader.view.activity.ActFilmDetails;
import br.com.voidcorp.omdbreader.view.holder.HolderActFilmDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerActFilmDetails {

    private final Callback<Film> mOnFilmRetrievedListener = new Callback<Film>() {
        @Override
        public void onResponse(final Call<Film> call, final Response<Film> response) {
            mHolder.setProgressEnabled(false);
            if (response.isSuccessful()) {
                mFilm = response.body();
                mHolder.putValues(mView.getContext(), mFilm);
            }
        }

        @Override
        public void onFailure(final Call<Film> call, final Throwable t) {
            mHolder.setProgressEnabled(false);
            final Snackbar snackbar = mHolder.getMessage(mView.getString(R.string.error_retrieve_film), Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.action_ok, new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mView.finish();
                }
            });
            snackbar.show();
        }
    };


    private final IView mView;
    private final HolderActFilmDetails mHolder;

    private Call<Film> mFilmCaller;

    private Film mFilm;

    public ControllerActFilmDetails(final IView view) {
        mView = view;
        mHolder = new HolderActFilmDetails(mView);
        initialize();
    }

    private void initialize() {
        final Intent intent = mView.getIntent();
        if (intent.hasExtra(ActFilmDetails.FILM_EXTRA)) {
            mFilm = (Film) intent.getSerializableExtra(ActFilmDetails.FILM_EXTRA);
            mHolder.putValues(mView.getContext(), mFilm);
        } else if (intent.hasExtra(ActFilmDetails.FILM_ID_EXTRA)) {
            final Request request = new Request(intent.getStringExtra(ActFilmDetails.FILM_ID_EXTRA));
            mFilmCaller = mView.getSupportApplication().getOmdbService().getFilmByID(request, mOnFilmRetrievedListener);
            mHolder.setProgressEnabled(true);
        }
        mView.setSupportActionBar(mHolder.getToolbar());
        mView.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed() {
        if (mFilmCaller != null && !mFilmCaller.isExecuted()) {
            mFilmCaller.cancel();
        }
    }

    public void setMenu(final Menu menu) {
        mHolder.setMenu(menu);
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.film_details_action_new) {
            onAddToCollection();
            return true;
        } else if (id == R.id.film_details_action_remove) {
            onRemoveFromCollection();
            return true;
        } else if (id == android.R.id.home) {
            mView.onBackPressed();
            return true;
        }
        return false;
    }

    private void onRemoveFromCollection() {
        if (mFilm == null) {
            mHolder.getMessage(mView.getString(R.string.error_delete_film), Snackbar.LENGTH_SHORT).show();
        } else {
            final boolean deleted = SugarRecord.delete(mFilm);
            if (deleted) {
                mFilm.setID(null);
                mHolder.getMessage(mView.getString(R.string.info_delete_film), Snackbar.LENGTH_SHORT).show();
                mView.invalidateOptionsMenu();
            } else {
                mHolder.getMessage(mView.getString(R.string.error_delete_film), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void onAddToCollection() {
        if (mFilm == null) {
            mHolder.getMessage(mView.getString(R.string.error_add_film), Snackbar.LENGTH_SHORT).show();
        } else {
            final long id = SugarRecord.save(mFilm);
            if (id > -1) {
                mHolder.getMessage(mView.getString(R.string.info_add_film), Snackbar.LENGTH_SHORT).show();
                mView.invalidateOptionsMenu();
            } else {
                mHolder.getMessage(mView.getString(R.string.error_add_film), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public void onPrepareOptionsMenu() {
        boolean enabled;
        if (mFilm == null) {
            enabled = mView.getIntent().hasExtra(ActFilmDetails.FILM_ID_EXTRA);
        } else {
            enabled = mFilm.getID() == null || mFilm.getID() == 0;
        }
        mHolder.setAddToCollectionMenuEnabled(enabled);
        mHolder.setRemoveFromCollectionMenuEnabled(!enabled);
    }
}
