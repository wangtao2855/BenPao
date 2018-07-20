package huxibianjie.com.gonggong.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.home.runmining.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.MyApplication;
import huxibianjie.com.gonggong.adapter.NearbyRankingsAdapter;
import huxibianjie.com.gonggong.bean.NearbyBean;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.Constant;
import huxibianjie.com.gonggong.util.DensityUtil;

public class NearbyRankingsActivity extends AppCompatActivity {

    @BindView(R.id.back_up1)
    ImageView mBackUp1;
    @BindView(R.id.top_header_text)
    TextView mTopHeaderText;
    @BindView(R.id.Nearby_rankings_recyclerView)
    RecyclerView mNearbyRankingsRecyclerView;
    @BindView(R.id.ll_top1)
    LinearLayout mLlTop1;

    private String lot;
    private String lat;
    private Intent intent;
    private Gson gson = new Gson();
    private NearbyRankingsAdapter nearby_rankings_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_rankings_);
        ButterKnife.bind(this);
        mTopHeaderText.setText("附近的排名");
        mNearbyRankingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nearby_rankings_adapter = new NearbyRankingsAdapter();
        mNearbyRankingsRecyclerView.setAdapter(nearby_rankings_adapter);
        intent = getIntent();
        lot = intent.getStringExtra("lot");
        lat = intent.getStringExtra("lat");
        getData();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT > 18) {
            AppUtils.initSystemBar(this);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mLlTop1.getLayoutParams();
            params.height = DensityUtil.Dp2Px(this, -23);
            mLlTop1.setLayoutParams(params);
        }
    }

    @OnClick({R.id.back_up1})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back_up1:
                finish();
                break;
        }
    }

    public void getData() {
        Map map1 = new HashMap();
        map1.put("coinName", Constant.MONEY);//币的名称
        map1.put("lot", lot + "");//经度
        map1.put("lat", lat + "");//纬度
        OkGo.post(UrlConstan.NEARBYRANKINGS).headers("uid", MyApplication.user.getUid() + "").headers("token", MyApplication.user.getToken() + "").params(map1).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    NearbyBean nearbyBean = gson.fromJson(response.body(), NearbyBean.class);
                    if (nearbyBean.getCode() == 0) {
                        nearby_rankings_adapter.setDatas(nearbyBean.getDatas());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
