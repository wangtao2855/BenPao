package huxibianjie.com.gonggong.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangtao on 2018/6/19.
 */

public class Yuebean {


    /**
     * code : 0
     * codeMsg : ok
     * datas : [{"coinName":"DVE","maxCoinEveryDay":1000,"putInCoin":998885,"stepNumberEveryCoin":2},{"coinName":"BTC","maxCoinEveryDay":2,"putInCoin":999.89,"stepNumberEveryCoin":1000}]
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
         * coinName : DVE
         * maxCoinEveryDay : 1000
         * putInCoin : 998885
         * stepNumberEveryCoin : 2
         */

        private String coinName;
        private String  maxCoinEveryDay;
        private String putInCoin;
        private String stepNumberEveryCoin;
        private String price;

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCoinName() {
            return coinName == null ? "" : coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getMaxCoinEveryDay() {
            return maxCoinEveryDay;
        }

        public void setMaxCoinEveryDay(String maxCoinEveryDay) {
            this.maxCoinEveryDay = maxCoinEveryDay;
        }

        public String getPutInCoin() {
            return putInCoin;
        }

        public void setPutInCoin(String putInCoin) {
            this.putInCoin = putInCoin;
        }

        public String getStepNumberEveryCoin() {
            return stepNumberEveryCoin;
        }

        public void setStepNumberEveryCoin(String stepNumberEveryCoin) {
            this.stepNumberEveryCoin = stepNumberEveryCoin;
        }
    }
}
