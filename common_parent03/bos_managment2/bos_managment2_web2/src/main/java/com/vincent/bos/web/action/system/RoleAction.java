package com.vincent.bos.web.action.system;

import com.vincent.bos.domain.system.Permission;
import com.vincent.bos.domain.system.Role;
import com.vincent.bos.service.system.RoleService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 21:34
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

 @Autowired
 private RoleService roleService;

 public RoleAction() {
  super(Role.class);
 }

 /**
  * 分页查询
  *
  * @return
  * @throws IOException
  */
 @Action(value = "roleAction_pageQuery")
 public String pageQuery() throws IOException {

  Pageable pageable = new PageRequest(page - 1, rows);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"users", "permissions", "menus"});
  Page<Role> pageBean = roleService.findAll(pageable);

  pageBeanToJson(pageBean, jsonConfig);

  return NONE;
 }
 // 使用属性驱动获取菜单和权限的ID
 private String menuIds;

 public void setMenuIds(String menuIds) {
  this.menuIds = menuIds;
 }

 private Long[] permissionIds;

 public void setPermissionIds(Long[] permissionIds) {
  this.permissionIds = permissionIds;
 }

 /**
  * 保存角色
  *
  * @return
  */
 @Action(value = "roleAction_save", results = {@Result(name = "success", location = "pages/system/role.html", type = "redirect")})
 public String save() {

  roleService.save(getModel(),menuIds,permissionIds);
  return SUCCESS;
 }

 @Action(value = "roleAction_findAll")
 public String findAll() throws IOException {

  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"users", "permissions", "menus"});
  Page<Role> pageBean = roleService.findAll(null);
  List<Role> list = pageBean.getContent();

  listToJson(list, jsonConfig);
  return NONE;
 }



}
