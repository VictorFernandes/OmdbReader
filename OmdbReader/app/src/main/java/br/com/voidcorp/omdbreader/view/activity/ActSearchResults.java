package br.com.voidcorp.omdbreader.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.voidcorp.omdbreader.OmdbApp;
import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.controller.ControllerActSearchResults;
import br.com.voidcorp.omdbreader.view.IView;

public class ActSearchResults extends AppCompatActivity implements IView {

    public static final String SEARCH_TEXT_EXTRA = "STE";

    private ControllerActSearchResults mController;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_list);
        mController = new ControllerActSearchResults(this);
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
