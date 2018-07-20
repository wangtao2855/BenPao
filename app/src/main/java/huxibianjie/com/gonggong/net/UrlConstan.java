package huxibianjie.com.gonggong.net;

/**
 * Created by wangtao on 2018/6/18.
 */

public interface UrlConstan {

    String baseUrl = "http://api.dvechain.io//dve-api/rest/api";
    String GETAUTHCODEFORACLOGIN = baseUrl + "/userInfo/getAuthCodeForACLogin?";
    String LOGIN = baseUrl + "/userInfo/login?";
    String GETMYCOIN = baseUrl + "/coinInfo/getMyCoin?";
    String GETMYCOINDETAIL = baseUrl + "/coinInfo/getMyCoinDetail?";
    String PUSHSTEPNUMBER = "http://api.dvechain.io//dve-api/rest/api2/coinInfo/add?";
    String GETCOINSTOCK = baseUrl + "/coinInfo/getCoinStock?";
    String GETABANER = baseUrl + "/base/advert?";
    String NEARBYRANKINGS = baseUrl + "/coinInfo/nearBank?";
    String DOWN_LOAD = baseUrl + "/setting/checkVersion?";
    String GETMYINVITE = baseUrl + "/coinInfo/getMyInvite?";
    String MYINVITE = "http://admin.dvechain.io/dve-admin/phone/myinvite?targetUid=";
}
