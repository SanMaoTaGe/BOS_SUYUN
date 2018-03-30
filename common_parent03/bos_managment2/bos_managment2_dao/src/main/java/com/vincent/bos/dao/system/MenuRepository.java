package com.vincent.bos.dao.system;

import com.vincent.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 16:50
 */
public interface MenuRepository extends JpaRepository<Menu,Long> {
 List<Menu> findByParentMenuIsNull();

 @Query("SELECT m FROM Menu m inner join m.roles mr inner join mr.users ur where ur.id=? ")
 List<Menu> findbyUser(Long id);

 //@Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
}
