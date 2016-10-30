package br.com.voidcorp.omdbreader.controller.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil extends AsyncTask<Void,Void,Boolean>{

    private static final String CONNECTION_URL = "http://www.google.com";

    private final Context mContext;
    private final OnNetworkStateChangeListener mListener;

    public NetworkUtil(final Context context, final OnNetworkStateChangeListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(final Void... params) {
        final boolean hasInternet = hasInternet(mContext);
        return hasInternet;
    }

    private boolean hasInternet(final Context context) {
        final ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            HttpURLConnection connection = null;
            try {
                final URL url = new URL(CONNECTION_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean hasInternet) {
        super.onPostExecute(hasInternet);
        if(!isCancelled() && mListener != null) {
            mListener.onNetworkChange(hasInternet);
        }
    }

    public interface OnNetworkStateChangeListener {

        void onNetworkChange(final boolean hasInternet);

    }

}
