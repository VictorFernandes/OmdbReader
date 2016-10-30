package br.com.voidcorp.omdbreader.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.com.voidcorp.omdbreader.OmdbApp;
import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.controller.ControllerActFilmDetails;
import br.com.voidcorp.omdbreader.view.IView;

public class ActFilmDetails extends AppCompatActivity implements IView {

    public static final String FILM_ID_EXTRA = "FIDE";
    public static final String FILM_EXTRA = "FE";

    private ControllerActFilmDetails mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_film_details);
        mController = new ControllerActFilmDetails(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.film_details_action, menu);
        mController.setMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        mController.onPrepareOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return mController.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mController.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public OmdbApp getSupportApplication() {
        return (OmdbApp) getApplication();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
