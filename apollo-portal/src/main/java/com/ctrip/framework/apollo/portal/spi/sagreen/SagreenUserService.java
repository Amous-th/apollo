package com.ctrip.framework.apollo.portal.spi.sagreen;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserService;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class SagreenUserService implements UserService {

  @Override
  public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
    return Arrays.asList(assembleDefaultUser());
  }

  @Override
  public UserInfo findByUserId(String userId) {
    if (userId.equals("hong.tian")) {
      return assembleDefaultUser();
    }
    return null;
  }

  @Override
  public List<UserInfo> findByUserIds(List<String> userIds) {
    if (userIds.contains("hong.tian")) {
      return Lists.newArrayList(assembleDefaultUser());
    }
    return null;
  }

  private UserInfo assembleDefaultUser() {
    UserInfo defaultUser = new UserInfo();
    defaultUser.setUserId("hong.tian");
    defaultUser.setName("hong.tian");
    defaultUser.setEmail("hong.tian@sa-green.cn");

    return defaultUser;
  }
}
