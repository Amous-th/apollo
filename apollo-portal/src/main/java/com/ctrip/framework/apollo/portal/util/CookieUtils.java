package com.ctrip.framework.apollo.portal.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class CookieUtils {

    public static String getCookieValue(HttpServletRequest request,String cookieKey) {
        String value = null;
        String key = "test";
        String ivParameter = "test";
        Cookie[] cookie = request.getCookies();
        for (Cookie cook : cookie) {
            String cookName = cook.getName();
            if (cookName.equalsIgnoreCase(cookieKey)) {
                value = cook.getValue();
                //value = AESCryptUtils.decrypt(key, ivParameter, valueEncrypted);

            }
        }
        return value;
    }

}
