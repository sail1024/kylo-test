package com.stella.test.jpa.service;

import com.stella.test.jpa.entity.UserInfoEntity;

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
     * @param userInfoEntity
     * @return
     */
    UserInfoEntity createNewUserInfo(UserInfoEntity userInfoEntity);

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
    UserInfoEntity getUserInfo(String userId);
}
