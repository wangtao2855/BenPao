package huxibianjie.com.gonggong.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.home.runmining.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.MyApplication;
import huxibianjie.com.gonggong.adapter.MoneyListAdapter;
import huxibianjie.com.gonggong.adapter.OnGroupExpandedListener;
import huxibianjie.com.gonggong.bean.GetMyCoinBean;
import huxibianjie.com.gonggong.bean.GetMyCoinDetailBean;
import huxibianjie.com.gonggong.bean.GetMyInviteBean;
import huxibianjie.com.gonggong.dialog.DialogConfirmView;
import huxibianjie.com.gonggong.dialog.DialogLoginView;
import huxibianjie.com.gonggong.dialog.DialogUtils;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.CircleImageView;
import huxibianjie.com.gonggong.util.Constant;
import huxibianjie.com.gonggong.util.DensityUtil;
import huxibianjie.com.gonggong.util.RealPathFromUriUtils;
import huxibianjie.com.gonggong.util.SingLeLineZoomTextView;
import huxibianjie.com.gonggong.util.SpUtil;
import huxibianjie.com.gonggong.util.XiaoShuUtil;

public class AccountActivity extends AutoLayoutActivity {

    @BindView(R.id.elListView)
    ExpandableListView recyclerView;
    private RelativeLayout mLlTop;
    private LinearLayout mTopBarLinear;
    @BindView(R.id.imageView)
    CircleImageView iv_head;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.qian)
    SingLeLineZoomTextView qian;
    @BindView(R.id.iv_imageView)
    ImageView imageView;
    @BindView(R.id.shouru)
    ImageView shouru;
    @BindView(R.id.jiazhi)
    ImageView jiazhi;
    @BindView(R.id.qianRig)
    TextView qianRig;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_yaoqing)
    TextView tv_yaoqing;
    @BindView(R.id.tv_fenxiang)
    TextView tv_fenxiang;
    @BindView(R.id.tv_guanzhu)
    TextView tv_guanzhu;

    private boolean isTop = false;


    private int CAMERA_REQUEST_CODE = 99;
    private int ALBUM_REQUEST_CODE = 100;
    private Context context;
    private DialogUtils dialogHead;
    private Gson gson = new Gson();
    private List<GetMyCoinBean.DatasBean> listAll = null;
    private List<GetMyCoinBean.DatasBean> datas;
    private MoneyListAdapter adapter;
    private List<GetMyCoinDetailBean.DatasBean> detailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_fragment);
        ButterKnife.bind(this);
        context = this;
        String head = SpUtil.getInstance().getString(Constant.REALPATHFROMURI, "");
        if (!TextUtils.isEmpty(head)) {
            Glide.with(this).load(head).error(R.mipmap.none).into(iv_head);
        }
        initview();
        initListView();
        initData();
        initHeadDialog();
    }

    private void initListView() {
        adapter = new MoneyListAdapter();
        recyclerView.setAdapter(adapter);
        XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
        // 清除默认的 Indicator
        recyclerView.setGroupIndicator(null);
        adapter.setOnGroupExpandedListener(new OnGroupExpandedListener() {
            @Override
            public void onGroupExpanded(int groupPosition) {
                expandOnlyOne(groupPosition);
            }
        });


        //  设置分组项的点击监听事件
        recyclerView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                adapter.setIndicatorState(groupPosition, groupExpanded);
                if (datas != null) {
                    initDataDetails(datas.get(groupPosition).getCoinName());
                }
                XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
                return false;
            }
        });

        recyclerView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
            }
        });
        recyclerView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
            }

        });
    }

    private boolean expandOnlyOne(int expandedPosition) {
        boolean result = true;
        int groupLength = recyclerView.getExpandableListAdapter().getGroupCount();
        for (int i = 0; i < groupLength; i++) {
            if (i != expandedPosition && recyclerView.isGroupExpanded(i)) {
                result &= recyclerView.collapseGroup(i);
            }
        }
        return result;
    }

    @SuppressLint("WrongConstant")
    private void initData() {
        if (MyApplication.user != null) {
            tv_login.setClickable(false);
            tv_login.setText("已登入");
            Map map2 = new HashMap();
            OkGo.post(UrlConstan.GETMYCOIN).headers("uid", MyApplication.user.getUid() + "").headers("token", MyApplication.user.getToken()).params(map2).execute(new StringCallback() {
                @SuppressLint("WrongConstant")
                @Override
                public void onSuccess(Response<String> response) {
                    GetMyCoinBean getMyCoinBean = gson.fromJson(response.body(), GetMyCoinBean.class);
                    if (getMyCoinBean.getCode() == 0) {
                        datas = getMyCoinBean.getDatas();
                        if (datas != null && datas.size() > 0) {
                            if (datas.size() > 0) {
                                tv_title.setVisibility(View.VISIBLE);
                                listAll = new ArrayList<>();
                                if (datas.size() > 3) {
                                    for (int i = 0; i < 3; i++) {
                                        listAll.add(datas.get(i));
                                    }
                                    imageView.setVisibility(View.VISIBLE);
                                    adapter.bounGroupData(listAll);
                                    XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
                                } else {
                                    imageView.setVisibility(View.GONE);
                                    adapter.bounGroupData(datas);
                                    XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
                                }
                            }
                            for (int i = 0; i < datas.size(); i++) {
                                if (datas.get(i).getCoinName().equals("DVE")) {
                                    qian.setText(datas.get(i).getCoin());
                                    qianRig.setText(datas.get(i).getCoin());
                                }
                            }
                        }
                    }
                }
            });
            Map map3 = new HashMap();
            OkGo.post(UrlConstan.GETMYINVITE).headers("uid", MyApplication.user.getUid() + "").headers("token", MyApplication.user.getToken()).params(map3).execute(new StringCallback() {

                @SuppressLint("WrongConstant")
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        GetMyInviteBean getMyInviteBean = gson.fromJson(response.body(), GetMyInviteBean.class);
                        if (getMyInviteBean.getCode() == 0) {
                            GetMyInviteBean.DatasBean getMyInviteBeanDatas = getMyInviteBean.getDatas();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            tv_login.setClickable(true);
            tv_login.setText("未登入");
            qian.setText("0");
            qianRig.setText("0");
            tv_title.setVisibility(View.GONE);
        }
    }

    private void initview() {
        Glide.with(this).load(SpUtil.getInstance().getString(Constant.REALPATHFROMURI)).error(R.mipmap.none).into(iv_head);
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
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        moneyAdapter = new MoneyAdapter(this);
//        recyclerView.setAdapter(moneyAdapter);
    }

    private void initDataDetails(String coinName) {
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
                    detailData = getMyCoinDetailBean.getDatas();
                    adapter.bounChildData(detailData);
                    XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
                }
            }
        });
    }

    private File tempFile;

    private void initHeadDialog() {
        dialogHead = new DialogUtils();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_head, null);
        inflate.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHead.dismissDialog();
            }
        });
        inflate.findViewById(R.id.tv_one).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
                String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                SpUtil.getInstance().putString(Constant.REALPATHFROMURI, realPathFromUri);
                Glide.with(this).load(realPathFromUri).error(R.mipmap.none).into(iv_head);
            }
        }
    }


    @OnClick({R.id.imageView, R.id.tv_login, R.id.iv_back, R.id.iv_imageView, R.id.jiazhi, R.id.shouru, R.id.tv_yaoqing, R.id.tv_fenxiang, R.id.tv_guanzhu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                dialogHead.showDialog();
                break;
            case R.id.tv_login:
                DialogLoginView dialogLoginView1 = new DialogLoginView(context);
                dialogLoginView1.E(new DialogLoginView.A() {
                    @Override
                    public void c() {
                        initData();
                        tv_login.setText("已登入！");
                    }
                });
                dialogLoginView1.showDialog();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_imageView:
                if (!isTop) {
                    if (datas != null && datas.size() > 0) {
                        isTop = true;
                        adapter.bounGroupData(datas);
                        XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
                    }
                } else {
                    if (listAll != null && listAll.size() > 0) {
                        isTop = false;
                        adapter.bounGroupData(listAll);
                        XiaoShuUtil.setListViewHeightBasedOnChildren(recyclerView);
                    }
                }
                break;
            case R.id.jiazhi:
                DialogConfirmView dialogConfirmView = new DialogConfirmView(AccountActivity.this);
                dialogConfirmView.setDialogMsg(getString(R.string.msg2));
                dialogConfirmView.showDialog();
                break;
            case R.id.shouru:
                DialogConfirmView dialogConfirmView1 = new DialogConfirmView(AccountActivity.this);
                dialogConfirmView1.setDialogMsg(getString(R.string.msg4));
                dialogConfirmView1.showDialog();
                break;
            case R.id.tv_yaoqing:
                if (MyApplication.user != null) {
                    Intent intent = new Intent();
                    intent.putExtra("Url", UrlConstan.MYINVITE + MyApplication.user.getUid());
                    intent.setClass(getApplicationContext(), DetailsActivity.class);
                    startActivity(intent);
                } else {
                    DialogLoginView dialogLoginView = new DialogLoginView(AccountActivity.this);
                    dialogLoginView.showDialog();
                }
                break;
            case R.id.tv_fenxiang:
                Intent intent = new Intent();
                intent.putExtra("source", "imageShare");
                intent.setClass(getApplicationContext(), DetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_guanzhu:
                Intent intent2 = new Intent();
                intent2.putExtra("source", "imageGuanZhu");
                intent2.setClass(getApplicationContext(), DetailsActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
