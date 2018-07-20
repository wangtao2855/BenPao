package huxibianjie.com.gonggong;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.home.runmining.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import huxibianjie.com.gonggong.adapter.MoneyAdapter;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.DensityUtil;

public class AccountFragment extends AutoLayoutActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    private RelativeLayout mLlTop;
    private LinearLayout mTopBarLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fragment);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MoneyAdapter(this));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new MoneyAdapter(this));
        initview();
    }

    private void initview() {
        mLlTop = (RelativeLayout) findViewById(R.id.ll_top);
        mTopBarLinear = (LinearLayout) findViewById(R.id.top_bar_linear);
        mTopBarLinear.setBackgroundColor(0);
        if (Build.VERSION.SDK_INT > 18) {
            AppUtils.initSystemBar(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLlTop.getLayoutParams();
            params.height = DensityUtil.Dp2Px(this, -10);
            mLlTop.setLayoutParams(params);
            mTopBarLinear.setPadding(0, AppUtils.getStatusBarHeight(this), 0, 0);
        }
    }

}
