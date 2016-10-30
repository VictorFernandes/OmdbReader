package br.com.voidcorp.omdbreader.view.holder;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.view.IView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HolderFilmList {

    private final IView mView;

    @Bind(R.id.films_list)
    RecyclerView mMyFilms;

    @Bind(R.id.films_search_view)
    FloatingSearchView mSearchView;

    private ProgressDialog mProgressDialog;

    public HolderFilmList(final IView view) {
        mView = view;
        ButterKnife.bind(this, (AppCompatActivity) view);
        initialize(view.getContext());
    }

    private void initialize(final Context context) {
        initializeProgress(context);
        mMyFilms.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void initializeProgress(final Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(context.getString(R.string.loading));
    }

    public RecyclerView getMyFilms() {
        return mMyFilms;
    }

    public FloatingSearchView getSearchView() {
        return mSearchView;
    }

    public void setProgressEnabled(final boolean enabled) {
        if(enabled && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        } else if(mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
