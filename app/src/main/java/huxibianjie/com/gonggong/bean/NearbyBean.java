package huxibianjie.com.gonggong.bean;

import java.util.List;

/**
 * Created by Mr.c on 2018/6/20.
 */

public class NearbyBean {


    /**
     * code : 0
     * codeMsg : ok
     * datas : [{"coin":616.15,"distance":16,"head":"/api_files/head/000/001/000/head_781.jpg","id":0,"name":"Like Angel","stepNumberTotal":824,"timeTotal":482088066,"uid":1000},{"coin":355,"distance":6719769,"id":0,"stepNumberTotal":7100,"timeTotal":30120,"uid":1136},{"coin":200,"distance":6719769,"head":"http://img04.tooopen.com/images/20130424/tooopen_15462112.jpg","id":0,"name":"就是太爱你","stepNumberTotal":200,"timeTotal":240,"uid":1126},{"coin":75.75,"distance":95,"id":0,"stepNumberTotal":174,"timeTotal":2409887799,"uid":1132},{"coin":14.15,"distance":7,"id":0,"stepNumberTotal":67,"timeTotal":1959585549,"uid":1135}]
     */

    private int code;
    private String codeMsg;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * coin : 616.15
         * distance : 16
         * head : /api_files/head/000/001/000/head_781.jpg
         * id : 0
         * name : Like Angel
         * stepNumberTotal : 824
         * timeTotal : 482088066
         * uid : 1000
         */

        private String coin;
        private String distance;
        private String head;
        private String id;
        private String name;
        private String stepNumberTotal;
        private String timeTotal;
        private String uid;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStepNumberTotal() {
            return stepNumberTotal;
        }

        public void setStepNumberTotal(String stepNumberTotal) {
            this.stepNumberTotal = stepNumberTotal;
        }

        public String getTimeTotal() {
            return timeTotal;
        }

        public void setTimeTotal(String timeTotal) {
            this.timeTotal = timeTotal;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
