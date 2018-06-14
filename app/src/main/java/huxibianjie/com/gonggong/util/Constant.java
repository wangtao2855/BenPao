package huxibianjie.com.gonggong.util;

/**
 * Created by adu on 2016/10/23.
 */

public class Constant {


    public static final class Config {

        public static final int MSG_FROM_CLIENT = 0;
        public static final int MSG_FROM_SERVER = 1;
        public static final int REQUEST_SERVER = 2;

        public static final String isStepServiceRunning = "isStepServiceRunning";
        public static final String stopStepService = "stopStepService";
        public static final String stepNum = "stepNum";
        public static final String FILE_NAME = "config";
    }

    public static final String TAG = "BaiduTraceSDK_V3";

    public static final int REQUEST_CODE = 1;

    public static final int RESULT_CODE = 1;

    public static final int DEFAULT_RADIUS_THRESHOLD = 0;

    public static final int PAGE_SIZE = 1000;

    /**
     * 默认采集周期
     */
    public static final int DEFAULT_GATHER_INTERVAL = 2;

    /**
     * 默认打包周期
     */
    public static final int DEFAULT_PACK_INTERVAL = 2;

    /**
     * 实时定位间隔(单位:秒)
     */
    public static final int LOC_INTERVAL = 2;

    /**
     * 最后一次定位信息
     */
    public static final String LAST_LOCATION = "last_location";

}
