package com.vincent.bos.realms;

import com.vincent.bos.dao.system.PermissonRepository;
import com.vincent.bos.dao.system.RoleRepository;
import com.vincent.bos.dao.system.UserRepository;
import com.vincent.bos.domain.system.Permission;
import com.vincent.bos.domain.system.Role;
import com.vincent.bos.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-26 15:37AuthorizingRealm
 */
@Component
public class UserRealm extends AuthorizingRealm {

 @Autowired
 private UserRepository userRepository;
 @Autowired
 private RoleRepository roleRepository;
 @Autowired
 private PermissonRepository permissonRepository;


 // 授权的方法

 // 每一次访问需要权限的资源的时候,都会调用授权的方法
 @Override
 protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
  SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
  // 授权
  User user = (User) SecurityUtils.getSubject().getPrincipal();
  if ("admin".equals(user.getUsername())) {

   List<Role> roles = roleRepository.findAll();
   List<Permission> permisssions = permissonRepository.findAll();

   for (Role r : roles) {
    info.addRole(r.getKeyword());
   }

   for (Permission p : permisssions) {
    info.addStringPermission(p.getKeyword());/**/
   }

  } else {

   List<Role> listR = roleRepository.findbyUid(user.getId());
   List<Permission> listP = permissonRepository.findbyUid(user.getId());

   for (Role r : listR) {
    info.addRole(r.getKeyword());
   }

   for (Permission p : listP) {
    info.addStringPermission(p.getKeyword());
   }
  }

  //授予角色
  return info;
 }
 // 认证的方法(登录.....)

 // 参数中的token就是subject.login(token)方法中传入的参数
 //消费者
 @Override
 protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
  UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
  // // 根据用户名查找用户
  //先获得用户名
  String username = usernamePasswordToken.getUsername();

  User user = userRepository.findByUsername(username);

  if (user != null) {

   /**
    * @param principal 当事人,主体.通常是从数据库中查询到的用户
    * @param credentials 凭证,密码.是从数据库中查询出来的密码
    * @param realmName
    */
   AuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
   // 比对成功 -> 执行后续的逻辑
   // 比对失败 -> 抛异常
   return simpleAuthenticationInfo;
  }
  // 找不到 -> 抛异常
  return null;
 }
}
