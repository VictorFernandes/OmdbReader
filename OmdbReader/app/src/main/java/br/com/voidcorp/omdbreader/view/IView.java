package br.com.voidcorp.omdbreader.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import br.com.voidcorp.omdbreader.OmdbApp;

public interface IView {

    /**
     * @see Activity#getBaseContext()
     * @return {@link Context}
     */
    Context getBaseContext();

    /**
     * @see Activity#getApplicationContext()
     * @return {@link Context}
     */
    Context getApplicationContext();

    /**
     * @see Activity#getAssets()
     * @return {@link AssetManager}
     */
    AssetManager getAssets();

    /**
     * @see Activity#getString(int)
     * @param resId
     * @return {@link String}
     */
    String getString(final int resId);

    String getString(int resId, Object... formatArgs);

    /**
     * @see Activity#getResources()
     * @return {@link Resources}
     */
    Resources getResources();

    /**
     * @see Activity#findViewById(int)
     * @param id
     * @return {@link View}
     */
    View findViewById(final int id);

    FragmentManager getSupportFragmentManager();
    
    /**
     * @see Activity#getSharedPreferences(String, int)
     * @param name
     * @param mode
     * @return {@link SharedPreferences}
     */
    SharedPreferences getSharedPreferences(final String name, final int mode);

    void startActivity(final Intent intent);

    void startActivityForResult(Intent intent, int requestCode);

    void finish();
    
    void onBackPressed();
    
    void setResult(int resultCode, Intent data);
    
    Intent getIntent();

    void setSupportActionBar(final Toolbar toolbar);

    ActionBar getSupportActionBar();

    OmdbApp getSupportApplication();

    ComponentName getComponentName();

    Object getSystemService(final String name);

    Window getWindow();

    void invalidateOptionsMenu();

    Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter);

    Context getContext();
}