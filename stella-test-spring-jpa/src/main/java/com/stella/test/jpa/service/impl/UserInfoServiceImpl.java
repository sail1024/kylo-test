package com.stella.test.jpa.service.impl;

import com.stella.test.jpa.dao.UserInfoDao;
import com.stella.test.jpa.entity.UserInfo;
import com.stella.test.jpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public UserInfo createNewUserInfo(UserInfo userInfo) {
        userInfo.setUserId(String.valueOf(System.currentTimeMillis()));
        return userInfoDao.save(userInfo);
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteUserInfo(String userId) {

        userInfoDao.deleteByUserId(userId);
    }

    @Override
    public UserInfo getUserInfo(String userId) {

        return userInfoDao.getUserInfoByUserId(userId);
    }

    @Override
    public List<UserInfo> listUserInfo() {
        return userInfoDao.findAll(Sort.by("userId"));
    }
}
