package com.vincent.bos.service.system.Impl;

import com.vincent.bos.dao.system.MenuRepository;
import com.vincent.bos.domain.system.Menu;
import com.vincent.bos.domain.system.User;
import com.vincent.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 16:54
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

 @Autowired
 private MenuRepository menuRepository;

 @Override
 public List<Menu> findLevelOne() {
  return menuRepository.findByParentMenuIsNull();
 }

 @Override
 public void save(Menu model) {
  // 导致异常的原因是parentMenu字段是一个瞬时态的对象
  // 判断用户是否是要添加一个一级菜单
  Menu parentMenu = model.getParentMenu();
  if (model.getParentMenu() != null && model.getParentMenu().getId() == null) {
   model.setParentMenu(null);
  }
  System.out.println("------ab--service");
  menuRepository.save(model);
 }

 @Override
 public Page<Menu> findAll(Pageable pageable) {
  return menuRepository.findAll(pageable);
 }

 @Override
 public List<Menu> findByUser(User user) {
  if (user.getUsername().equals("admin")) {

   return menuRepository.findAll();
  }


  return menuRepository.findbyUser(user.getId());
 }
}
