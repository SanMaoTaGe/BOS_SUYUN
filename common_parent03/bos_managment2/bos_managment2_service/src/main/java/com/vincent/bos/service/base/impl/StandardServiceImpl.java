package com.vincent.bos.service.base.impl;

import com.vincent.bos.dao.base.StandardReposity;
import com.vincent.bos.domain.base.Standard;
import com.vincent.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-14 15:30
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {
 @Autowired
 private StandardReposity standardReposity;
 @Override
 public void save(Standard standard) {
   standardReposity.save(standard);
 }

 @Override
 public Page<Standard> findAll(Pageable pageable) {

  return standardReposity.findAll(pageable);
 }
}
