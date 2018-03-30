package com.vincent.bos.service.base.impl;

import com.vincent.bos.dao.base.CourierReposity;
import com.vincent.bos.domain.base.Courier;
import com.vincent.bos.service.base.CourierService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-15 16:26
 */
@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService{
@Autowired
 private CourierReposity courierReposity;


 @Override
 public void save(Courier courier) {
  courierReposity.save(courier);
 }

 @Override
 public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
  return courierReposity.findAll(specification, pageable);
 }
 // 批量删除

 // 在调用方法时,框架就会检查当前用户是否有对应的权限,如果有就放行,没有就抛异常
 @Override
 public void delete(String ids) {

  // 真实开发中只有逻辑删除
  // null " "
  // 判断数据是否为空
  if (StringUtils.isNotEmpty(ids)) {
   // 切割数据
   String[] split = ids.split(",");
   for (String id : split) {
    courierReposity.updateDelTagById(Long.parseLong(id));
    System.out.println("service ID------------------------------------------------\n"+id+"\n");
   }
  }

 }
}
