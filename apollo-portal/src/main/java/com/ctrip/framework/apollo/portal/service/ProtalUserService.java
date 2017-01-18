package com.ctrip.framework.apollo.portal.service;

import com.ctrip.framework.apollo.portal.entity.po.User;
import com.ctrip.framework.apollo.portal.repository.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProtalUserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<User> findAll() {
        Iterable<User>  users = userRepository.findAll();
        if (users == null) {
            return Collections.emptyList();
        }
        return Lists.newArrayList((users));
    }

    public List<User> findAllByUserNames(List<String> names) {
        List<User> users= userRepository.findByUserNameIn(names);
        return users;
    }

}
