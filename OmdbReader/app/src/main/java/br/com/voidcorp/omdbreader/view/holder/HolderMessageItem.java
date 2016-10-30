package br.com.voidcorp.omdbreader.view.holder;

import android.view.View;
import android.widget.TextView;

import br.com.voidcorp.omdbreader.model.Content;
import br.com.voidcorp.omdbreader.model.Message;

public class HolderMessageItem extends HolderContent {

    public HolderMessageItem(final View itemView) {
        super(itemView);
    }

    @Override
    public void putValues(final Content content) {
        if (content instanceof Message) {
            final Message message = (Message) content;
            ((TextView) itemView).setText(message.getMessage());
        }
    }


}
