package com.stella.test.jpa.service;

import com.stella.test.jpa.entity.UserInfo;

import java.util.List;

/**
 * user info service.
 *
 * @author sail
 * @date 14:19 2019-11-12.
 * @since 1.0
 */
public interface UserInfoService {
    /**
     * create new user info
     * @param userInfo
     * @return
     */
    UserInfo createNewUserInfo(UserInfo userInfo);

    /**
     * delete user info
     * @param userId
     */
    void deleteUserInfo(String userId);

    /**
     * get user info
     * @param userId
     * @return
     */
    UserInfo getUserInfo(String userId);

    /**
     * list
     * @return
     */
    List<UserInfo> listUserInfo();
}
