package br.com.voidcorp.omdbreader.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.model.Content;
import br.com.voidcorp.omdbreader.model.ContentType;
import br.com.voidcorp.omdbreader.view.holder.HolderContent;
import br.com.voidcorp.omdbreader.view.holder.HolderFilmItem;
import br.com.voidcorp.omdbreader.view.holder.HolderMessageItem;
import br.com.voidcorp.omdbreader.view.holder.HolderNoneItem;

public class AdpContent extends RecyclerView.Adapter<HolderContent> {

    private final SparseArray<List<Content>> mContentMap;

    private int mCurrentType = ContentType.NONE;

    public AdpContent() {
        mContentMap = new SparseArray<>();
        setSource(ContentType.NONE, new ArrayList<Content>(0));
//        show(ContentType.NONE);
    }

    public void setSource(final int type, @NonNull final List<Content> contents) {
//        mSource.clear();
//        addAll(films);
        mContentMap.put(type, contents);
    }

    public void add(final int type, @NonNull final Content content) {
        addAll(type, Collections.singletonList(content));
    }

    public void addAll(final int type, @NonNull final List<Content> contents) {
        List<Content> list = mContentMap.get(type);
        if(list == null) {
            list = contents;
        } else {
            list.addAll(contents);
        }
        setSource(type, list);
    }

    public void remove(final int type) {
        mContentMap.put(type, null);
        mContentMap.remove(type);
    }

    public void show(final int type) {
        mCurrentType = type;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        final Content item = getItem(position);
        if (item == null) {
            return ContentType.NONE;
        } else {
            return item.getType();
        }
    }

    @Override
    public HolderContent onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == ContentType.MESSAGE) {
            return new HolderMessageItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
        } else if (viewType == ContentType.FILM) {
            return new HolderFilmItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false));
        } else {
            return new HolderNoneItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.none_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final HolderContent holder, final int position) {
        final Content item = getItem(position);
        if (item != null) {
            holder.putValues(item);
        }
    }

    public boolean isEmpty() {
        return mContentMap.get(mCurrentType).isEmpty();
    }

    @Override
    public int getItemCount() {
        return mContentMap.get(mCurrentType).size();
    }

    public Content getItem(final int position) {
        final List<Content> source = mContentMap.get(mCurrentType);
        if (source.isEmpty() || position < 0 || position > source.size()) {
            return null;
        } else {
            return source.get(position);
        }
    }

}
