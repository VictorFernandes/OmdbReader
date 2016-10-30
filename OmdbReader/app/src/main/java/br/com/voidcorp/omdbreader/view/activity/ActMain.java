package br.com.voidcorp.omdbreader.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.voidcorp.omdbreader.OmdbApp;
import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.controller.ControllerActMain;
import br.com.voidcorp.omdbreader.view.IView;

public class ActMain extends AppCompatActivity implements IView {

    private ControllerActMain mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_list);
        mController = new ControllerActMain(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mController.loadMyCollection();
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
