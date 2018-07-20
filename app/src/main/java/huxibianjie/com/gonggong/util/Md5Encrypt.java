package huxibianjie.com.gonggong.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wangtao on 2018/7/15.
 */

public class Md5Encrypt {

    public static String md5(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        byte[] encodedValue = md5.digest();
        int j = encodedValue.length;
        char finalValue[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte encoded = encodedValue[i];
            finalValue[k++] = hexDigits[encoded >> 4 & 0xf];
            finalValue[k++] = hexDigits[encoded & 0xf];
        }
        return new String(finalValue);
    }

    public static  String createHashedQueryString(Map queryMap, String appSecret) {
        Map<String, Object> treeMap = new TreeMap<String, Object>(queryMap);
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<?, ?> entry : treeMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue() + "", "utf-8"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = Md5Encrypt.md5(String.format("%s&salt=%s", sb, appSecret));
        //sign就是最后的签名结果.
        return sign;
    }
}
