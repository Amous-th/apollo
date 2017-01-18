package com.ctrip.framework.apollo.portal.service;

import com.ctrip.framework.apollo.portal.entity.po.User;
import com.ctrip.framework.apollo.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtalUserService {

  @Autowired
  private UserRepository userRepository;

  public User findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }



}
