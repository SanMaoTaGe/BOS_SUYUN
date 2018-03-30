package com.vincent.bos.web.action.system;

import com.vincent.bos.domain.system.Permission;
import com.vincent.bos.service.system.PermissionService;
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
 * @create 2018-03-28 21:06
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission> {

 @Autowired
 private PermissionService permissionService;

 public PermissionAction() {
  super(Permission.class);
 }

 @Action(value = "permissionAction_pageQuery")
 public String pageQuery() throws IOException {

  Pageable pageable = new PageRequest(page - 1, rows);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"roles"});
  Page<Permission> pageBean = permissionService.findAll(pageable);

  pageBeanToJson(pageBean, jsonConfig);

  return NONE;
 }

 /**
  * 保存权限
  *
  * @return
  */
 @Action(value = "permissionAction_save", results = {@Result(name = "success", location = "pages/system/permission.html", type = "redirect")})
 public String save() {

  permissionService.save(getModel());
  return SUCCESS;
 }

 /**
  * 角色增加中,提供权限数据供选择
  * @return
  * @throws IOException
  */
 @Action(value = "permissionAction_findAll")
 public String findAll() throws IOException {

  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"roles"});
  Page<Permission> pageBean = permissionService.findAll(null);
  List<Permission> list = pageBean.getContent();

  listToJson(list, jsonConfig);
  return NONE;
 }


}
