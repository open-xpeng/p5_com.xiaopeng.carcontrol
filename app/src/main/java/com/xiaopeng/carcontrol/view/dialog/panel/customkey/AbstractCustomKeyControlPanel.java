package com.xiaopeng.carcontrol.view.dialog.panel.customkey;

import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.helper.SoundHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.view.dialog.panel.AbstractControlPanel;
import com.xiaopeng.carcontrol.view.dialog.panel.customkey.CustomKeyAdapter;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class AbstractCustomKeyControlPanel extends AbstractControlPanel {
    protected CustomKeyAdapter mAdapter;
    private int mDividerHeight;
    private final CustomKeyAdapter.OnItemCheckedListener mListener = new CustomKeyAdapter.OnItemCheckedListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.customkey.-$$Lambda$kx4ddqQBOqxAMM-MYhVPela-JDc
        @Override // com.xiaopeng.carcontrol.view.dialog.panel.customkey.CustomKeyAdapter.OnItemCheckedListener
        public final void onItemChecked(int i) {
            AbstractCustomKeyControlPanel.this.setCustomKey(i);
        }
    };
    protected MeterViewModel mMeterVm;

    protected abstract int getBackgroundId();

    protected abstract int getTitleId();

    protected abstract List<String> getTitleList();

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean needInitVm() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void setCustomKey(int position);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitViewModel() {
        this.mMeterVm = (MeterViewModel) ViewModelManager.getInstance().getViewModelImpl(IMeterViewModel.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public int getLayoutId() {
        return R.layout.custom_key_panel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    public void onInitView() {
        int backgroundId = getBackgroundId();
        if (backgroundId != 0) {
            ((ImageView) findViewById(R.id.custom_key_bg)).setImageResource(backgroundId);
        }
        XImageButton xImageButton = (XImageButton) findViewById(R.id.close_btn);
        xImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.customkey.-$$Lambda$AbstractCustomKeyControlPanel$Um3KFlRrxOI9h5ZS2cwal1xMDiE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AbstractCustomKeyControlPanel.this.lambda$onInitView$0$AbstractCustomKeyControlPanel(view);
            }
        });
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(VuiConstants.PROPS_GENERALACT, VuiConstants.GENERALACT_CLOSE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        xImageButton.setVuiProps(jSONObject);
        int titleId = getTitleId();
        if (titleId != 0) {
            ((XTextView) findViewById(R.id.title)).setText(titleId);
            ((XTextView) findViewById(R.id.title)).setVuiFatherElementId(String.valueOf(R.id.title));
        }
        this.mDividerHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.custom_key_divider_height);
        XRecyclerView xRecyclerView = (XRecyclerView) findViewById(R.id.custom_key_list);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(App.getInstance()));
        xRecyclerView.setItemAnimator(null);
        xRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaopeng.carcontrol.view.dialog.panel.customkey.AbstractCustomKeyControlPanel.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, parent.getChildAdapterPosition(view) > 0 ? AbstractCustomKeyControlPanel.this.mDividerHeight : 0, 0, 0);
            }
        });
        CustomKeyAdapter customKeyAdapter = new CustomKeyAdapter(getTitleList(), this.mListener, R.id.title);
        this.mAdapter = customKeyAdapter;
        xRecyclerView.setAdapter(customKeyAdapter);
        VuiManager.instance().initRecyclerView(xRecyclerView, getSceneId());
    }

    public /* synthetic */ void lambda$onInitView$0$AbstractCustomKeyControlPanel(View v) {
        dismiss();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.AbstractPanel
    protected boolean onKey(int keyCode, KeyEvent event) {
        if (event.getAction() == 0) {
            return handleKeyEvent(keyCode);
        }
        return false;
    }

    private boolean handleKeyEvent(int keyCode) {
        LogUtils.d(this.TAG, "handleKeyEvent::keyCode = " + keyCode, false);
        if (keyCode == 3 || keyCode == 4) {
            dismiss();
        } else {
            if (keyCode != 1011) {
                if (keyCode != 1012) {
                    if (keyCode == 1015) {
                        SoundHelper.play(SoundHelper.PATH_WHEEL_TIP_1, false, true);
                        dismiss();
                    } else if (keyCode != 1083) {
                        if (keyCode != 1084) {
                            return false;
                        }
                    }
                }
                SoundHelper.play(SoundHelper.PATH_WHEEL_SCROLL_2, false, true);
                this.mAdapter.increase();
            }
            SoundHelper.play(SoundHelper.PATH_WHEEL_SCROLL_2, false, true);
            this.mAdapter.decrease();
        }
        return true;
    }
}
