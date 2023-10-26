package com.xiaopeng.xpmeditation.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.model.MeditationItemBean;
import com.xiaopeng.xpmeditation.util.GlideUtil;
import com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter;
import com.xiaopeng.xpmeditation.view.adapter.HorizatalLinearItemDecoration;
import com.xiaopeng.xpmeditation.view.adapter.MeditationMusicAdapter;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;

/* loaded from: classes2.dex */
public class MeditationPlayView extends XRelativeLayout {
    private static final String ACTION_HVAC = "com.xiaopeng.carcontrol.intent.action.SHOW_HVAC_PANEL";
    private static final int ITEM_DECORATION_SIZE = 25;
    private static final String TAG = "MeditationPlayView";
    private MeditationMusicAdapter mAdapter;
    private XTextView mAirTv;
    protected LottieAnimationView mAnimView;
    protected XImageView mCenterIconImg;
    private View.OnClickListener mControllerListener;
    protected XTextView mControllerText;
    private View.OnClickListener mFinishListener;
    private BaseRecyclerViewAdapter.ItemClickListener mItemClickListener;
    private RecyclerView mRecyclerView;
    private SeekBar mSeekBar;
    private XTextView mTimeTxt;
    private XTextView mTitleTxt;
    private int mVisiblity;

    public MeditationPlayView(Context context) {
        super(context);
        this.mVisiblity = -1;
        init();
    }

    public MeditationPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mVisiblity = -1;
        init();
    }

    public MeditationPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mVisiblity = -1;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_meditation_play, this);
        initView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initView() {
        LogUtils.d(TAG, "initView: ");
        initInfo();
        initAnim();
        initRecycler();
        initController();
        initHvac();
        refreshStatus(-1);
    }

    private void initInfo() {
        this.mCenterIconImg = (XImageView) findViewById(R.id.img_center_icon);
        this.mTitleTxt = (XTextView) findViewById(R.id.txt_title);
        this.mTimeTxt = (XTextView) findViewById(R.id.txt_time);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initAnim() {
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_meditation);
        this.mAnimView = lottieAnimationView;
        lottieAnimationView.setRepeatCount(-1);
        this.mAnimView.setAnimation("anim_meditation.json");
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_music);
        this.mRecyclerView = recyclerView;
        recyclerView.addItemDecoration(getItemDecoration());
        this.mRecyclerView.setLayoutManager(getLayoutManager());
        MeditationMusicAdapter meditationMusicAdapter = new MeditationMusicAdapter();
        this.mAdapter = meditationMusicAdapter;
        this.mRecyclerView.setAdapter(meditationMusicAdapter);
        this.mAdapter.setItemClickListener(new BaseRecyclerViewAdapter.ItemClickListener() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationPlayView$qZVPA2LzBntf-rW0q1-qvCMkPCg
            @Override // com.xiaopeng.xpmeditation.view.adapter.BaseRecyclerViewAdapter.ItemClickListener
            public final void click(int i, int i2) {
                MeditationPlayView.this.lambda$initRecycler$0$MeditationPlayView(i, i2);
            }
        });
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_progress);
        this.mSeekBar = seekBar;
        seekBar.setPadding(0, 0, 0, 0);
        this.mSeekBar.setThumbOffset(0);
        this.mSeekBar.setClickable(false);
        this.mSeekBar.setEnabled(false);
        this.mSeekBar.setFocusable(false);
        this.mSeekBar.setVisibility(8);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaopeng.xpmeditation.view.MeditationPlayView.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                int computeHorizontalScrollExtent = MeditationPlayView.this.mRecyclerView.computeHorizontalScrollExtent();
                int computeHorizontalScrollRange = MeditationPlayView.this.mRecyclerView.computeHorizontalScrollRange();
                int computeHorizontalScrollOffset = MeditationPlayView.this.mRecyclerView.computeHorizontalScrollOffset();
                MeditationPlayView.this.mSeekBar.setMax(computeHorizontalScrollRange - computeHorizontalScrollExtent);
                MeditationPlayView.this.mSeekBar.setProgress(computeHorizontalScrollOffset);
                MeditationPlayView.this.refreshSeekBarStatus();
            }
        });
    }

    public /* synthetic */ void lambda$initRecycler$0$MeditationPlayView(int type, int position) {
        BaseRecyclerViewAdapter.ItemClickListener itemClickListener = this.mItemClickListener;
        if (itemClickListener != null) {
            itemClickListener.click(type, position);
        }
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        LogUtils.i(TAG, "getItemDecoration: ");
        return new HorizatalLinearItemDecoration(25);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), 0, false);
    }

    private void initController() {
        XTextView xTextView = (XTextView) findViewById(R.id.btn_control);
        this.mControllerText = xTextView;
        xTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.MeditationPlayView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (MeditationPlayView.this.mControllerListener != null) {
                    MeditationPlayView.this.mControllerListener.onClick(v);
                }
            }
        });
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.MeditationPlayView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MeditationPlayView.this.finish(v);
            }
        });
    }

    private void initHvac() {
        XTextView xTextView = (XTextView) findViewById(R.id.tv_air);
        this.mAirTv = xTextView;
        xTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xpmeditation.view.MeditationPlayView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.xiaopeng.carcontrol.intent.action.SHOW_HVAC_PANEL");
                intent.addFlags(1024);
                MeditationPlayView.this.getContext().startActivity(intent);
            }
        });
    }

    protected void finish(View v) {
        View.OnClickListener onClickListener = this.mFinishListener;
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    public void refreshMeditationList(List<MeditationItemBean> list) {
        this.mAdapter.setData(list);
        refreshSeekBarStatus();
    }

    protected void refreshSeekBarStatus() {
        this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationPlayView$iTF0ovvv9QSY_edzntthkERPKmQ
            @Override // java.lang.Runnable
            public final void run() {
                MeditationPlayView.this.lambda$refreshSeekBarStatus$1$MeditationPlayView();
            }
        });
    }

    public /* synthetic */ void lambda$refreshSeekBarStatus$1$MeditationPlayView() {
        if (this.mRecyclerView.computeHorizontalScrollExtent() >= this.mRecyclerView.computeHorizontalScrollRange()) {
            this.mSeekBar.setVisibility(8);
        } else {
            this.mSeekBar.setVisibility(0);
        }
    }

    public void refreshInfo(MeditationItemBean bean) {
        LogUtils.d(TAG, "bean = " + bean);
        if (bean.getData() == null) {
            return;
        }
        GlideUtil.loadWithOriginSize(getContext(), bean.getData().getPicUrl(), this.mCenterIconImg);
        this.mTitleTxt.setText(bean.getData().getTitle());
    }

    public void refreshStatus(int status) {
        refreshControllerBtnStatus(status);
        refreshAnimStatus(status);
    }

    protected void refreshControllerBtnStatus(int status) {
        if (status == 2) {
            this.mControllerText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mid_meditation_pause, 0, 0, 0);
            this.mControllerText.setText(R.string.meditation_pause);
            return;
        }
        this.mControllerText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mid_meditation_play, 0, 0, 0);
        this.mControllerText.setText(R.string.meditation_play);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refreshAnimStatus(int status) {
        LogUtils.i(TAG, "refreshAnimStatus: " + status);
        if (status == 2) {
            this.mAnimView.resumeAnimation();
        } else {
            this.mAnimView.pauseAnimation();
        }
    }

    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        int i = this.mVisiblity;
        if ((i == 4 || i != visibility) && visibility == 0 && !this.mAnimView.isAnimating()) {
            this.mAnimView.playAnimation();
            LogUtils.d(TAG, "visible play anim");
        }
        this.mVisiblity = visibility;
    }

    public void scrollToIndex(final int index) {
        this.mRecyclerView.post(new Runnable() { // from class: com.xiaopeng.xpmeditation.view.-$$Lambda$MeditationPlayView$MG_o8es4vPLD2bCrnjG19cUSC0o
            @Override // java.lang.Runnable
            public final void run() {
                MeditationPlayView.this.lambda$scrollToIndex$2$MeditationPlayView(index);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: realScrollToIndex */
    public void lambda$scrollToIndex$2$MeditationPlayView(int index) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (linearLayoutManager.getItemCount() <= 0) {
                return;
            }
            int round = Math.round((findLastVisibleItemPosition - findFirstVisibleItemPosition) / 2.0f);
            int i = 0;
            if (index < findFirstVisibleItemPosition) {
                int i2 = index - round;
                if (i2 <= 0) {
                    i2 = 0;
                }
                linearLayoutManager.scrollToPositionWithOffset(index, 0);
                i = i2;
            } else if (index > findLastVisibleItemPosition) {
                i = index + round;
                if (i > linearLayoutManager.getItemCount() - 1) {
                    i = linearLayoutManager.getItemCount() - 1;
                }
                linearLayoutManager.scrollToPositionWithOffset(index, -25);
            }
            LogUtils.i(TAG, "current=" + index + " top=" + findFirstVisibleItemPosition + " bottom=" + findLastVisibleItemPosition + " visibleNum=" + round + " pos = " + i);
        }
    }

    public void setOnItemClick(BaseRecyclerViewAdapter.ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void refreshTime(String time) {
        this.mTimeTxt.setText(time);
    }

    public void setOnFinishListener(View.OnClickListener listener) {
        this.mFinishListener = listener;
    }

    public void setOnControllerListener(View.OnClickListener listener) {
        this.mControllerListener = listener;
    }
}
