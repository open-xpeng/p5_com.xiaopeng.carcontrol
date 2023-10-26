package com.xiaopeng.lludancemanager.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.xui.widget.XCircularProgressBar;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class LluDanceViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "LluDanceViewHolder";
    public XImageView mImg;
    private XImageView mImgNewFrag;
    private XCircularProgressBar mProgressBar;
    public XTextView mTvAuthor;
    private XTextView mTvState;
    public XTextView mTvTitle;

    public LluDanceViewHolder(View itemView) {
        super(itemView);
        this.mImg = (XImageView) itemView.findViewById(R.id.img);
        this.mTvTitle = (XTextView) itemView.findViewById(R.id.tv_title);
        this.mTvAuthor = (XTextView) itemView.findViewById(R.id.tv_author);
        this.mTvState = (XTextView) itemView.findViewById(R.id.tv_state);
        this.mProgressBar = (XCircularProgressBar) itemView.findViewById(R.id.llu_activity_dance_item_download_progress);
        this.mImgNewFrag = (XImageView) itemView.findViewById(R.id.img_new);
    }

    public void bind(LluDanceViewData data, int position, boolean activated) {
        this.itemView.setActivated(activated);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.llu_dance_item_place_holder);
        requestOptions.error(R.drawable.llu_dance_item_place_holder);
        Glide.with(App.getInstance()).setDefaultRequestOptions(requestOptions).load(data.getImageThumbnailUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(6))).into(this.mImg);
        this.mTvTitle.setText(data.getTitle());
        this.mTvAuthor.setText(data.getAuthor());
        setStatus(data);
        applyVuiAttr(position, data);
    }

    public void bind(LluDanceViewData data) {
        this.itemView.setActivated(false);
        disableItemVuiAttr();
        this.mImg.setImageResource(R.drawable.llu_dance_more);
        this.mTvTitle.setText(data.getTitle());
        this.mTvAuthor.setText(data.getAuthor());
        setStatus(data);
        this.mTvState.setVisibility(8);
        this.mImgNewFrag.setVisibility(8);
        this.mProgressBar.setVisibility(8);
    }

    private void setStatus(LluDanceViewData data) {
        int state = data.getState();
        if (state == 1) {
            this.mProgressBar.setVisibility(8);
            this.mTvState.setVisibility(0);
            this.mTvState.setText(ResUtils.getString(R.string.llu_dance_not_downloaded));
            this.mImgNewFrag.setImageResource(R.drawable.llu_dance_ic_new);
            this.mImgNewFrag.setVisibility(0);
        } else if (state == 2) {
            this.mProgressBar.setVisibility(8);
            this.mTvState.setVisibility(0);
            this.mImgNewFrag.setVisibility(8);
            this.mTvState.setCompoundDrawablesWithIntrinsicBounds(R.drawable.llu_dance_ic_suspend, 0, 0, 0);
            this.mTvState.setText(ResUtils.getString(R.string.llu_dance_wait));
        } else if (state == 3) {
            this.mProgressBar.setVisibility(0);
            this.mTvState.setVisibility(8);
            this.mProgressBar.setProgress((int) data.getDownloadedPercentage());
            this.mImgNewFrag.setVisibility(8);
        } else if (state == 5) {
            this.mProgressBar.setVisibility(0);
            this.mTvState.setVisibility(8);
            this.mImgNewFrag.setVisibility(8);
            this.mTvState.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.mProgressBar.setProgress((int) data.getDownloadedPercentage());
            this.mTvState.setText(ResUtils.getString(R.string.llu_dance_pause));
        } else if (state == 7) {
            this.mProgressBar.setVisibility(8);
            this.mTvState.setVisibility(0);
            this.mImgNewFrag.setImageResource(R.drawable.llu_dance_ic_new);
            this.mImgNewFrag.setVisibility(0);
            this.mTvState.setText(ResUtils.getString(R.string.llu_dance_not_downloaded));
        } else if (state != 8) {
        } else {
            this.mProgressBar.setVisibility(8);
            this.mTvState.setVisibility(0);
            this.mTvState.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            this.mTvState.setText(getDuration(data.getDuration()));
            this.mImgNewFrag.setVisibility(8);
        }
    }

    private String getDuration(long second) {
        long j = second / 60;
        long j2 = second % 60;
        return (j < 10 ? new StringBuilder().append("0").append(j) : new StringBuilder().append(j).append("")).toString() + QuickSettingConstants.JOINER + (j2 < 10 ? new StringBuilder().append("0").append(j2) : new StringBuilder().append(j2).append("")).toString();
    }

    private void applyVuiAttr(int position, LluDanceViewData data) {
        XConstraintLayout xConstraintLayout = (XConstraintLayout) this.itemView;
        xConstraintLayout.setVuiAction(VuiAction.CLICK.getName());
        xConstraintLayout.setVuiElementType(VuiElementType.BUTTON);
        xConstraintLayout.setVuiElementId(xConstraintLayout.getId() + "_" + position);
        xConstraintLayout.setVuiLabel(String.format(xConstraintLayout.getContext().getString(R.string.vui_label_llu_position_title), Integer.valueOf(position + 1), data.getTitle()));
        VuiUtils.addHasFeedbackProp(xConstraintLayout);
    }

    private void disableItemVuiAttr() {
        ((XConstraintLayout) this.itemView).setVuiMode(VuiMode.DISABLED);
    }
}
