package com.ctrip.framework.apollo.portal.spi.sagreen;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserInfoHolder;

/**
 * 不是ctrip的公司默认提供一个假用户
 */
public class SagreenUserInfoHolder implements UserInfoHolder {


  public SagreenUserInfoHolder() {

  }

  @Override
  public UserInfo getUser() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId("hong.tian");
    return userInfo;
  }
}
