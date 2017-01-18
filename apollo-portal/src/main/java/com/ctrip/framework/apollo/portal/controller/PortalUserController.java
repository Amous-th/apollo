package com.ctrip.framework.apollo.portal.controller;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.entity.po.User;
import com.ctrip.framework.apollo.portal.service.ProtalUserService;
import com.ctrip.framework.apollo.portal.spi.sagreen.SagreenUserConfig;
import com.ctrip.framework.apollo.portal.util.AESCryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@RestController
public class PortalUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalUserController.class);

    @Autowired
    private SagreenUserConfig sagreenUserConfig;

    @Autowired
    private ProtalUserService protalUserService;

    @RequestMapping(value = "/portal/login", method = RequestMethod.POST)
    public void find(HttpServletRequest httpRequest,
                     HttpServletResponse httpResponse,
                     @RequestParam("username") String userName,
                     @RequestParam("password") String password) throws ServletException, IOException {


        if(StringUtils.isBlank(userName)){
            LOGGER.error("用户名为空");
            return;
        }
        User user =  protalUserService.findByUserName(userName);
        if(null == user){
            LOGGER.error("无此用户");
            return;
        }

        String pass = user.getPassword();
        if (!StringUtils.isBlank(pass) && pass.equals(password)) {
            String decryptedSysUser = AESCryptUtils.encrypt(sagreenUserConfig.getKey(), sagreenUserConfig
                    .getIvParameter(), userName + "|" + password);
            Cookie sys_user = new Cookie("sys_user", decryptedSysUser);
            sys_user.setHttpOnly(false);
            sys_user.setMaxAge((int) TimeUnit.MINUTES.toSeconds(24 * 60));
            sys_user.setPath("/");
            httpResponse.addCookie(sys_user);
            httpResponse.sendRedirect("/");
        } else {
            LOGGER.error("错误的用户名密码，用户名={},密码={}", userName, password);
        }

    }

}
