package huxibianjie.com.gonggong.util;

/**
 * Created by adu on 2016/10/23.
 */

public class Constant {


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
     * 币种选择
     */
    public static String MONEY = "DVE";
    /**
     * sp保存的缓存数据
     */
    public final static String USER = "userInfo";
    /**
     * 缓存的步数儿
     */
    public static final String STEPNUMBER = "stepnumber";

    /**
     * 缓存的里程
     */
    public static final String DISTANCE = "distance";
    /**
     * 判断srever 计步器（不能停止）
     */
    public static boolean ISCLICK = false;
    /**
     * 保存TabLayout
     */
    public static final String TABLAOUT = "tabLayout";
    /**
     * Tablayout临时储存
     */
    public static boolean isTab = false;
    /**
     * 保存的头像地址，搞本地的
     */
    public static String REALPATHFROMURI = "";
    /**
     * 判断是否提交成功
     */
    public static String IsGOUP;
    /**
     * 第一次缓存系统计步器的步数儿。是为了让用户点击stop得到最新的数据 只是一个判断数据
     */
    public static final String ONESTEPNUMBER = "onestepnumber";
    /**
     * 每次第一次启动记步服务时是否从系统中获取了已有的步数记录 fasle就是需要清除步数了
     */
    public static boolean hasRecord = false;
}
