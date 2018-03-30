package com.vincent.bos.service.system;

import com.vincent.bos.domain.system.Menu;
import com.vincent.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 16:54
 */
public interface MenuService {
 List<Menu> findLevelOne();

 void save(Menu model);

 Page<Menu> findAll(Pageable pageable);

 List<Menu> findByUser(User user);
}
