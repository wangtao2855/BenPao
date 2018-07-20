package huxibianjie.com.gonggong.bean;

/**
 * Created by wangtao on 2018/7/1.
 */

public class GetMyInviteBean {


    /**
     * code : 0
     * codeMsg : ok
     * datas : {"inviteCount":2,"name":"Like Angel","inviteUrl":"http://dvechain.io/iv?i=DrlSEyHF8hXbDEiH9VUTag%3D%3D","inviteCode":"DrlSEyHF8hXbDEiH9VUTag%3D%3D","inviteDve":1000}
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
        /**
         * inviteCount : 2
         * name : Like Angel
         * inviteUrl : http://dvechain.io/iv?i=DrlSEyHF8hXbDEiH9VUTag%3D%3D
         * inviteCode : DrlSEyHF8hXbDEiH9VUTag%3D%3D
         * inviteDve : 1000
         */

        private int inviteCount;
        private String name;
        private String inviteUrl;
        private String inviteCode;
        private int inviteDve;

        public int getInviteCount() {
            return inviteCount;
        }

        public void setInviteCount(int inviteCount) {
            this.inviteCount = inviteCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInviteUrl() {
            return inviteUrl;
        }

        public void setInviteUrl(String inviteUrl) {
            this.inviteUrl = inviteUrl;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public int getInviteDve() {
            return inviteDve;
        }

        public void setInviteDve(int inviteDve) {
            this.inviteDve = inviteDve;
        }
    }
}
