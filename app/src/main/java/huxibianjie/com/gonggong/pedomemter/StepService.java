package huxibianjie.com.gonggong.pedomemter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.home.runmining.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import huxibianjie.com.gonggong.WalkingActivity;
import huxibianjie.com.gonggong.util.Constant;


/**
 * 实现计步功能的前台服务
 */
public class StepService extends Service {
    //默认为30秒进行一次存储
    private static int duration = 30000;
    private static String CURRENTDATE = "";
    private SensorManager sensorManager;
    private StepDetector stepDetector;
    private NotificationManager nm;
    private NotificationCompat.Builder builder;
    private Messenger messenger = new Messenger(new MessenerHandler());
    private BroadcastReceiver mBatInfoReceiver;
    private WakeLock mWakeLock;
    private TimeCount time;
    private SharedPreferences sp;

    //测试
    private static int i = 0;
    //计步传感器类型 0-counter 1-detector
    private static int stepSensor = -1;
    private boolean isBroad = false;


    private static class MessenerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        Messenger messenger = msg.replyTo;
                        Message replyMsg = Message.obtain(null, 1);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.Config.stepNum, StepDetector.CURRENT_SETP);
                        replyMsg.setData(bundle);
                        messenger.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CURRENTDATE = getTodayDate();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            public void run() {
                startStepDetector();
            }
        }).start();

        startTimeCount();
        initTodayData();

        Log.i("xf", "onCreate()");

        updateNotification("今日步数：" + StepDetector.CURRENT_SETP + " 步");
    }

    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void initTodayData() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        StepDetector.CURRENT_SETP = sp.getInt(Constant.Config.stepNum, 0);
    }

    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        filter.addAction(Constant.Config.stopStepService);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d("xf", "screen on");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d("xf", "screen off");
                    //改为60秒一存储
                    duration = 60000;
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d("xf", "screen unlock");
                    save();
                    //改为30秒一存储
                    duration = 30000;
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.i("xf", " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                    //保存一次
                    save();
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    Log.i("xf", " receive ACTION_SHUTDOWN");
                    save();
                } else if (Constant.Config.stopStepService.equals(intent.getAction())) {
                    save();
                    isBroad = true;
                    stopSelf();
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    private void startTimeCount() {
        time = new TimeCount(duration, 1000);
        time.start();
    }

    /**
     * 更新通知
     */
    private void updateNotification(String content) {
        Log.i("xf", "updateNotification: " + content);
        builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_MIN);

        //Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, WalkingActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_appicon);
        builder.setTicker("BasePedo");
        builder.setContentTitle("Running");
        //设置不可清除
        builder.setOngoing(true);
        builder.setContentText(content);
        Notification notification = builder.build();
        //设置为前台服务，确保服务不被摧毁
        startForeground(0, notification);

        //展示在通知栏
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(R.string.app_name, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("xf", "onBind()");
        return messenger.getBinder();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        Log.i("xf", "onStart()");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("xf", "onStartCommand()");
        return START_STICKY;
    }

    private void startStepDetector() {
        if (sensorManager != null && stepDetector != null) {
            sensorManager.unregisterListener(stepDetector);
            sensorManager = null;
            stepDetector = null;
        }
        getLock(this);
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        addBasePedoListener();
    }

    private void addBasePedoListener() {
        stepDetector = new StepDetector(this);
        // 获得传感器的类型，这里获得的类型是加速度传感器
        // 此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // sensorManager.unregisterListener(stepDetector);
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_UI);
        stepDetector.setOnSensorChangeListener(new StepDetector.OnSensorChangeListener() {

            @Override
            public void onChange() {
                updateNotification("今日步数：" + StepDetector.CURRENT_SETP + " 步");
                Log.v("xf", "onChange： " + StepDetector.CURRENT_SETP);
            }
        });
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // 如果计时器正常结束，则开始计步
            time.cancel();
            save();
            startTimeCount();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

    }

    private void save() {
        int tempStep = StepDetector.CURRENT_SETP;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constant.Config.stepNum, tempStep);
//        if (Constant.Config.Go_up==true){
//            editor.putInt(Constant.Config.stepNum, 0);
//            StepDetector.CURRENT_SETP = 0;
//            Constant.Config.Go_up = false;
//        }
        editor.commit();

        Log.v("xf", "sp.putInt: " + "---CURRENTDATE: " + CURRENTDATE + "---tempStep: " + tempStep);
    }


    @Override
    public void onDestroy() {
        Log.i("xf", "onDestroy()");
        //取消前台进程
        stopForeground(true);
        unregisterReceiver(mBatInfoReceiver);
        if (!isBroad) {
            Log.i("xf", "onDestroy()重启");
            Intent intent = new Intent(this, StepService.class);
            startService(intent);
        } else {
            Log.i("xf", "onDestroy()摧毁");
            sensorManager.unregisterListener(stepDetector);
           // nm.cancel(R.string.app_name);
        }
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private void unlock() {
        setLockPatternEnabled(android.provider.Settings.Secure.LOCK_PATTERN_ENABLED, false);
    }

    private void setLockPatternEnabled(String systemSettingKey, boolean enabled) {
        //推荐使用
        android.provider.Settings.Secure.putInt(getContentResolver(), systemSettingKey, enabled ? 1 : 0);
    }

    synchronized private WakeLock getLock(Context context) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld())
                mWakeLock.release();
            mWakeLock = null;
        }

        if (mWakeLock == null) {
            PowerManager mgr = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    StepService.class.getName());
            mWakeLock.setReferenceCounted(true);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (hour >= 23 || hour <= 6) {
                mWakeLock.acquire(5000);
            } else {
                mWakeLock.acquire(300000);
            }
        }

        return (mWakeLock);
    }
}
