package com.vincent.bos.service.system.Impl;

import com.vincent.bos.dao.system.UserRepository;
import com.vincent.bos.domain.system.Role;
import com.vincent.bos.domain.system.User;
import com.vincent.bos.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-29 16:32
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

 @Autowired
 UserRepository userRepository;

 @Override
 public Page<User> findAll(Pageable pageable) {
  return userRepository.findAll(pageable);
 }

 @Override
 public void save(User user, Long[] roleIds) {
  userRepository.save(user);
  // 建立用户和角色的关联时,只能使用用户关联角色,不能使用角色关联用户
  // 因为在角色中使用了mappedBy属性
  if (roleIds != null && roleIds.length > 0) {
   for (Long roleId : roleIds) {
    Role role = new Role();
    role.setId(roleId);

    user.getRoles().add(role);
   }
  }

 }
}
