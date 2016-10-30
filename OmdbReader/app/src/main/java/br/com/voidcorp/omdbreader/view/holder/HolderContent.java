package br.com.voidcorp.omdbreader.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.voidcorp.omdbreader.model.Content;

public abstract class HolderContent extends RecyclerView.ViewHolder {
    public HolderContent(final View itemView) {
        super(itemView);
    }

    public abstract void putValues(final Content content);

}
