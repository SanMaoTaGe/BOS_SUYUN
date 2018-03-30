package com.vincent.bos.dao.system;

import com.vincent.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-26 15:58
 */
public interface UserRepository extends JpaRepository<User,Long>{

 User findByUsername(String username);
}
