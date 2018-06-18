package huxibianjie.com.gonggong;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.home.runmining.R;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.DensityUtil;

public class TaskActivity extends AutoLayoutActivity {

    private RelativeLayout mLlTop;
    private LinearLayout mTopBarLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        XBanner mXBanner = (XBanner) findViewById(R.id.xbanner);

        final List<String> imgesUrl = new ArrayList<>();
        imgesUrl.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3110094567,1509314476&fm=27&gp=0.jpg");
        imgesUrl.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3110094567,1509314476&fm=27&gp=0.jpg");
        imgesUrl.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3110094567,1509314476&fm=27&gp=0.jpg");
        imgesUrl.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3110094567,1509314476&fm=27&gp=0.jpg");
        mXBanner.setData(imgesUrl,null);//第二个参数为提示文字资源集合
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                Glide.with(TaskActivity.this).load(model).asBitmap().into((ImageView) view);
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
}
