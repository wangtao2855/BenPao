package huxibianjie.com.gonggong.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangtao on 2018/6/20.
 */

public class GetMyCoinBean {


    /**
     * code : 0
     * codeMsg : ok
     * datas : [{"coin":560,"coinName":"DVE","stepNumberTotal":700,"timeTotal":840},{"coin":0.11,"coinName":"BTC","stepNumberTotal":200,"timeTotal":240}]
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
         * coin : 560
         * coinName : DVE
         * stepNumberTotal : 700
         * timeTotal : 840
         */

        private String coin;
        private String coinName;
        private String stepNumberTotal;
        private String timeTotal;

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

        public String getStepNumberTotal() {
            return stepNumberTotal == null ? "" : stepNumberTotal;
        }

        public void setStepNumberTotal(String stepNumberTotal) {
            this.stepNumberTotal = stepNumberTotal;
        }

        public String getTimeTotal() {
            return timeTotal == null ? "" : timeTotal;
        }

        public void setTimeTotal(String timeTotal) {
            this.timeTotal = timeTotal;
        }
    }
}
