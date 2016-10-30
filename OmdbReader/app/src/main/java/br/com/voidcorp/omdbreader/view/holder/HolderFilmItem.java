package br.com.voidcorp.omdbreader.view.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.voidcorp.omdbreader.R;
import br.com.voidcorp.omdbreader.model.Content;
import br.com.voidcorp.omdbreader.model.Film;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HolderFilmItem extends HolderContent {

    @Bind(R.id.my_film_item_cover)
    ImageView mFilmCover;

    @Bind(R.id.my_film_item_title)
    TextView mFilmTitle;

    public HolderFilmItem(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void putValues(final Content content) {
        if (content instanceof Film) {
            final Film film = (Film) content;
            mFilmTitle.setText(film.getTitle());
            Glide.with(itemView.getContext()).load(film.getPoster()).into(mFilmCover);
        }
    }


}
