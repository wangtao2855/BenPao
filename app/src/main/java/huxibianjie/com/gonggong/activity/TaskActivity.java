package huxibianjie.com.gonggong.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.home.runmining.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import huxibianjie.com.gonggong.bean.GetBabner;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.DensityUtil;
import huxibianjie.com.gonggong.util.ToastUtils;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mLlTop;
    private LinearLayout mTopBarLinear;
    private ImageView mHuodongRightBack;
    private Gson gson = new Gson();
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    GetBabner getBabner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initView();

        final XBanner mXBanner = (XBanner) findViewById(R.id.xbanner);
        final List<String> imgesUrl = new ArrayList<>();
        Map map1 = new HashMap();
        map1.put("type", "all");
        OkGo.post(UrlConstan.GETABANER).params(map1).execute(new StringCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    getBabner = gson.fromJson(response.body(), GetBabner.class);
                    if (getBabner.getCode() == 0) {
                        List<GetBabner.DatasBean.ActivityActivityBean> activity_activity = getBabner.getDatas().getActivity_activity();
                        List<GetBabner.DatasBean.ActivityADBean> activity_ad = getBabner.getDatas().getActivity_AD();
                        for (int i = 0; i < activity_ad.size(); i++) {
                            imgesUrl.add(activity_ad.get(i).getImgs().toString());
                            mXBanner.setData(imgesUrl, null);//第二个参数为提示文字资源集合
                        }
                        switch (activity_activity.size()) {
                            case 0:
                                mImage1.setVisibility(View.GONE);
                                mImage2.setVisibility(View.GONE);
                                mImage3.setVisibility(View.GONE);
                                break;
                            case 1:
                                mImage1.setVisibility(View.VISIBLE);
                                mImage2.setVisibility(View.GONE);
                                mImage3.setVisibility(View.GONE);
                                Glide.with(TaskActivity.this).load(activity_activity.get(0).getImgs()).asBitmap().into((ImageView) mImage1);
                                break;
                            case 2:
                                mImage1.setVisibility(View.VISIBLE);
                                mImage2.setVisibility(View.VISIBLE);
                                mImage3.setVisibility(View.GONE);
                                Glide.with(TaskActivity.this).load(activity_activity.get(0).getImgs()).asBitmap().into((ImageView) mImage1);
                                Glide.with(TaskActivity.this).load(activity_activity.get(1).getImgs()).asBitmap().into((ImageView) mImage2);
                                break;
                            case 3:
                                mImage1.setVisibility(View.VISIBLE);
                                mImage2.setVisibility(View.VISIBLE);
                                mImage3.setVisibility(View.VISIBLE);
                                Glide.with(TaskActivity.this).load(activity_activity.get(0).getImgs()).asBitmap().into((ImageView) mImage1);
                                Glide.with(TaskActivity.this).load(activity_activity.get(1).getImgs()).asBitmap().into((ImageView) mImage2);
                                Glide.with(TaskActivity.this).load(activity_activity.get(2).getImgs()).asBitmap().into((ImageView) mImage3);
                                break;
                        }
                    } else {
                        ToastUtils.showSingleToast(getBabner.getCodeMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(TaskActivity.this).load(model).asBitmap().into((ImageView) view);
            }
        });
        mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("Url", getBabner.getDatas().getActivity_AD().get(position).getContent().toString());
                startActivity(intent);
            }
        });
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

    private void initView() {
        mHuodongRightBack = (ImageView) findViewById(R.id.huodong_right_back);
        mHuodongRightBack.setOnClickListener(this);
        mImage1 = (ImageView) findViewById(R.id.image1);
        mImage2 = (ImageView) findViewById(R.id.image2);
        mImage3 = (ImageView) findViewById(R.id.image3);
        mImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("Url", getBabner.getDatas().getActivity_activity().get(0).getContent().toString());
                startActivity(intent);
            }
        });
        mImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("Url", getBabner.getDatas().getActivity_activity().get(1).getContent().toString());
                startActivity(intent);
            }
        });
        mImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("Url", getBabner.getDatas().getActivity_activity().get(2).getContent().toString());
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.huodong_right_back:
                finish();
                break;
        }
    }
}
