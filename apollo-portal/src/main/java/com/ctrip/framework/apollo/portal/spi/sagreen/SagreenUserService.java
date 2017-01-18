package com.ctrip.framework.apollo.portal.spi.sagreen;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.entity.po.User;
import com.ctrip.framework.apollo.portal.service.ProtalUserService;
import com.ctrip.framework.apollo.portal.spi.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class SagreenUserService implements UserService {

    @Autowired
    private ProtalUserService protalUserService;

    @Override
    public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
        List<User> users = protalUserService.findAll();
        return getUserInfos(users);
    }

    private List<UserInfo> getUserInfos(List<User> users) {
        List<UserInfo> userInfos = Lists.newArrayList();
        if(CollectionUtils.isEmpty(users)){
            return userInfos;
        }
        users.stream().forEach(user -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(user.getUserName());
            userInfo.setUserId(user.getUserName());
            userInfo.setEmail(user.getEmail());
            userInfos.add(userInfo);
        });
        return userInfos;
    }

    @Override
    public UserInfo findByUserId(String userId) {
        User user = protalUserService.findByUserName(userId);
        if(null == user){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserName());
        userInfo.setName(user.getUserName());
        userInfo.setEmail(user.getEmail());
        return userInfo;
    }

    @Override
    public List<UserInfo> findByUserIds(List<String> userIds) {
        List<User> users = protalUserService.findAllByUserNames(userIds);
        return getUserInfos(users);
    }

}
