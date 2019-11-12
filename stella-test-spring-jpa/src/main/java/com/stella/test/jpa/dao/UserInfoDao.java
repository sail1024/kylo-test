package com.stella.test.jpa.dao;

import com.stella.test.jpa.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * user info dao.
 *
 * @author sail
 * @date 14:22 2019-11-12.
 * @since 1.0
 */
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {

    @Query("select u from UserInfo u where u.userId = :userId")
    UserInfo getUserInfoByUserId(@Param("userId") String userId);

    void deleteByUserId(String userId);
}
