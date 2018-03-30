package com.vincent.bos.service.system.Impl;

import com.vincent.bos.dao.system.PermissonRepository;
import com.vincent.bos.domain.system.Permission;
import com.vincent.bos.service.system.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-28 21:01
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
 @Autowired
 private PermissonRepository permissonRepository;

 @Override
 public Page<Permission> findAll(Pageable pageable) {
  return permissonRepository.findAll(pageable);
 }

 @Override
 public void save(Permission model) {
  permissonRepository.save(model);
 }
}
