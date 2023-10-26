package com.xiaopeng.xpmeditation.view.adapter;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public class HorizatalLinearItemDecoration extends RecyclerView.ItemDecoration {
    private boolean includeEdge;
    private boolean justEdge;
    private int leftSpacing;
    private int rightSpacing;
    private int spacing;

    public HorizatalLinearItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    public HorizatalLinearItemDecoration(int spacing, int leftSpacing) {
        this.spacing = spacing;
        this.leftSpacing = leftSpacing;
    }

    public void setIncludeEdge(boolean includeEdge) {
        this.includeEdge = includeEdge;
        if (includeEdge) {
            this.justEdge = false;
        }
    }

    public void setJustEdge(boolean justEdge) {
        this.justEdge = justEdge;
        if (justEdge) {
            this.includeEdge = false;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = 0;
        outRect.bottom = 0;
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount() - 1;
        if (childAdapterPosition == 0) {
            if (this.includeEdge || this.justEdge) {
                outRect.left = this.spacing;
            } else {
                int i = this.leftSpacing;
                if (i > 0) {
                    outRect.left = i;
                }
            }
        } else if (!this.justEdge) {
            outRect.left = this.spacing;
        }
        if (childAdapterPosition == itemCount) {
            if (this.includeEdge || this.justEdge) {
                outRect.right = this.spacing;
            }
        }
    }
}
