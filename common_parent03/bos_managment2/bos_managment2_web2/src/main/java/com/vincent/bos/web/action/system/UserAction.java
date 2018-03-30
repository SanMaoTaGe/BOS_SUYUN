package com.vincent.bos.web.action.system;

import com.vincent.bos.domain.system.Menu;
import com.vincent.bos.domain.system.User;
import com.vincent.bos.service.system.UserService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
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

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-26 13:22
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends CommonAction<User> {

 @Autowired
 private UserService userService;

 private String code;

 public void setCode(String code) {
  this.code = code;
 }

 public UserAction() {
  super(User.class);
 }

 @Action(value = "userAction_login", results = {
  @Result(name = "success", location = "index.html", type = "redirect"),
  @Result(name = "login", location = "/login.html", type = "redirect")})
 public String login() {
  System.out.println("------------UserAction------------------login");
  //获取验证码
  String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");

  if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(serverCode) && serverCode.equals(code)) {
// 主体,代表当前用户
   Subject subject = SecurityUtils.getSubject();
   // 使用代码校验权限
   // subject.checkPermission("");
// 创建用户名密码令牌
   UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());

   //执行登录
   try {
    subject.login(usernamePasswordToken);

    User user = (User) subject.getPrincipal();

    ServletActionContext.getRequest().getSession().setAttribute("user", user);

    return SUCCESS;
   } catch (UnknownAccountException e) {
    e.printStackTrace();
    System.out.println("用户名写错了");
   } catch (IncorrectCredentialsException e) {
    System.out.println("密码错误");
    e.printStackTrace();

   } catch (Exception e) {
    e.printStackTrace();
    System.out.println("其他错误");
   }
  }

  return LOGIN;
 }

 /**
  * Logout 方法
  *
  * @return
  */
 @Action(value = "userAction_logout", results = {
  @Result(name = "success", location = "login.html", type = "redirect")})
 public String logout() {

  //注销
  SecurityUtils.getSubject().logout();

  //清空session
  ServletActionContext.getRequest().getSession().removeAttribute("user");
  return SUCCESS;
 }

 @Action(value = "userAction_pageQuery")
 public String pageQuery() throws IOException {

  Pageable pageable = new PageRequest(page - 1, rows);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"roles"});
  Page<User> pageBean = userService.findAll(pageable);

  pageBeanToJson(pageBean, jsonConfig);

  return NONE;
 }

 private Long[] roleIds;

 public void setRoleIds(Long[] roleIds) {
  this.roleIds = roleIds;
 }

 @Action(value = "userAction_save", results = {@Result(name = "success", location = "pages/system/userlist.html", type = "redirect")})
 public String save() {

  userService.save(getModel(),roleIds);
  return SUCCESS;
 }

}
