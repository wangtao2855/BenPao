package huxibianjie.com.gonggong.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.DistanceRequest;
import com.baidu.trace.api.track.DistanceResponse;
import com.baidu.trace.api.track.HistoryTrackRequest;
import com.baidu.trace.api.track.HistoryTrackResponse;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.api.track.TrackPoint;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.ProcessOption;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.SortType;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.baidu.trace.model.TransportMode;
import com.google.gson.Gson;
import com.home.runmining.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import huxibianjie.com.gonggong.MyApplication;
import huxibianjie.com.gonggong.bean.CheckVersionBean;
import huxibianjie.com.gonggong.bean.UserInfoBean;
import huxibianjie.com.gonggong.bean.Yuebean;
import huxibianjie.com.gonggong.dialog.DialogConfirmView;
import huxibianjie.com.gonggong.dialog.DialogLoginView;
import huxibianjie.com.gonggong.dialog.DownLoadView;
import huxibianjie.com.gonggong.net.UrlConstan;
import huxibianjie.com.gonggong.pedomemter.StepService;
import huxibianjie.com.gonggong.pedomemter.UpdateUiCallBack;
import huxibianjie.com.gonggong.util.APKVersionCodeUtils;
import huxibianjie.com.gonggong.util.AppUtils;
import huxibianjie.com.gonggong.util.BitmapUtil;
import huxibianjie.com.gonggong.util.CommonUtil;
import huxibianjie.com.gonggong.util.Constant;
import huxibianjie.com.gonggong.util.CurrentLocation;
import huxibianjie.com.gonggong.util.DensityUtil;
import huxibianjie.com.gonggong.util.MapUtil;
import huxibianjie.com.gonggong.util.SingLeLineZoomTextView;
import huxibianjie.com.gonggong.util.SpUtil;
import huxibianjie.com.gonggong.util.TrackReceiver;
import huxibianjie.com.gonggong.util.XiaoShuUtil;
import huxibianjie.com.gonggong.view.StepArcView;

/**
 * Created by adu on 2016/10/21.
 */

@SuppressLint("WrongConstant")
public class WalkingActivity extends AutoLayoutActivity implements SensorEventListener {

    @BindView(R.id.ll_top)
    RelativeLayout llTop;
    @BindView(R.id.top_bar_linear)
    LinearLayout topBarLinear;

    @BindView(R.id.step_count)
    StepArcView stepCount;      //计算步数
    @BindView(R.id.calories)
    SingLeLineZoomTextView calories;         //热量千卡

    public static final String WALKCAMPID = "walkCampId";
    @BindView(R.id.Button_left)
    ImageView mButtonLeft;
    @BindView(R.id.Button_right)
    ImageView mButtonRight;
    @BindView(R.id.xiangqing3)
    ImageView mXiangqing3;
    @BindView(R.id.renwujiangli)
    LinearLayout mRenwujiangli;
    @BindView(R.id.xiangqing1)
    ImageView mXiangqing1;
    @BindView(R.id.Today_huode)
    TextView mTodayHuode;
    @BindView(R.id.get_today_many)
    SingLeLineZoomTextView mGetTodayMany;
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
    @BindView(R.id.time_textview)
    Chronometer timer;
    @BindView(R.id.time_textview2)
    TextView mTimeTextview2;
    @BindView(R.id.Calculation_Button)
    Button mCalculationButton;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.line_tab)
    TabLayout tabLayout;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_money)
    TextView tv_money1;


    private final int reqerstcode = 100;
    private LBSTraceClient mTraceClient;
    private SDKReceiver mReceiver;
    private MyApplication trackApp;
    private Double lastX = 0.0;
    private boolean isLogin;
    private Gson gson = new Gson();
    private int SumBuShu = 10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        ButterKnife.bind(this);
        UserInfoBean userInfoBean = SpUtil.getInstance().getObject(Constant.USER, null);
        if (userInfoBean != null) {
            MyApplication.user = userInfoBean;
        }
        initData();
        // 初始化轨迹服务客户端
        mTraceClient = new LBSTraceClient(getApplicationContext());
        initView();
        // AK的授权需要一定的时间，在授权成功之前地图相关操作会出现异常；AK授权成功后会发送广播通知，我们这里注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        BitmapUtil.init();
        initMapUtil();
    }


    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount1) {
                    if (stepCount1 < 1) {
                        stepCount1 = 0;
                    }
                    SpUtil.getInstance().putLong(Constant.STEPNUMBER, stepCount1);
                    stepCount.setCurrentCount(SumBuShu, stepCount1);
                    mGetTodayMany.setText(String.valueOf(stepToKcal(stepCount1)));
                    calories.setText(String.valueOf(stepToKca()));
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initUpdate() {
        String versionName = APKVersionCodeUtils.getVerName(WalkingActivity.this);
        Map map = new HashMap();
        map.put("os", "android");
        map.put("versionCode", versionName);
        OkGo.post(UrlConstan.DOWN_LOAD).params(map).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    CheckVersionBean checkVersionBean = gson.fromJson(response.body(), CheckVersionBean.class);
                    if (checkVersionBean.getCode() == 0) {
                        CheckVersionBean.DatasBean datas = checkVersionBean.getDatas();
                        if (datas.getState().equals("1") || datas.getState().equals("2")) {
                            DownLoadView downLoadView = new DownLoadView(WalkingActivity.this, datas.getVersionMsg(), datas.getUrl());
                            downLoadView.showDialog();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 适配android M，检查权限
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        }
        if (trackApp.trackConf.contains("is_trace_started")
                && trackApp.trackConf.contains("is_gather_started")
                && trackApp.trackConf.getBoolean("is_trace_started", false)
                && trackApp.trackConf.getBoolean("is_gather_started", false)) {
            startRealTimeLoc(packInterval);
        } else {
            startRealTimeLoc(Constant.LOC_INTERVAL);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SpUtil.getInstance().getLong(Constant.STEPNUMBER, 0) < 1) {
            LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                View tabView = tabStrip.getChildAt(i);
                if (tabView != null) {
                    tabView.setClickable(true);
                }
            }
        } else {
            LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                View tabView = tabStrip.getChildAt(i);
                if (tabView != null) {
                    tabView.setClickable(false);
                }
            }
        }
        mapUtil.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);

        // 在Android 6.0及以上系统，若定制手机使用到doze模式，请求将应用添加到白名单。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = trackApp.getPackageName();
            boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName);
            if (!isIgnoring) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRealTimeLoc();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRealTimeLoc();
        trackPoints.clear();
        trackPoints = null;
        mapUtil.clear();
        if (trackApp.trackConf.contains("is_trace_started")
                && trackApp.trackConf.getBoolean("is_trace_started", true)) {
            // 退出app停止轨迹服务时，不再接收回调，将OnTraceListener置空
            trackApp.mClient.setOnTraceListener(null);
            trackApp.mClient.stopTrace(trackApp.mTrace, null);
            trackApp.mClient.clear();
        } else {
            trackApp.mClient.clear();
        }
        SharedPreferences.Editor editor = trackApp.trackConf.edit();
        editor.remove("is_trace_started");
        editor.remove("is_gather_started");
        editor.apply();
        BitmapUtil.clear();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //每次方向改变，重新给地图设置定位数据，用上一次的经纬度
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {// 方向改变大于1度才设置，以免地图上的箭头转动过于频繁
            mCurrentDirection = (int) x;
            if (!CommonUtil.isZeroPoint(CurrentLocation.latitude, CurrentLocation.longitude)) {
                mapUtil.updateMapLocation(new LatLng(CurrentLocation.latitude, CurrentLocation.longitude), (float) mCurrentDirection);
            }
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();

            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Toast.makeText(WalkingActivity.this, "AK验证失败，地图功能无法正常使用", Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                Toast.makeText(WalkingActivity.this, "AK验证成功", Toast.LENGTH_SHORT).show();
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(WalkingActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Yuebean.DatasBean> datas1;

    private void initData() {
        Map map = new HashMap();
        map.put("coinName", "");
        OkGo.post(UrlConstan.GETCOINSTOCK).params(map).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    Yuebean yuebean = gson.fromJson(response.body(), Yuebean.class);
                    if (yuebean.getCode() == 0) {
                        List<Yuebean.DatasBean> datas = yuebean.getDatas();
                        if (datas != null && datas.size() > 0) {
                            datas1 = datas;
                            for (int i = 0; i < yuebean.getDatas().size(); i++) {
                                Constant.MONEY = yuebean.getDatas().get(0).getCoinName();
                                tabLayout.addTab(tabLayout.newTab().setText(datas.get(i).getCoinName()));
                            }
                            long aLong = SpUtil.getInstance().getLong(Constant.STEPNUMBER, 0);
                            if (aLong > 0) {
                                stepCount.setCurrentCount(SumBuShu, (int) aLong);
                                mGetTodayMany.setText(stepToKcal(aLong));
                                calories.setText(String.valueOf(stepToKca()));
                            } else {
                                stepCount.setCurrentCount(SumBuShu, 0);
                                mGetTodayMany.setText(stepToKcal(0));
                                calories.setText(String.valueOf(stepToKca()));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;
    private int mCurrentDirection = 0;
    private PowerManager powerManager;
    private SensorManager mSensorManager;
    private static Boolean Effective_value;
    private static int mileage_value;

    private void initMapUtil() {
        initListener();
        trackApp = (MyApplication) getApplication();
        mapUtil = MapUtil.getInstance();
        mapUtil.init(mapView);
        mapUtil.setCenter(mCurrentDirection);//设置地图中心点
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        trackPoints = new ArrayList<>();
    }

    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    /**
     * 轨迹监听器(用于接收纠偏后实时位置回调)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity监听器(用于接收实时定位回调)
     */
    private OnEntityListener entityListener = null;

    /**
     * 实时定位任务
     */
    private RealTimeHandler realTimeHandler = new RealTimeHandler();

    private RealTimeLocRunnable realTimeLocRunnable = null;

    /**
     * 打包周期
     */
    public int packInterval = Constant.DEFAULT_PACK_INTERVAL;

    /**
     * 查询历史轨迹开始时间
     */
    public long startTime = CommonUtil.getCurrentTime();

    /**
     * 查询历史轨迹结束时间
     */
    public long endTime = CommonUtil.getCurrentTime();

    private int pageIndex = 1;

    /**
     * 轨迹点集合
     */
    private List<LatLng> trackPoints;

    private boolean firstLocate = true;
    private HistoryTrackRequest historyTrackRequest;

    private void initListener() {
        trackListener = new OnTrackListener() {

            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                //经过服务端纠偏后的最新的一个位置点，回调
                try {
                    if (StatusCodes.SUCCESS != response.getStatus()) {

                        return;
                    }

                    LatestPoint point = response.getLatestPoint();
                    if (null == point || CommonUtil.isZeroPoint(point.getLocation().getLatitude(), point.getLocation()
                            .getLongitude())) {
                        return;
                    }

                    LatLng currentLatLng = mapUtil.convertTrace2Map(point.getLocation());
                    if (null == currentLatLng) {
                        return;
                    }

                    if (firstLocate) {
                        firstLocate = false;
                        return;
                    }

                    //当前经纬度
                    CurrentLocation.locTime = point.getLocTime();
                    CurrentLocation.latitude = currentLatLng.latitude;
                    CurrentLocation.longitude = currentLatLng.longitude;

                    if (trackPoints == null) {
                        return;
                    }
                    trackPoints.add(currentLatLng);
                    endTime = CommonUtil.getCurrentTime();
                    //ToastUtils.showToastBottom(WalkingActivity.this, "差值：" + (endTime - startTime));
                    queryHistoryTrack();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }

            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse response) {
                try {
                    int total = response.getTotal();
                    if (StatusCodes.SUCCESS != response.getStatus()) {
//                        ToastUtils.showToastBottom(WalkingActivity.this, response.getMessage());
                    } else if (0 == total) {
                    } else {
                        List<TrackPoint> points = response.getTrackPoints();
                        if (null != points) {
                            for (TrackPoint trackPoint : points) {
                                if (!CommonUtil.isZeroPoint(trackPoint.getLocation().getLatitude(),
                                        trackPoint.getLocation().getLongitude())) {
                                    trackPoints.add(MapUtil.convertTrace2Map(trackPoint.getLocation()));
                                }
                            }
                        }
                    }

                    //查找下一页数据
                    if (total > Constant.PAGE_SIZE * pageIndex) {
                        historyTrackRequest.setPageIndex(++pageIndex);
                        queryHistoryTrack();
                    } else {
                        mapUtil.drawHistoryTrack(trackPoints, false, mCurrentDirection);//画轨迹
                        queryDistance();// 查询里程
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onDistanceCallback(DistanceResponse response) {
                int aLong = (int) SpUtil.getInstance().getLong(Constant.DISTANCE, 0);
                mileage_value = (int) response.getDistance() + aLong;
                SpUtil.getInstance().putLong(Constant.DISTANCE, mileage_value);
                super.onDistanceCallback(response);
            }
        };

        entityListener = new OnEntityListener() {

            @Override
            public void onReceiveLocation(TraceLocation location) {
                //本地LBSTraceClient客户端获取的位置
                try {
                    if (StatusCodes.SUCCESS != location.getStatus() || CommonUtil.isZeroPoint(location.getLatitude(),
                            location.getLongitude())) {
                        return;
                    }
                    LatLng currentLatLng = mapUtil.convertTraceLocation2Map(location);
                    if (null == currentLatLng) {
                        return;
                    }
                    CurrentLocation.locTime = CommonUtil.toTimeStamp(location.getTime());
                    CurrentLocation.latitude = currentLatLng.latitude;
                    CurrentLocation.longitude = currentLatLng.longitude;

                    if (null != mapUtil) {
                        mapUtil.updateMapLocation(currentLatLng, mCurrentDirection);//显示当前位置
                        mapUtil.animateMapStatus(currentLatLng);//缩放
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }

        };

        traceListener = new OnTraceListener() {

            @Override
            public void onBindServiceCallback(int errorNo, String message) {
            }

            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_trace_started", true);
                    editor.apply();
                    registerReceiver();
                }
            }

            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_trace_started");
                    editor.remove("is_gather_started");
                    editor.apply();
                    unregisterPowerReceiver();
                    firstLocate = true;
                }
            }

            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.putBoolean("is_gather_started", true);
                    editor.apply();
                    stopRealTimeLoc();
                    startRealTimeLoc(packInterval);
                }
            }

            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    SharedPreferences.Editor editor = trackApp.trackConf.edit();
                    editor.remove("is_gather_started");
                    editor.apply();
                    firstLocate = true;
                    stopRealTimeLoc();
                    startRealTimeLoc(Constant.LOC_INTERVAL);
                }
            }

            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {

            }

            @Override
            public void onInitBOSCallback(int i, String s) {

            }
        };
    }

    /**
     * 查询历史里程
     */
    private void queryDistance() {
        DistanceRequest distanceRequest = new DistanceRequest(trackApp.getTag(), trackApp.serviceId, trackApp.entityName);
        distanceRequest.setStartTime(startTime); // 设置开始时间
        distanceRequest.setEndTime(endTime);     // 设置结束时间
        distanceRequest.setProcessed(true);      // 纠偏
        ProcessOption processOption = new ProcessOption();// 创建纠偏选项实例
        processOption.setNeedDenoise(true);// 去噪
        processOption.setNeedMapMatch(false);// 绑路
        processOption.setRadiusThreshold(0);
        processOption.setTransportMode(TransportMode.walking);// 交通方式为步行
        distanceRequest.setProcessOption(processOption);// 设置纠偏选项
        distanceRequest.setSupplementMode(SupplementMode.no_supplement);// 里程填充方式为无
        trackApp.mClient.queryDistance(distanceRequest, trackListener);// 查询里程
    }

    /**
     * 查询历史轨迹
     */
    private void queryHistoryTrack() {

        historyTrackRequest = new HistoryTrackRequest();
        ProcessOption processOption = new ProcessOption();//纠偏选项
        processOption.setRadiusThreshold(0);//精度过滤
        processOption.setTransportMode(TransportMode.walking);//交通方式，默认为驾车
        processOption.setNeedDenoise(true);//去噪处理，默认为false，不处理
        processOption.setNeedVacuate(true);//设置抽稀，仅在查询历史轨迹时有效，默认需要false
        processOption.setNeedMapMatch(false);//绑路处理，将点移到路径上，默认不需要false
        historyTrackRequest.setProcessOption(processOption);
        trackApp.initRequest(historyTrackRequest);
        /**
         * 设置里程补偿方式，当轨迹中断5分钟以上，会被认为是一段中断轨迹，默认不补充
         * 比如某些原因造成两点之间的距离过大，相距100米，那么在这两点之间的轨迹如何补偿
         * SupplementMode.driving：补偿轨迹为两点之间最短驾车路线
         * SupplementMode.riding：补偿轨迹为两点之间最短骑车路线
         * SupplementMode.walking：补偿轨迹为两点之间最短步行路线
         * SupplementMode.straight：补偿轨迹为两点之间直线
         */
        historyTrackRequest.setSupplementMode(SupplementMode.no_supplement);
        historyTrackRequest.setSortType(SortType.asc);//设置返回结果的排序规则，默认升序排序；升序：集合中index=0代表起始点；降序：结合中index=0代表终点。
        historyTrackRequest.setCoordTypeOutput(CoordType.bd09ll);//设置返回结果的坐标类型，默认为百度经纬度

        /**
         * 设置是否返回纠偏后轨迹，默认不纠偏
         * true：打开轨迹纠偏，返回纠偏后轨迹;
         * false：关闭轨迹纠偏，返回原始轨迹。
         * 打开纠偏时，请求时间段内轨迹点数量不能超过2万，否则将返回错误。
         */
        historyTrackRequest.setProcessed(true);

        historyTrackRequest.setServiceId(trackApp.serviceId);//设置轨迹服务id，Trace中的id
        historyTrackRequest.setEntityName(trackApp.entityName);//Trace中的entityName

        /**
         * 设置startTime和endTime，会请求这段时间内的轨迹数据;
         * 这里查询采集开始到采集结束之间的轨迹数据
         */
        historyTrackRequest.setStartTime(startTime);
        historyTrackRequest.setEndTime(endTime);
        historyTrackRequest.setPageIndex(pageIndex);
        historyTrackRequest.setPageSize(Constant.PAGE_SIZE);
        trackApp.mClient.queryHistoryTrack(historyTrackRequest, trackListener);//发起请求，设置回调监听

    }


    private void initView() {
        topBarLinear.setBackgroundColor(0);
        if (Build.VERSION.SDK_INT > 18) {
            AppUtils.initSystemBar(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llTop.getLayoutParams();
            params.height = DensityUtil.Dp2Px(this, 0);
            llTop.setLayoutParams(params);
            topBarLinear.setPadding(0, AppUtils.getStatusBarHeight(this), 0, 0);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Constant.MONEY = String.valueOf(tab.getText());
                if (Constant.isTab) {
                    SpUtil.getInstance().putString(Constant.TABLAOUT, Constant.MONEY);
                }
                if (datas1 != null) {
                    String aDouble = XiaoShuUtil.getDouble((double) 1, Double.parseDouble(datas1.get(tabLayout.getSelectedTabPosition()).getStepNumberEveryCoin()));
                    mTodayHuode.setText("1步 = " + aDouble + datas1.get(tabLayout.getSelectedTabPosition()).getCoinName());
                    mLbscText.setText(datas1.get(tabLayout.getSelectedTabPosition()).getCoinName());
                    tv_name.setText(datas1.get(tab.getPosition()).getCoinName());
                    tv_money1.setText(datas1.get(tab.getPosition()).getPutInCoin() + "");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Constant.isTab = true;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, conn, Context.BIND_WAIVE_PRIORITY);
        startService(intent);
    }

    @OnClick({R.id.Button_left, R.id.Button_right, R.id.Calculation_Button, R.id.fujin, R.id.xiangqing1, R.id.xiangqing2, R.id.xiangqing3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button_left:
                startActivity(new Intent(this, TaskActivity.class));
                break;
            case R.id.Button_right:
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case R.id.Calculation_Button:
                trackApp.mClient.startTrace(trackApp.mTrace, traceListener);//开始服务
                trackApp.mClient.setInterval(Constant.DEFAULT_GATHER_INTERVAL, packInterval);
                trackApp.mClient.startGather(traceListener);//开启采集
                startTime = CommonUtil.getCurrentTime();
                if (Constant.ISCLICK == false) {
                    LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    for (int i = 0; i < tabStrip.getChildCount(); i++) {
                        View tabView = tabStrip.getChildAt(i);
                        if (tabView != null) {
                            tabView.setClickable(false);
                        }
                    }
                    mCalculationButton.setBackgroundResource(R.mipmap.btn_stop);
                    mCalculationButton.setText("STOP");
                    Constant.ISCLICK = true;
                    setupService();
                    timer.setBase(SystemClock.elapsedRealtime());//计时器清零
                    int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
                    timer.setFormat("0" + String.valueOf(hour) + ":%s");
                    timer.start();
                } else if (Constant.ISCLICK == true) {
                    //得到能量传给结束页面，计算今日所得货币数量
                    //满足条件就能跳转
//                    if (mileage_value != 0) {
//                        if (stepnumber / mileage_value > 1 && stepnumber / mileage_value < 3.1) {
//                            Effective_value = false;
//                        } else {
                    Effective_value = true;
//                        }
//                    } else {
//                        Toast.makeText(this, "系统检测您没有动哦,请检查GPS是否打开", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    mCalculationButton.setBackgroundResource(R.mipmap.btn_start);
                    mCalculationButton.setText("START");
                    timer.stop();
                    Constant.ISCLICK = false;
                    SpUtil.getInstance().putBoolean(Constant.IsGOUP, true);
                    Intent intent = new Intent(this, CloseActivity.class);
                    intent.putExtra("coinName", Constant.MONEY);
                    intent.putExtra("lot", CurrentLocation.latitude + "");
                    intent.putExtra("lat", CurrentLocation.longitude + "");
                    intent.putExtra("time", SystemClock.elapsedRealtime() + "");
                    intent.putExtra("Effective_value", Effective_value + "");
                    long aLong = SpUtil.getInstance().getLong(Constant.STEPNUMBER, 0);
                    intent.putExtra("stepnumber", aLong + "");
                    intent.putExtra("moeny", String.valueOf(stepToKcal(aLong)));
                    startActivityForResult(intent, reqerstcode);
                }
                break;
            case R.id.fujin:
                if (MyApplication.user == null) {
                    DialogLoginView dialogLoginView = new DialogLoginView(WalkingActivity.this);
                    dialogLoginView.showDialog();
                } else {
                    Intent intent = new Intent(this, NearbyRankingsActivity.class);
                    intent.putExtra("lot", CurrentLocation.latitude + "");
                    intent.putExtra("lat", CurrentLocation.longitude + "");
                    startActivity(intent);
                }
                break;
            case R.id.xiangqing1:
                DialogConfirmView dialogConfirmView = new DialogConfirmView(WalkingActivity.this);
                dialogConfirmView.setDialogMsg(getString(R.string.msg3));
                dialogConfirmView.showDialog();
                break;
            case R.id.xiangqing2:
                DialogConfirmView dialogConfirmView1 = new DialogConfirmView(WalkingActivity.this);
                dialogConfirmView1.setDialogMsg(getString(R.string.msg2));
                dialogConfirmView1.showDialog();
                break;
            case R.id.xiangqing3:
                DialogConfirmView dialogConfirmView2 = new DialogConfirmView(WalkingActivity.this);
                dialogConfirmView2.setDialogMsg(getString(R.string.msg1));
                dialogConfirmView2.showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (reqerstcode == requestCode && resultCode == 101) {
            mGetTodayMany.setText("0");
            calories.setText("0");
            timer.setBase(SystemClock.elapsedRealtime());//计时器清零
            timer.stop();
            Constant.hasRecord = false;
            SpUtil.getInstance().putLong(Constant.STEPNUMBER, 0);
            SpUtil.getInstance().putLong(Constant.DISTANCE, 0);
            SpUtil.getInstance().putBoolean(Constant.IsGOUP, true);
            stepCount.setCurrentCount(SumBuShu, 0);
            mGetTodayMany.setText(String.valueOf(stepToKcal(0)));
            calories.setText(String.valueOf(stepToKca()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //步数与币的交
    private String stepToKcal(float step) {
        if (datas1 != null) {
            double i = Double.parseDouble(datas1.get(tabLayout.getSelectedTabPosition()).getStepNumberEveryCoin());
            String aDouble = XiaoShuUtil.getDouble((double) step, i);
            return aDouble;
        } else {
            return "0";
        }
    }

    //步数与钱的交
    private float stepToKca() {
        if (datas1 != null) {
            Float txt = Float.parseFloat(String.valueOf(mGetTodayMany.getText())) * Float.parseFloat(datas1.get(tabLayout.getSelectedTabPosition()).getPrice());
            return txt;
        } else {
            return 0;
        }
    }

    private long exitTime = 0;//初始时间变量LONG

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    static class RealTimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    }

    /**
     * 注册广播（电源锁、GPS状态）
     */

    private PowerManager.WakeLock wakeLock;
    private TrackReceiver trackReceiver;

    private void registerReceiver() {
        if (trackApp.isRegisterReceiver) {
            return;
        }

        if (null == wakeLock) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "track upload");
        }
        if (null == trackReceiver) {
            trackReceiver = new TrackReceiver(wakeLock);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(StatusCodes.GPS_STATUS_ACTION);
        trackApp.registerReceiver(trackReceiver, filter);
        trackApp.isRegisterReceiver = true;

    }

    private void unregisterPowerReceiver() {
        if (!trackApp.isRegisterReceiver) {
            return;
        }
        if (null != trackReceiver) {
            trackApp.unregisterReceiver(trackReceiver);
        }
        trackApp.isRegisterReceiver = false;
    }

    /**
     * 实时定位任务
     */
    class RealTimeLocRunnable implements Runnable {

        private int interval = 0;

        public RealTimeLocRunnable(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            trackApp.getCurrentLocation(entityListener, trackListener);
            realTimeHandler.postDelayed(this, interval * 1000);
        }
    }

    public void startRealTimeLoc(int interval) {
        realTimeLocRunnable = new RealTimeLocRunnable(interval);
        realTimeHandler.post(realTimeLocRunnable);
    }

    public void stopRealTimeLoc() {
        if (null != realTimeHandler && null != realTimeLocRunnable) {
            realTimeHandler.removeCallbacks(realTimeLocRunnable);
        }
        trackApp.mClient.stopRealTimeLoc();
    }

    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 定位精确位置
        addPermission(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 读取手机状态
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

}
