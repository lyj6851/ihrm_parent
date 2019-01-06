package com.ihrm.system.dao;

import com.ihrm.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * <p>user dao</p>
 *
 * @author xiaodongsun
 * @date 2019/1/6
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
