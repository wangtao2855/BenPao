package huxibianjie.com.gonggong;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.home.runmining.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.adapter.Money1Adapter;
import huxibianjie.com.gonggong.adapter.MoneyAdapter;
import huxibianjie.com.gonggong.dialog.DialogLoginView;
import huxibianjie.com.gonggong.dialog.DialogUtils;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.DensityUtil;
import huxibianjie.com.gonggong.util.RealPathFromUriUtils;
import huxibianjie.com.gonggong.util.ToastUtils;

public class AccountFragment extends AutoLayoutActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    private RelativeLayout mLlTop;
    private LinearLayout mTopBarLinear;
    @BindView(R.id.imageView)
    ImageView iv_head;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private int CAMERA_REQUEST_CODE = 99;
    private int ALBUM_REQUEST_CODE = 100;
    private Context context;
    private DialogUtils dialogHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fragment);
        ButterKnife.bind(this);
        context = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MoneyAdapter(this));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new Money1Adapter(this));
        initview();
        initHeadDialog();
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
    private File tempFile;

    private void initHeadDialog() {
        dialogHead = new DialogUtils();
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_head, null);
        inflate.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHead.dismissDialog();
            }
        });
        inflate.findViewById(R.id.tv_one).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                ToastUtils.showSingleToast("点击了相机");
                tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
                //跳转到调用系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //判断版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
                    intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context, getPackageName() + ".fileprovider", tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                } else {    //否则使用Uri.fromFile(file)方法获取Uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                }
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                dialogHead.dismissDialog();
            }
        });
        inflate.findViewById(R.id.tv_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
                dialogHead.dismissDialog();
            }
        });
        dialogHead.displayDialog(context, inflate, Gravity.BOTTOM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALBUM_REQUEST_CODE) {
            if (data != null) {
                ToastUtils.showSingleToast("返回来了");
                String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                Glide.with(this).load(realPathFromUri).into(iv_head);
            }
        }
    }

    @OnClick({R.id.imageView, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                dialogHead.showDialog();
                break;
            case R.id.tv_login:
                DialogLoginView dialogLoginView1 = new DialogLoginView(context);
                dialogLoginView1.showDialog();
                break;
        }
    }

}
