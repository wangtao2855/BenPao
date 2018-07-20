package huxibianjie.com.gonggong.bean;

import java.util.List;

/**
 * Created by Mr.c on 2018/6/19.
 */

public class GetBabner {

    /**
     * code : 0
     * codeMsg : ok
     * datas : {"openAD":[{"content":"","createTime":1529412556000,"delayTime":5,"endTime":"2018-06-19 20:49","id":10,"imgs":"http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529412517139022400.jpg","isShow":1,"linkUrl":"http://127.0.0.1:8080/dve-admin/wappages/base/advert?id=10","msgKey":"openAD","msgValue":"开屏广告","orderby":0,"startTime":"2018-06-19 20:49","title":"开屏广告","type":"content","updateTime":"2018-06-19 20:49:16"}],"activity_AD":[{"content":"http://www.baidu.com","createTime":1529412934000,"delayTime":5,"id":11,"imgs":"http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529412924050089992.jpg","isShow":1,"linkUrl":"","msgKey":"activity_AD","msgValue":"活动广告图","orderby":0,"title":"广告图1","type":"link","updateTime":"2018-06-19 20:55:34"},{"content":"http://www.baidu.com","createTime":1529412969000,"delayTime":5,"id":12,"imgs":"http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529412955650084465.jpg","isShow":1,"linkUrl":"","msgKey":"activity_AD","msgValue":"活动广告图","orderby":0,"title":"广告图2","type":"link","updateTime":"2018-06-19 20:56:09"}],"activity_activity":[{"content":"","createTime":1529413192000,"delayTime":5,"id":13,"imgs":"http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529413170475040981.jpg","isShow":1,"linkUrl":"http://127.0.0.1:8080/dve-admin/wappages/base/advert?id=13","msgKey":"activity_activity","msgValue":"活动列表","orderby":0,"title":"活动","type":"content","updateTime":"2018-06-19 20:59:52"},{"content":"","createTime":1529413244000,"delayTime":5,"id":14,"imgs":"http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529413208060001937.jpg","isShow":1,"linkUrl":"http://127.0.0.1:8080/dve-admin/wappages/base/advert?id=14","msgKey":"activity_activity","msgValue":"活动列表","orderby":0,"title":"活动2","type":"content","updateTime":"2018-06-19 21:00:44"},{"content":"","createTime":1529413325000,"delayTime":5,"id":15,"imgs":"http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529413260746009024.jpg","isShow":1,"linkUrl":"http://127.0.0.1:8080/dve-admin/wappages/base/advert?id=15","msgKey":"activity_activity","msgValue":"活动列表","orderby":0,"title":"活动3","type":"content","updateTime":"2018-06-19 21:02:05"}]}
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
        return codeMsg;
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
        private List<OpenADBean> openAD;
        private List<ActivityADBean> activity_AD;
        private List<ActivityActivityBean> activity_activity;

        public List<OpenADBean> getOpenAD() {
            return openAD;
        }

        public void setOpenAD(List<OpenADBean> openAD) {
            this.openAD = openAD;
        }

        public List<ActivityADBean> getActivity_AD() {
            return activity_AD;
        }

        public void setActivity_AD(List<ActivityADBean> activity_AD) {
            this.activity_AD = activity_AD;
        }

        public List<ActivityActivityBean> getActivity_activity() {
            return activity_activity;
        }

        public void setActivity_activity(List<ActivityActivityBean> activity_activity) {
            this.activity_activity = activity_activity;
        }

        public static class OpenADBean {
            /**
             * content :
             * createTime : 1529412556000
             * delayTime : 5
             * endTime : 2018-06-19 20:49
             * id : 10
             * imgs : http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529412517139022400.jpg
             * isShow : 1
             * linkUrl : http://127.0.0.1:8080/dve-admin/wappages/base/advert?id=10
             * msgKey : openAD
             * msgValue : 开屏广告
             * orderby : 0
             * startTime : 2018-06-19 20:49
             * title : 开屏广告
             * type : content
             * updateTime : 2018-06-19 20:49:16
             */

            private String content;
            private long createTime;
            private int delayTime;
            private String endTime;
            private int id;
            private String imgs;
            private int isShow;
            private String linkUrl;
            private String msgKey;
            private String msgValue;
            private int orderby;
            private String startTime;
            private String title;
            private String type;
            private String updateTime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDelayTime() {
                return delayTime;
            }

            public void setDelayTime(int delayTime) {
                this.delayTime = delayTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getMsgKey() {
                return msgKey;
            }

            public void setMsgKey(String msgKey) {
                this.msgKey = msgKey;
            }

            public String getMsgValue() {
                return msgValue;
            }

            public void setMsgValue(String msgValue) {
                this.msgValue = msgValue;
            }

            public int getOrderby() {
                return orderby;
            }

            public void setOrderby(int orderby) {
                this.orderby = orderby;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class ActivityADBean {
            /**
             * content : http://www.baidu.com
             * createTime : 1529412934000
             * delayTime : 5
             * id : 11
             * imgs : http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529412924050089992.jpg
             * isShow : 1
             * linkUrl :
             * msgKey : activity_AD
             * msgValue : 活动广告图
             * orderby : 0
             * title : 广告图1
             * type : link
             * updateTime : 2018-06-19 20:55:34
             */

            private String content;
            private long createTime;
            private int delayTime;
            private int id;
            private String imgs;
            private int isShow;
            private String linkUrl;
            private String msgKey;
            private String msgValue;
            private int orderby;
            private String title;
            private String type;
            private String updateTime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDelayTime() {
                return delayTime;
            }

            public void setDelayTime(int delayTime) {
                this.delayTime = delayTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getMsgKey() {
                return msgKey;
            }

            public void setMsgKey(String msgKey) {
                this.msgKey = msgKey;
            }

            public String getMsgValue() {
                return msgValue;
            }

            public void setMsgValue(String msgValue) {
                this.msgValue = msgValue;
            }

            public int getOrderby() {
                return orderby;
            }

            public void setOrderby(int orderby) {
                this.orderby = orderby;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class ActivityActivityBean {
            /**
             * content :
             * createTime : 1529413192000
             * delayTime : 5
             * id : 13
             * imgs : http://admin.dvechain.io/api_files/chatMsg/image/20180619/1529413170475040981.jpg
             * isShow : 1
             * linkUrl : http://127.0.0.1:8080/dve-admin/wappages/base/advert?id=13
             * msgKey : activity_activity
             * msgValue : 活动列表
             * orderby : 0
             * title : 活动
             * type : content
             * updateTime : 2018-06-19 20:59:52
             */

            private String content;
            private long createTime;
            private int delayTime;
            private int id;
            private String imgs;
            private int isShow;
            private String linkUrl;
            private String msgKey;
            private String msgValue;
            private int orderby;
            private String title;
            private String type;
            private String updateTime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDelayTime() {
                return delayTime;
            }

            public void setDelayTime(int delayTime) {
                this.delayTime = delayTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getMsgKey() {
                return msgKey;
            }

            public void setMsgKey(String msgKey) {
                this.msgKey = msgKey;
            }

            public String getMsgValue() {
                return msgValue;
            }

            public void setMsgValue(String msgValue) {
                this.msgValue = msgValue;
            }

            public int getOrderby() {
                return orderby;
            }

            public void setOrderby(int orderby) {
                this.orderby = orderby;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
