package com.vincent.bos.dao.system;

import com.vincent.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 20:58
 */
public interface PermissonRepository extends JpaRepository<Permission, Long> {
 @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
 List<Permission> findbyUid(Long id);
}
