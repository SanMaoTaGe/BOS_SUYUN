package com.vincent.bos.web.action.system;

import com.vincent.bos.domain.system.Menu;
import com.vincent.bos.domain.system.User;
import com.vincent.bos.service.system.MenuService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import java.security.Security;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 16:55
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends CommonAction<Menu> {
 @Autowired
 private MenuService menuService;

 public MenuAction() {
  super(Menu.class);
 }


 /**
  * add页面中父菜单项的数据
  *
  * @return
  * @throws IOException
  */
 @Action(value = "menuAction_findLevelOne")
 public String findLevelOne() throws IOException {

  List<Menu> list = menuService.findLevelOne();//TODO
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"roles", "childrenMenus", "parentMenu"});
  listToJson(list, jsonConfig);
  return NONE;
 }

 /**
  * 保存菜单
  *
  * @return
  */
 @Action(value = "menuAction_save", results = {@Result(name = "success", location = "pages/system/menu.html", type = "redirect")})
 public String save() {

  menuService.save(getModel());
  return SUCCESS;
 }

 /**
  *分页查询
  * @return
  * @throws IOException
  */
 @Action(value = "menuAction_pageQuery")
 public String pageQuery() throws IOException {
  System.out.println("----------addd-------------pageuery");
  Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage()) - 1, rows);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"roles", "childrenMenus", "parentMenu"});
  Page<Menu> pageBean = menuService.findAll(pageable);

  pageBeanToJson(pageBean, jsonConfig);

  return NONE;
 }

 @Action("menuAction_findbyUser")
 public String findByUser() throws IOException {
 //获取Uid
  User user = (User) SecurityUtils.getSubject().getPrincipal();

  List<Menu> list=menuService.findByUser(user);

  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"roles", "childrenMenus", "parentMenu","children"});

  listToJson(list,jsonConfig);


  return NONE;
 }


}
