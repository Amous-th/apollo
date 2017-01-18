package com.ctrip.framework.apollo.portal.util;

import org.apache.commons.lang.ArrayUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class CookieUtils {

    private static final ThreadLocal<String> LOCAL_USERNAME = new ThreadLocal<>();

    public static String getCookieValue(HttpServletRequest request,String cookieKey) {
        String value = null;
        String key = "test";
        String ivParameter = "test";
        Cookie[] cookie = request.getCookies();
        if(ArrayUtils.isEmpty(cookie)){
            return null;
        }
        for (Cookie cook : cookie) {
            String cookName = cook.getName();
            if (cookName.equalsIgnoreCase(cookieKey)) {
                value = cook.getValue();
                //value = AESCryptUtils.decrypt(key, ivParameter, valueEncrypted);

            }
        }
        return value;
    }

    public static String getLocalUserName() {
        return LOCAL_USERNAME.get();
    }

    public static void setLocalUserName(String userName) {
        LOCAL_USERNAME.set(userName);
    }

}
