package br.com.voidcorp.omdbreader.controller;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.model.Content;
import br.com.voidcorp.omdbreader.model.ContentType;
import br.com.voidcorp.omdbreader.model.Film;
import br.com.voidcorp.omdbreader.model.Message;
import br.com.voidcorp.omdbreader.model.net.request.Request;
import br.com.voidcorp.omdbreader.model.net.response.SearchResponse;
import br.com.voidcorp.omdbreader.view.IView;
import br.com.voidcorp.omdbreader.view.activity.ActFilmDetails;
import br.com.voidcorp.omdbreader.view.activity.ActSearchResults;
import br.com.voidcorp.omdbreader.view.adapter.AdpContent;
import br.com.voidcorp.omdbreader.view.holder.HolderFilmList;
import br.com.voidcorp.omdbreader.view.util.EndlessRecyclerViewScrollListener;
import br.com.voidcorp.omdbreader.view.util.MarginItemDecoration;
import br.com.voidcorp.omdbreader.view.util.RecyclerViewItemClickSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerActSearchResults {

    private final Callback<SearchResponse> mSearchResultsListener = new Callback<SearchResponse>() {
        @Override
        public void onResponse(final Call<SearchResponse> call, final Response<SearchResponse> response) {
            mHolder.setProgressEnabled(false);
            if (response.isSuccessful()) {
                onSearchResponseRetrieved(response.body());
            } else {
                // any other circumstance
                setPage(mPage - 1);
                showError(mView.getString(R.string.error_service));
            }
        }

        private void onSearchResponseRetrieved(final SearchResponse searchResponse) {
            if (searchResponse.isSuccessful()) {
                final boolean isNewResults = mLastPage >= mPage;
                notifyFilmList(searchResponse.getResults(), isNewResults);
            } else if (mLastPage == mPage && !TextUtils.isEmpty(searchResponse.getError())) {
                // if search have no results
                showError(searchResponse.getError());
            } else {
                // if search have no results and it's page have over
                mEndlessScrollListener.setEnabled(false);
                setPage(mPage - 1);
                mEndlessScrollListener.rollback();
            }
        }

        @Override
        public void onFailure(final Call<SearchResponse> call, final Throwable t) {
            mHolder.setProgressEnabled(false);
            setPage(mPage - 1);
            if(!mView.getSupportApplication().hasInternet()) {
                showError(mView.getString(R.string.error_no_network_connection));
            } else {
                showError(t.getMessage());
            }
        }
    };

    private final RecyclerViewItemClickSupport.OnItemClickListener mOnSearchResultClickListener = new RecyclerViewItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClicked(final RecyclerView recyclerView, final int position, final View v) {
            final int type = mAdpSearchResult.getItemViewType(position);
            if (type == ContentType.FILM) {
                final Content item = mAdpSearchResult.getItem(position);
                if (item != null) {
                    onFilmItemClick((Film) item);
                }
            }
        }
    };

    private final EndlessRecyclerViewScrollListener mEndlessScrollListener = new EndlessRecyclerViewScrollListener() {
        @Override
        public void onLoadMore(final int page, final int totalItemsCount, final RecyclerView view) {
            search(page, mSearchText);
        }
    };


    private final FloatingSearchView.OnSearchListener mOnSearchListener = new FloatingSearchView.OnSearchListener() {
        @Override
        public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

        }

        @Override
        public void onSearchAction(final String currentQuery) {
            search(1, currentQuery);
        }
    };

    private final IView mView;
    private final HolderFilmList mHolder;

    private int mLastPage = 0;
    private int mPage = 0;
    private Call<SearchResponse> mSearchCall;

    private AdpContent mAdpSearchResult;
    private MarginItemDecoration mMarginItemDecorator;

    private String mSearchText;

    public ControllerActSearchResults(final IView view) {
        mView = view;
        mHolder = new HolderFilmList(mView);
        initialize();
        initializeActions();
    }

    private void initialize() {
        mEndlessScrollListener.setLayoutManager(mHolder.getMyFilms().getLayoutManager());
        mAdpSearchResult = new AdpContent();
        mHolder.getMyFilms().setAdapter(mAdpSearchResult);
        search(1, mView.getIntent().getStringExtra(ActSearchResults.SEARCH_TEXT_EXTRA));
        mHolder.getSearchView().setSearchText(mSearchText);
        mHolder.getSearchView().setLeftActionMode(FloatingSearchView.LEFT_ACTION_MODE_SHOW_SEARCH);
    }

    private void search(final int page, final String text) {
        final Request searchRequest = new Request(text, page);
        mSearchCall = mView.getSupportApplication().getOmdbService().search(searchRequest, mSearchResultsListener);
        setPage(page);
        mHolder.setProgressEnabled(true);
        mSearchText = text;
    }

    private void setPage(final int page) {
        mLastPage = mPage;
        if (page < 0) {
            mPage = 0;
        } else {
            mPage = page;
        }
    }

    private void initializeActions() {
        RecyclerViewItemClickSupport.addTo(mHolder.getMyFilms()).setOnItemClickListener(mOnSearchResultClickListener);
        mHolder.getSearchView().setOnSearchListener(mOnSearchListener);
        mHolder.getMyFilms().addOnScrollListener(mEndlessScrollListener);
    }

    private void onFilmItemClick(final Film item) {
        final Intent intent = new Intent(mView.getContext(), ActFilmDetails.class);
        intent.putExtra(ActFilmDetails.FILM_ID_EXTRA, item.getImdbID());
        mView.startActivity(intent);
    }

    private void showError(final String error) {

        mEndlessScrollListener.setEnabled(false);
        mEndlessScrollListener.resetState();

        mAdpSearchResult.remove(ContentType.FILM);
        mAdpSearchResult.remove(ContentType.MESSAGE);

        final Message message = new Message(error);
        mAdpSearchResult.add(ContentType.MESSAGE, message);
        mAdpSearchResult.show(ContentType.MESSAGE);

        mHolder.getMyFilms().removeItemDecoration(mMarginItemDecorator);
        mMarginItemDecorator = null;

    }

    private void notifyFilmList(final List<Film> results, final boolean newResults) {

        if (mMarginItemDecorator == null) {
            mMarginItemDecorator = new MarginItemDecoration(16);
            mHolder.getMyFilms().addItemDecoration(mMarginItemDecorator);
        }

        mAdpSearchResult.remove(ContentType.MESSAGE);

        if (newResults) {
            mAdpSearchResult.remove(ContentType.FILM);
            mEndlessScrollListener.resetState();
        }

        mEndlessScrollListener.setEnabled(true);

        mAdpSearchResult.addAll(ContentType.FILM, new ArrayList<Content>(results));
        mAdpSearchResult.show(ContentType.FILM);
    }

    public void onBackPressed() {
        mSearchCall.cancel();
    }

}
