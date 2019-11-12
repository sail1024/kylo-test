package com.stella.test.jpa.rest;

import com.stella.test.jpa.entity.UserInfoEntity;
import com.stella.test.jpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user info controller.
 *
 * @author sail
 * @date 15:03 2019-11-12.
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/add")
    public UserInfoEntity createUser(UserInfoEntity userInfoEntity){
        return userInfoService.createNewUserInfo(userInfoEntity);
    }

    @GetMapping("/get")
    public UserInfoEntity getUser(String userId){
        return userInfoService.getUserInfo(userId);
    }

    @GetMapping("/delete")
    public String deleteUser(String userId){
        userInfoService.deleteUserInfo(userId);
        return "ok";
    }
}
