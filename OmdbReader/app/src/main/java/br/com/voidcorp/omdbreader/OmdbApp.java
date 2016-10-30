package br.com.voidcorp.omdbreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.orm.SugarApp;

import br.com.voidcorp.omdbreader.controller.net.NetworkUtil;
import br.com.voidcorp.omdbreader.controller.net.api.OmdbService;

public class OmdbApp extends SugarApp {

    private final BroadcastReceiver mNetworkStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            checkNetwork();
        }
    };

    private final NetworkUtil.OnNetworkStateChangeListener mOnInternetChangeListener = new NetworkUtil.OnNetworkStateChangeListener() {
        @Override
        public void onNetworkChange(final boolean hasInternet) {
            mHasInternet = hasInternet;
        }
    };


    private OmdbService mOmdbService;

    private boolean mHasInternet;
    private NetworkUtil mNetworkUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        checkNetwork();
        mOmdbService = new OmdbService();
        registerReceiver(mNetworkStatusReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mNetworkStatusReceiver);
    }

    public boolean hasInternet() {
        return mHasInternet;
    }

    public OmdbService getOmdbService() {
        return mOmdbService;
    }

    private void checkNetwork() {
        if(mNetworkUtil != null &&
                (mNetworkUtil.getStatus() == AsyncTask.Status.PENDING || mNetworkUtil.getStatus() == AsyncTask.Status.FINISHED)
                ) {
            mNetworkUtil.cancel(true);
        }
        mNetworkUtil = new NetworkUtil(this, mOnInternetChangeListener);
        mNetworkUtil.execute();
    }

}
