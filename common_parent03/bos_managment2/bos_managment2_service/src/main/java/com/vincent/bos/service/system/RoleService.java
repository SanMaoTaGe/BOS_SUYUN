package com.vincent.bos.service.system;

import com.vincent.bos.domain.system.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 21:00
 */
public interface RoleService {

 Page<Role> findAll(Pageable pageable);

 void save(Role model, String menuIds, Long[] permissionIds);
}
