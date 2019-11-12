package com.stella.test.jpa.service.impl;

import com.stella.test.jpa.dao.UserInfoDao;
import com.stella.test.jpa.entity.UserInfoEntity;
import com.stella.test.jpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * user info service impl.
 *
 * @author sail
 * @date 14:21 2019-11-12.
 * @since 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;



    @Override
    public UserInfoEntity createNewUserInfo(UserInfoEntity userInfoEntity) {
        userInfoEntity.setUserId(String.valueOf(System.currentTimeMillis()));
        return userInfoDao.save(userInfoEntity);
    }


    @Override
    public void deleteUserInfo(String userId) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(userId);

        userInfoDao.delete(userInfoEntity);
    }

    @Override
    public UserInfoEntity getUserInfo(String userId) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(userId);

        return userInfoDao.get(userInfoEntity);
    }
}
