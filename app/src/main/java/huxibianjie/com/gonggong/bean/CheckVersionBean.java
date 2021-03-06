package huxibianjie.com.gonggong.bean;

/**
 * Created by wangtao on 2018/6/24.
 */

public class CheckVersionBean {


    /**
     * code : 0
     * codeMsg : ok
     * datas : {"state":2,"stateMsg":"state=0:不用更新；state=1:需要进行提醒更新；state=2:强制更新","url":"http://blued2013.oss.aliyuncs.com/Blued2013_2.0.5.apk","versionMsg":"9月4日更新：\n新增：\n1、新增了远程崩溃统计，使开发团队能及时获取大家的崩溃日志（不含用户的身份等敏感信息）。\n2、新增了找人界面加载数据时增加loading提示\n修复：\n1、修复了程序切换到后台会崩机的现象。\n2、修复了私聊离线消息接收不即使的问题。\n3、修复了极少情况下私聊消息阅读后不发送已读回执问题。\n4、修复上传照片时用\u201c文件管理器\u201d选择照片后的崩溃问题。\n优化：\n1、优化了保存别人照片的存储方式，更加利于系统图库捕捉到这张图片了。\n2、优化了详细资料界面和动态评论列表界面的细节。\n3、优化了修改资料的逻辑。\n4、优化了按钮的点击效果，现在点击\u201c返回\u201d、\u201c菜单\u201d等按钮不再那么纠结了。"}
     */

    private int code;
    private String codeMsg;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg == null ? "" : codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * state : 2
         * stateMsg : state=0:不用更新；state=1:需要进行提醒更新；state=2:强制更新
         * url : http://blued2013.oss.aliyuncs.com/Blued2013_2.0.5.apk
         * versionMsg : 9月4日更新：
         * 新增：
         * 1、新增了远程崩溃统计，使开发团队能及时获取大家的崩溃日志（不含用户的身份等敏感信息）。
         * 2、新增了找人界面加载数据时增加loading提示
         * 修复：
         * 1、修复了程序切换到后台会崩机的现象。
         * 2、修复了私聊离线消息接收不即使的问题。
         * 3、修复了极少情况下私聊消息阅读后不发送已读回执问题。
         * 4、修复上传照片时用“文件管理器”选择照片后的崩溃问题。
         * 优化：
         * 1、优化了保存别人照片的存储方式，更加利于系统图库捕捉到这张图片了。
         * 2、优化了详细资料界面和动态评论列表界面的细节。
         * 3、优化了修改资料的逻辑。
         * 4、优化了按钮的点击效果，现在点击“返回”、“菜单”等按钮不再那么纠结了。
         */

        private String state;
        private String stateMsg;
        private String url;
        private String versionMsg;

        public String getState() {
            return state == null ? "" : state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateMsg() {
            return stateMsg == null ? "" : stateMsg;
        }

        public void setStateMsg(String stateMsg) {
            this.stateMsg = stateMsg;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersionMsg() {
            return versionMsg == null ? "" : versionMsg;
        }

        public void setVersionMsg(String versionMsg) {
            this.versionMsg = versionMsg;
        }
    }
}
