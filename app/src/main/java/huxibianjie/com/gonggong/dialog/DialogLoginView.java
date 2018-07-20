package huxibianjie.com.gonggong.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import huxibianjie.com.gonggong.bean.GetAuthCodeForACLoginBean;
import huxibianjie.com.gonggong.bean.LoginBtnBean;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.util.APKVersionCodeUtils;
import huxibianjie.com.gonggong.util.Constant;
import huxibianjie.com.gonggong.util.CountDownTimerUtils;
import huxibianjie.com.gonggong.util.SpUtil;
import huxibianjie.com.gonggong.util.SystemUtil;
import huxibianjie.com.gonggong.util.ToastUtils;

/**
 * Created by wangtao on 2018/6/14.
 */

public class DialogLoginView extends View {

    private Context context;
    private DialogUtils dialogUtils;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_yanzhengma)
    EditText et_yanzhengma;
    @BindView(R.id.tv_yanzhengma)
    TextView tv_yanzhengma;
    @BindView(R.id.bt_comple)
    Button bt_comple;
    private CountDownTimerUtils countDownTimer;
    private Gson gson = new Gson();


    public DialogLoginView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        dialogUtils = new DialogUtils();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        ButterKnife.bind(this, inflate);
        dialogUtils.displayDialogFull(context, inflate, Gravity.CENTER);
        countDownTimer = CountDownTimerUtils.getCountDownTimer();
    }

    @OnClick({R.id.tv_yanzhengma, R.id.bt_comple})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yanzhengma:
                if (et_phone.getText().toString().length() != 11) {
                    ToastUtils.showSingleToast("请输入正确的手机号");
                    return;
                }
                Map map = new HashMap();
                map.put("phoneNumber", et_phone.getText().toString());
                map.put("isSendToMobile", "true");

                OkGo.post(UrlConstan.GETAUTHCODEFORACLOGIN).params(map).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            GetAuthCodeForACLoginBean getAuthCodeForACLoginBean = gson.fromJson(response.body(), GetAuthCodeForACLoginBean.class);
                            if (getAuthCodeForACLoginBean.getCode() == 0) {
                                tv_yanzhengma.setClickable(false);
                                countDownTimer.setMillisInFuture(60 * 1000);
                                countDownTimer.setCountDownInterval(1000);
                                countDownTimer.setFinishDelegate(new CountDownTimerUtils.FinishDelegate() {
                                    @Override
                                    public void onFinish() {
                                        tv_yanzhengma.setText("重新获取");
                                        tv_yanzhengma.setClickable(true);
                                    }
                                });
                                countDownTimer.setTickDelegate(new CountDownTimerUtils.TickDelegate() {
                                    @Override
                                    public void onTick(long pMillisUntilFinished) {
                                        tv_yanzhengma.setText((pMillisUntilFinished / 1000) + "");
                                    }
                                });
                                countDownTimer.start();
                            }
                            ToastUtils.showSingleToast(getAuthCodeForACLoginBean.getCodeMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.bt_comple:
                if (et_yanzhengma.getText().toString().length() == 0) {
                    ToastUtils.showSingleToast("请输入验证码之后在进行登入");
                    return;
                }
                Map map1 = new HashMap();
                map1.put("loginAccount", et_phone.getText().toString());
                map1.put("authCode", et_yanzhengma.getText().toString().trim());
                map1.put("os", "Android");//可选值【Android,iOS】
                map1.put("phoneModel", SystemUtil.getSystemModel() + "");
                map1.put("versionCode", APKVersionCodeUtils.getVersionCode(context) + "");
                map1.put("deviceToken", SystemUtil.getIMEI(context) + "");
                map1.put("osVersion", SystemUtil.getSystemVersion() + "");
                map1.put("isNeedVersionMsg", "false");

                OkGo.post(UrlConstan.LOGIN).params(map1).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            LoginBtnBean liginBtnBean = gson.fromJson(response.body(), LoginBtnBean.class);
                            if (liginBtnBean.getCode() == 0) {
                                SpUtil.getInstance().putObject(Constant.USER, liginBtnBean.getUserInfo());
                                MyApplication.user = liginBtnBean.getUserInfo();
                                SpUtil.getInstance().putObject(Constant.USER, MyApplication.user);
                                DisDialog();
                                //登入成功之后的回调
                                if (a != null) {
                                    a.c();
                                }
                            }
                            ToastUtils.showSingleToast(liginBtnBean.getCodeMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    private A a;

    public interface A {
        void c();
    }

    public void E(A a) {
        this.a = a;
    }

    public void showDialog() {
        if (dialogUtils != null) {
            dialogUtils.showDialog();
        }
    }

    public void DisDialog() {
        if (dialogUtils != null) {
            dialogUtils.dismissDialog();
        }
    }

}
