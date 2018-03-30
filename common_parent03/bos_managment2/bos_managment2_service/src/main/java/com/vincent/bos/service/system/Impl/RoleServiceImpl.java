package com.vincent.bos.service.system.Impl;

import com.vincent.bos.dao.system.RoleRepository;
import com.vincent.bos.domain.system.Menu;
import com.vincent.bos.domain.system.Permission;
import com.vincent.bos.domain.system.Role;
import com.vincent.bos.service.system.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 21:02
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

 @Autowired
 private RoleRepository roleRepository;

 @Override
 public Page<Role> findAll(Pageable pageable) {
  return roleRepository.findAll(pageable);
 }

 @Override
 public void save(Role model, String menuIds, Long[] permissionIds) {
  //关联Role-->权限-->菜单

  if (StringUtils.isNotEmpty(menuIds) && model != null) {

   String[] split = menuIds.split(",");
   for (String s : split) {
    Menu menu = new Menu();
    menu.setId(Long.parseLong(s));
    model.getMenus().add(menu);
   }
  }
  if (permissionIds != null && permissionIds.length > 0) {

   for (Long p : permissionIds) {
    Permission permission = new Permission();
    permission.setId(p);
    model.getPermissions().add(permission);
   }
  }

  roleRepository.save(model);
 }
}
