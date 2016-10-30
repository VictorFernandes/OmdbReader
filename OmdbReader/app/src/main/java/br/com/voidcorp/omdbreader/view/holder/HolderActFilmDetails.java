package br.com.voidcorp.omdbreader.view.holder;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.model.Film;
import br.com.voidcorp.omdbreader.view.IView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HolderActFilmDetails {

    @Bind(R.id.act_film_detail_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.act_film_detail_cover)
    ImageView mFilmCover;

    @Bind(R.id.act_film_detail_actors)
    TextView mFilmActors;

    @Bind(R.id.act_film_detail_description)
    TextView mFilmDescription;

    @Bind(R.id.act_film_detail_released)
    TextView mFilmReleased;

    @Bind(R.id.act_film_detail_writers)
    TextView mFilmWriters;

    @Bind(R.id.act_film_detail_title)
    CollapsingToolbarLayout mFilmTitle;

    private ProgressDialog mProgressDialog;

    private MenuItem mAddToCollectionMenu;
    private MenuItem mRemoveFromCollectionMenu;


    public HolderActFilmDetails(final IView view) {
        ButterKnife.bind(this, (AppCompatActivity) view);
        initializeProgressDialog(view.getContext());
    }

    public void setMenu(final Menu menu) {
        if(menu != null) {
            mAddToCollectionMenu = menu.findItem(R.id.film_details_action_new);
            mRemoveFromCollectionMenu = menu.findItem(R.id.film_details_action_remove);
        }
    }

    private void initializeProgressDialog(final Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(context.getString(R.string.loading));
    }

    public void putValues(final Context context, final Film value) {
        Glide.with(context).load(value.getPoster()).into(mFilmCover);
        mFilmActors.setText(value.getActors());
        mFilmDescription.setText(value.getPlot());
        mFilmReleased.setText(value.getReleased());
        mFilmWriters.setText(value.getWriter());

        mFilmTitle.setTitle(value.getTitle());

    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setProgressEnabled(final boolean enabled) {
        if (enabled && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        } else if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public Snackbar getMessage(final String message, final int duration) {
        return Snackbar.make(mFilmTitle, message, duration);
    }

    public void setAddToCollectionMenuEnabled(final boolean enabled) {
        if(mAddToCollectionMenu != null) {
            mAddToCollectionMenu.setVisible(enabled);
        }
    }

    public void setRemoveFromCollectionMenuEnabled(final boolean enabled) {
        if(mRemoveFromCollectionMenu != null) {
            mRemoveFromCollectionMenu.setVisible(enabled);
        }
    }
}
