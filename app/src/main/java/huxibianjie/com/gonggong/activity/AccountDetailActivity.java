package huxibianjie.com.gonggong.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.home.runmining.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.MyApplication;
import huxibianjie.com.gonggong.adapter.MoneyDetailsAdapter;
import huxibianjie.com.gonggong.bean.GetMyCoinDetailBean;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.DensityUtil;

public class AccountDetailActivity extends AppCompatActivity {

    @BindView(R.id.back_up1)
    ImageView backUp1;
    @BindView(R.id.ll_top1)
    LinearLayout mLlTop1;
    @BindView(R.id.rec)
    RecyclerView recyclerView;
    private Gson gson = new Gson();
    private MoneyDetailsAdapter moneyDetailsAdapter;
    private String coinName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        ButterKnife.bind(this);
        coinName = getIntent().getStringExtra("coinName");
        if (TextUtils.isEmpty(coinName)) {
            coinName = "";
        }
        initview();
        initData();
    }

    private void initData() {
        Map map1 = new HashMap();
        map1.put("coinName", "");
        map1.put("page", "0");
        map1.put("coinName", coinName);
        OkGo.post(UrlConstan.GETMYCOINDETAIL).headers("uid", MyApplication.user.getUid() + "")
                .headers("token", MyApplication.user.getToken())
                .params(map1).execute(new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                GetMyCoinDetailBean getMyCoinDetailBean = gson.fromJson(response.body(), GetMyCoinDetailBean.class);
                if (getMyCoinDetailBean.getCode() == 0) {
                    List<GetMyCoinDetailBean.DatasBean> datas = getMyCoinDetailBean.getDatas();
                    moneyDetailsAdapter.bounData(datas);
                }
            }
        });
    }

    private void initview() {
        if (Build.VERSION.SDK_INT > 18) {
            AppUtils.initSystemBar(this);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mLlTop1.getLayoutParams();
            params.height = DensityUtil.Dp2Px(this, -23);
            mLlTop1.setLayoutParams(params);
        }
        moneyDetailsAdapter = new MoneyDetailsAdapter(AccountDetailActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AccountDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(moneyDetailsAdapter);
    }

    @OnClick({R.id.back_up1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_up1:
                finish();
                break;
        }
    }
}
