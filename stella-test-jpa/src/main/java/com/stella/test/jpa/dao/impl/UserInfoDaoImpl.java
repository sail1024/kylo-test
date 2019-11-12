package com.stella.test.jpa.dao.impl;

import com.stella.test.jpa.PersistenceManager;
import com.stella.test.jpa.dao.UserInfoDao;
import com.stella.test.jpa.entity.UserInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * user info dao impl.
 *
 * @author sail
 * @date 14:28 2019-11-12.
 * @since 1.0
 */
@Service
public class UserInfoDaoImpl implements UserInfoDao {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoDaoImpl.class);

    @Autowired
    private PersistenceManager persist;

    @Override
    public UserInfoEntity save(UserInfoEntity userInfoEntity) {
        EntityManager entityManager = persist.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(userInfoEntity);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            logger.error("persist user info error", e);

            entityManager.getTransaction().rollback();
        }finally {

            entityManager.close();
        }

        return userInfoEntity;
    }

    @Override
    public void delete(UserInfoEntity userInfoEntity) {
        persist.doWork(entityManager -> {
            CriteriaDelete<UserInfoEntity> criteriaDelete = entityManager.getCriteriaBuilder().createCriteriaDelete(UserInfoEntity.class);

            Root<UserInfoEntity> from = criteriaDelete.from(UserInfoEntity.class);

            criteriaDelete.where(from.get("userId").in(userInfoEntity.getUserId()));

            int ret = entityManager.createQuery(criteriaDelete).executeUpdate();

            if (ret == 1){
                logger.info("delete user {} success", userInfoEntity.getUserId());
            }
        });
    }

    @Override
    public UserInfoEntity get(UserInfoEntity userInfoEntity) {
        UserInfoEntity ret = persist.doWorkCallable(entityManager -> {
            CriteriaQuery<UserInfoEntity> query = entityManager.getCriteriaBuilder().createQuery(UserInfoEntity.class);

            Root<UserInfoEntity> from = query.from(UserInfoEntity.class);

            query
                    .select(from)
                    .where(from.get("userId").in(userInfoEntity.getUserId()));

            return entityManager.createQuery(query).getSingleResult();
        });
        return ret;
    }
}
