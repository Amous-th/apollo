package com.ctrip.framework.apollo.portal.repository;

import com.ctrip.framework.apollo.portal.entity.po.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    /**
     * find user by user name
     */
    User findByUserName(String userName);

    List<User> findByUserNameIn(List<String> userNames);

}
