package com.stella.test.jpa.dao;

import com.stella.test.jpa.entity.UserInfoEntity;

/**
 * user info dao.
 *
 * @author sail
 * @date 14:22 2019-11-12.
 * @since 1.0
 */
public interface UserInfoDao {
    /**
     * save user info entity.
     * @param userInfoEntity
     * @return
     */
    UserInfoEntity save(UserInfoEntity userInfoEntity);

    /**
     * delete
     * @param userInfoEntity
     */
    void delete(UserInfoEntity userInfoEntity);

    /**
     * get
     * @param userInfoEntity
     * @return
     */
    UserInfoEntity get(UserInfoEntity userInfoEntity);
}
