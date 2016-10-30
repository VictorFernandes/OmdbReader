package br.com.voidcorp.omdbreader.controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.model.Content;
import br.com.voidcorp.omdbreader.model.ContentType;
import br.com.voidcorp.omdbreader.model.Film;
import br.com.voidcorp.omdbreader.model.Message;
import br.com.voidcorp.omdbreader.view.IView;
import br.com.voidcorp.omdbreader.view.activity.ActFilmDetails;
import br.com.voidcorp.omdbreader.view.activity.ActSearchResults;
import br.com.voidcorp.omdbreader.view.adapter.AdpContent;
import br.com.voidcorp.omdbreader.view.holder.HolderFilmList;
import br.com.voidcorp.omdbreader.view.util.MarginItemDecoration;
import br.com.voidcorp.omdbreader.view.util.RecyclerViewItemClickSupport;

public class ControllerActMain {

    private final RecyclerViewItemClickSupport.OnItemClickListener mOnFilmsClickListener = new RecyclerViewItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(final RecyclerView recyclerView, final int position, final View v) {
            final int type = mAdpMyFilms.getItemViewType(position);
            if (type == ContentType.FILM) {
                final Film item = (Film) mAdpMyFilms.getItem(position);
                onMyFilmClick(item);
            }
        }
    };

    private final FloatingSearchView.OnSearchListener mOnSearchListener = new FloatingSearchView.OnSearchListener() {
        @Override
        public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

        }

        @Override
        public void onSearchAction(final String currentQuery) {
            mHolder.getSearchView().setSearchText("");
            onSearch(currentQuery);
        }
    };


    private final IView mView;
    private final HolderFilmList mHolder;

    private AdpContent mAdpMyFilms;
    private MarginItemDecoration mMarginItemDecoration;

    public ControllerActMain(final IView view) {
        mView = view;
        mHolder = new HolderFilmList(mView);
        initializeActions();
    }

    private void initializeActions() {
        RecyclerViewItemClickSupport.addTo(mHolder.getMyFilms()).setOnItemClickListener(mOnFilmsClickListener);
        mHolder.getSearchView().setOnSearchListener(mOnSearchListener);
    }

    public void loadMyCollection() {
        mAdpMyFilms = new AdpContent();
        final List<Film> films = SugarRecord.listAll(Film.class);
        if (films.isEmpty()) {
            mAdpMyFilms.add(ContentType.MESSAGE, new Message(mView.getString(R.string.info_empty_collection)));
            mAdpMyFilms.show(ContentType.MESSAGE);
            if(mMarginItemDecoration != null) {
                mHolder.getMyFilms().removeItemDecoration(mMarginItemDecoration);
                mMarginItemDecoration = null;
            }
        } else {
            if(mMarginItemDecoration == null) {
                mMarginItemDecoration = new MarginItemDecoration(16);
                mHolder.getMyFilms().addItemDecoration(mMarginItemDecoration);
            }
            mAdpMyFilms.addAll(ContentType.FILM, new ArrayList<Content>(films));
            mAdpMyFilms.show(ContentType.FILM);
        }
        mHolder.getMyFilms().setAdapter(mAdpMyFilms);
        mHolder.setProgressEnabled(false);
    }


    private void onSearch(final String text) {
        final Intent intent = new Intent(mView.getContext(), ActSearchResults.class);
        intent.putExtra(ActSearchResults.SEARCH_TEXT_EXTRA, text);
        mView.startActivity(intent);
    }

    private void onMyFilmClick(final Film item) {
        final Intent intent = new Intent(mView.getContext(), ActFilmDetails.class);
        intent.putExtra(ActFilmDetails.FILM_EXTRA, item);
        mView.startActivity(intent);
    }

}
