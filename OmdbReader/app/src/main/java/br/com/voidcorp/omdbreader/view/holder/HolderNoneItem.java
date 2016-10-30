package br.com.voidcorp.omdbreader.view.holder;

import android.view.View;

import br.com.voidcorp.omdbreader.model.Content;

public class HolderNoneItem extends HolderContent {
    public HolderNoneItem(final View itemView) {
        super(itemView);
    }

    @Override
    public void putValues(final Content content) {
        // do nothing
    }
}
