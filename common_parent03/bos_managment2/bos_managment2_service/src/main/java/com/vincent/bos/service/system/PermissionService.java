package com.vincent.bos.service.system;

import com.vincent.bos.domain.system.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 21:01
 */
public interface PermissionService {

 Page<Permission> findAll(Pageable pageable);

 void save(Permission model);
}
