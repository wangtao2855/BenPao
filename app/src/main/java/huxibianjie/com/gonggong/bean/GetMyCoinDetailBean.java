package huxibianjie.com.gonggong.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangtao on 2018/6/20.
 */

public class GetMyCoinDetailBean {


    /**
     * code : 0
     * codeMsg : ok
     * datas : [{"coin":100,"coinName":"DVE","createTime":"2018-06-18 17:44:35","stepNumber":100,"time":120},{"coin":100,"coinName":"DVE","createTime":"2018-06-18 17:45:28","stepNumber":100,"time":120},{"coin":10,"coinName":"DVE","createTime":"2018-06-18 17:46:09","stepNumber":100,"time":120},{"coin":100,"coinName":"DVE","createTime":"2018-06-18 17:57:13","stepNumber":100,"time":120},{"coin":100,"coinName":"DVE","createTime":"2018-06-18 17:58:06","stepNumber":100,"time":120},{"coin":100,"coinName":"DVE","createTime":"2018-06-18 18:01:52","stepNumber":100,"time":120},{"coin":50,"coinName":"DVE","createTime":"2018-06-19 21:28:17","stepNumber":100,"time":120}]
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
        return codeMsg == null ? "" : codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public List<DatasBean> getDatas() {
        if (datas == null) {
            return new ArrayList<>();
        }
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * coin : 100
         * coinName : DVE
         * createTime : 2018-06-18 17:44:35
         * stepNumber : 100
         * time : 120
         */

        private String coin;
        private String coinName;
        private String createTime;
        private String stepNumber;
        private String time;

        public String getCoin() {
            return coin == null ? "" : coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getCoinName() {
            return coinName == null ? "" : coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStepNumber() {
            return stepNumber == null ? "" : stepNumber;
        }

        public void setStepNumber(String stepNumber) {
            this.stepNumber = stepNumber;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
