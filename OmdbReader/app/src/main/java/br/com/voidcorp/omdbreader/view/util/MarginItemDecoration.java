package br.com.voidcorp.omdbreader.view.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    private final int mLeft;
    private final int mTop;
    private final int mRight;
    private final int mBottom;

    public MarginItemDecoration(final int space) {
        mLeft = space;
        mTop = space;
        mRight = space;
        mBottom = space;
    }

    public MarginItemDecoration(final int horizontal, final int vertical) {
        mLeft = horizontal;
        mTop = vertical;
        mRight = horizontal;
        mBottom = vertical;
    }

    public MarginItemDecoration(final int left, final int top, final int right, final int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
        outRect.left = mLeft;
        outRect.right = mRight;
        outRect.bottom = mBottom;
        outRect.top = mTop;
    }
}
