package huxibianjie.com.gonggong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
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
import huxibianjie.com.gonggong.bean.Push_stepNumber;
import huxibianjie.com.gonggong.dialog.CustomDialog;
import huxibianjie.com.gonggong.dialog.DialogConfirmView;
import huxibianjie.com.gonggong.dialog.DialogLoginView;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.util.Constant;
import huxibianjie.com.gonggong.util.Md5Encrypt;
import huxibianjie.com.gonggong.util.ToastUtils;
import huxibianjie.com.gonggong.util.XiaoShuUtil;

public class CloseActivity extends AppCompatActivity {

    @BindView(R.id.btn_ac)
    TextView btn_ac;
    Intent intent;
    @BindView(R.id.get_today_many)
    TextView mGetTodayMany;
    @BindView(R.id.get_today_many1)
    TextView mGetTodayMany1;
    @BindView(R.id.get_today_many2)
    TextView mGetTodayMany2;
    @BindView(R.id.get_today_many4)
    TextView mGetTodayMany4;
    @BindView(R.id.xingdongli)
    ImageView xingdongli;
    private Gson gson = new Gson();
    private String lot;
    private String lat;
    private String time;
    private String isCheat;
    private String stepnumber;
    private String moeny;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        ButterKnife.bind(this);
        customDialog = CustomDialog.instance(CloseActivity.this);
        intent = getIntent();
        lot = intent.getStringExtra("lot");
        lat = intent.getStringExtra("lat");
        time = intent.getStringExtra("time");
        isCheat = intent.getStringExtra("Effective_value");
        stepnumber = intent.getStringExtra("stepnumber");
        moeny = intent.getStringExtra("moeny");
        mGetTodayMany.setText(stepnumber);
        mGetTodayMany1.setText(stepnumber);

        if (isCheat.equals("false")) {
            mGetTodayMany2.setText(moeny);
            mGetTodayMany4.setText(moeny);
        } else {
            String aDouble = XiaoShuUtil.getDouble(Double.parseDouble(moeny), (double) 10);
            mGetTodayMany2.setText(aDouble);
            mGetTodayMany4.setText(aDouble);
        }
    }

    @OnClick({R.id.xingdongli, R.id.btn_ac})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xingdongli:
                DialogConfirmView dialogConfirmView2 = new DialogConfirmView(CloseActivity.this);
                dialogConfirmView2.setDialogMsg(getString(R.string.msg1));
                dialogConfirmView2.showDialog();
                break;
            case R.id.btn_ac:
                if (MyApplication.user == null) {
                    DialogLoginView dialogLoginView = new DialogLoginView(CloseActivity.this);
                    dialogLoginView.showDialog();
                } else {
                    if (customDialog != null) {
                        customDialog.show();
                    }
                    Map map1 = new HashMap();
                    map1.put("coinName", Constant.MONEY);//币的名称
                    map1.put("lot", lot + "");//经度
                    map1.put("lat", lat + "");//纬度
                    map1.put("time", time + "");//耗时
                    map1.put("stepNumber", stepnumber + "");//步数
                    map1.put("isCheat", isCheat);//是否作弊
                    String timeAt = String.valueOf(System.currentTimeMillis() / 1000);
                    map1.put("timestamp", timeAt);
                    String singn = Md5Encrypt.createHashedQueryString(map1, "Xa46L2UL2XJ1OzT2A3fhKXYBhcCbhA43");
                    map1.remove("timestamp");
                    OkGo.post(UrlConstan.PUSHSTEPNUMBER).headers("uid", MyApplication.user.getUid() + "")
                            .headers("appid", "api461454570120163").headers("token", MyApplication.user.getToken())
                            .headers("api-sign", singn)
                            .headers("api-timestamp", timeAt)
                            .params(map1).execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                Push_stepNumber pushStepNumber = gson.fromJson(response.body(), Push_stepNumber.class);
                                if (pushStepNumber.getCode() == 0) {
                                    setResult(101);
                                    finish();
                                    ToastUtils.showSingleToast("提交成功，可点击右边按钮去账户查看记录。");
                                } else {
                                    ToastUtils.showSingleToast(pushStepNumber.getCodeMsg());
                                }
                            } catch (Exception e) {
                                ToastUtils.showSingleToast("提交失败,请重新提交！");
                                e.printStackTrace();
                            } finally {
                                if (customDialog != null) {
                                    customDialog.dismiss();
                                }
                            }
                        }
                    });
                }
                break;
        }
    }
}
