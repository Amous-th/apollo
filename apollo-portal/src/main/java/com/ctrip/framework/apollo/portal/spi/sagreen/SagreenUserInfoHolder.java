package com.ctrip.framework.apollo.portal.spi.sagreen;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserInfoHolder;
import com.ctrip.framework.apollo.portal.util.CookieUtils;

/**
 * 不是ctrip的公司默认提供一个假用户
 */
public class SagreenUserInfoHolder implements UserInfoHolder {

    private ThreadLocal threadLocal = new ThreadLocal();

    public SagreenUserInfoHolder() {

    }

    @Override
    public UserInfo getUser() {
        String userName =  CookieUtils.getLocalUserName();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userName);
        return userInfo;
    }
}
