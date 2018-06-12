package huxibianjie.com.gonggong;

import android.animation.AnimatorSet;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.track.DistanceRequest;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.TransportMode;
import com.home.runmining.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.Constant;
import huxibianjie.com.gonggong.util.DensityUtil;
import huxibianjie.com.gonggong.view.HintDialog;

/**
 * Created by adu on 2016/10/21.
 */

public class WalkingActivity extends AutoLayoutActivity implements Handler.Callback {

    //@BindView(R.id.rl_Right) RelativeLayout rlRight;
    @BindView(R.id.ll_top)
    RelativeLayout llTop;
    @BindView(R.id.top_bar_linear)
    LinearLayout topBarLinear;

    @BindView(R.id.step_count)
    TextView stepCount;      //计算步数
    @BindView(R.id.calories)
    TextView calories;         //热量千卡

    public static final String WALKCAMPID = "walkCampId";
    @BindView(R.id.Button_left)
    ImageView mButtonLeft;
    @BindView(R.id.Button_right)
    ImageView mButtonRight;
    @BindView(R.id.xiangqing3)
    ImageView mXiangqing3;
    @BindView(R.id.bushu_text)
    TextView mBushuText;
    @BindView(R.id.renwujiangli)
    LinearLayout mRenwujiangli;
    @BindView(R.id.xiangqing1)
    ImageView mXiangqing1;
    @BindView(R.id.Today_huode)
    TextView mTodayHuode;
    @BindView(R.id.get_today_many)
    TextView mGetTodayMany;
    @BindView(R.id.lbsc_text)
    TextView mLbscText;
    @BindView(R.id.xiangqing2)
    ImageView mXiangqing2;
    @BindView(R.id.Today_shouru)
    TextView mTodayShouru;
    @BindView(R.id.yang_text)
    TextView mYangText;
    @BindView(R.id.linearlayou_cen)
    LinearLayout mLinearlayouCen;
    @BindView(R.id.duihuan)
    LinearLayout mDuihuan;
    @BindView(R.id.fujin)
    ImageView mFujin;
    @BindView(R.id.miaobiao_image)
    ImageView mMiaobiaoImage;
    @BindView(R.id.time_textview)
    TextView mTimeTextview;
    @BindView(R.id.time_textview2)
    TextView mTimeTextview2;
    @BindView(R.id.Calculation_Button)
    Button mCalculationButton;
    @BindView(R.id.mapView)
    MapView mapView;

    private long step = 0;
    private long lastStep = 0;
    private boolean isPause = false;
    private boolean isStart = false;
    private long timemm = 0;
    private long thisTime = 0;
    private SharedPreferences sp;
    //循环获取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL = 500;
    public static final int TIMECOM = 30;

    private Messenger messenger;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Handler delayHandler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isPause && isStart) {
                thisTime = thisTime + TIME_INTERVAL;
                timemm = thisTime / 60000;
                delayHandler.sendEmptyMessageDelayed(TIMECOM, TIME_INTERVAL);
            }

        }
    };
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.Config.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private LBSTraceClient mTraceClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        ButterKnife.bind(this);
        // 初始化轨迹服务客户端
        mTraceClient = new LBSTraceClient(getApplicationContext());
        initView();
//        listener();
//        initLiCheng();
//        initMapView();
    }

    private void initLiCheng() {
        // 轨迹服务ID
        long serviceId = 201258;
        // 设备标识
        String entityName = "myTrace";
        // 是否需要对象存储服务，默认为：false，关闭对象存储服务。注：鹰眼 Android SDK v3.0以上版本支持随轨迹上传图像等对象数据，若需使用此功能，该参数需设为 true，且需导入bos-android-sdk-1.0.2.jar。
        boolean isNeedObjectStorage = false;
        // 初始化轨迹服务
        Trace mTrace = new Trace(serviceId, entityName, isNeedObjectStorage);
        // 定位周期(单位:秒)
        int gatherInterval = 5;
        // 打包回传周期(单位:秒)
        int packInterval = 10;
        // 设置定位和打包周期
        mTraceClient.setInterval(gatherInterval, packInterval);
        // 开启服务
        mTraceClient.startTrace(mTrace, mTraceListener);
        // 开启采集
        mTraceClient.startGather(mTraceListener);
    }

    private void initMapView() {
        // 请求标识
        int tag = 2;
        // 轨迹服务ID
        long serviceId = 0;
        // 设备标识
        String entityName = "myTrace";
        // 初始化轨迹服务
        DistanceRequest distanceRequest = new DistanceRequest(tag, serviceId, entityName);
        // 开始时间(单位：秒)
        long startTime = System.currentTimeMillis() / 1000 - 12 * 60 * 60;
        // 结束时间(单位：秒)
        long endTime = System.currentTimeMillis() / 1000;
        // 设置开始时间
        distanceRequest.setStartTime(startTime);
        // 设置结束时间
        distanceRequest.setEndTime(endTime);
        // 设置需要纠偏
        distanceRequest.setProcessed(true);
        // 创建纠偏选项实例
        ProcessOption processOption = new ProcessOption();
        // 设置需要去噪
        processOption.setNeedDenoise(true);
        // 设置需要绑路
        processOption.setNeedMapMatch(true);
        // 设置交通方式为驾车
        processOption.setTransportMode(TransportMode.walking);
        // 设置纠偏选项
        distanceRequest.setProcessOption(processOption);
        // 设置里程填充方式为驾车
        distanceRequest.setSupplementMode(SupplementMode.driving);
        // 初始化轨迹监听器
        OnTrackListener mTrackListener = new OnTrackListener() {
            // 里程回调
            @Override
            public void onDistanceCallback(DistanceResponse response) {
                System.out.println("repnse---" + response.getDistance() + "米");
            }
        };
        // 查询里程
        mTraceClient.queryDistance(distanceRequest, mTrackListener);

    }

    private OnTraceListener mTraceListener;

    private void listener() {
        // 初始化轨迹服务监听器
        mTraceListener = new OnTraceListener() {
            @Override
            public void onBindServiceCallback(int i, String s) {
                System.out.println("mTraceListener1---" + i + "--" + s);
            }

            // 开启服务回调
            @Override
            public void onStartTraceCallback(int status, String message) {
                System.out.println("mTraceListener2---" + status + "--" + message);

            }

            // 停止服务回调
            @Override
            public void onStopTraceCallback(int status, String message) {
                System.out.println("mTraceListener3---" + status + "--" + status);
            }

            // 开启采集回调
            @Override
            public void onStartGatherCallback(int status, String message) {
                System.out.println("mTraceListener4---" + status + "--" + message);
            }

            // 停止采集回调
            @Override
            public void onStopGatherCallback(int status, String message) {
                System.out.println("mTraceListener5---" + status + "--" + message);
            }

            // 推送回调
            @Override
            public void onPushCallback(byte messageNo, PushMessage message) {
                System.out.println("mTraceListener6---" + message);
            }

            @Override
            public void onInitBOSCallback(int i, String s) {
                System.out.println("mTraceListener7---" + i + "--" + s);
            }
        };
    }


    private void initView() {
        sp = getSharedPreferences(Constant.Config.FILE_NAME, MODE_PRIVATE);
        lastStep = sp.getInt(Constant.Config.stepNum, 0);
        delayHandler = new Handler(this);
        if (sp.getBoolean(Constant.Config.isStepServiceRunning, true)) {
            setupService();
        }
        topBarLinear.setBackgroundColor(0);
        if (Build.VERSION.SDK_INT > 18) {
            AppUtils.initSystemBar(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llTop.getLayoutParams();
            params.height = DensityUtil.Dp2Px(this, 35);
            llTop.setLayoutParams(params);
            topBarLinear.setPadding(0, AppUtils.getStatusBarHeight(this), 0, 0);
        }

        stepCount.setText(String.valueOf(step));
        calories.setText(String.valueOf(stepToKcal(step)));
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {

            case Constant.Config.MSG_FROM_SERVER:
                step = Long.valueOf(String.valueOf(msg.getData().get(Constant.Config.stepNum))) - lastStep;
                stepCount.setText(String.valueOf(step));
                calories.setText(String.valueOf(stepToKcal(step)));
                delayHandler.sendEmptyMessageDelayed(Constant.Config.REQUEST_SERVER, TIME_INTERVAL);
                break;
            case Constant.Config.REQUEST_SERVER:
                try {
                    Message msg1 = Message.obtain(null, Constant.Config.MSG_FROM_CLIENT);
                    msg1.replyTo = mGetReplyMessenger;
                    messenger.send(msg1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case TIMECOM:
                runnable.run();
                break;
        }
        return false;
    }

    private void startAnimation() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        AnimatorSet animatorSetStart = new AnimatorSet();
        animatorSetStart.setInterpolator(new DecelerateInterpolator());
        animatorSetStart.setDuration(1000);
        animatorSetStart.start();

    }

    private void setupService() {
//        Intent intent = new Intent(this, StepService.class);
//        bindService(intent, conn, Context.BIND_AUTO_CREATE);
//        startService(intent);
    }

    @OnClick({R.id.xiangqing3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiangqing3:
                startActivity(new Intent(getApplicationContext(), AccountFragment.class));
                break;
        }
    }

    //步数与热量的交
    private float stepToKcal(float step) {
        float i = 388.5f;
        return Math.round(100 * step * i / 10000.0f) / 100.0f;
    }

    @Override
    public void onBackPressed() {
        if (!isStart) {
            WalkingActivity.this.finish();
            super.onBackPressed();
        } else {
            new HintDialog.Builder(WalkingActivity.this).
                    setTitle("提示").
                    setMessage("确定退出跑步吗？您已跑步数将不会进行兑换！").
                    setConfirmBtnListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if (sp.getBoolean(Constant.Config.isStepServiceRunning, false)) {
                            sendBroadcast(new Intent(Constant.Config.stopStepService));
                            if (conn != null) {
                                unbindService(conn);
                            }
                        }
                    }).onCreate().show();
        }

    }
}
