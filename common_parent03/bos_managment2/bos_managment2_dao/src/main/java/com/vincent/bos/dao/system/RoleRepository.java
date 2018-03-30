package com.vincent.bos.dao.system;

import com.vincent.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 20:59
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

 @Query("select r from Role r inner join r.users u where u.id = ?")
 List<Role> findbyUid(Long id);
}
