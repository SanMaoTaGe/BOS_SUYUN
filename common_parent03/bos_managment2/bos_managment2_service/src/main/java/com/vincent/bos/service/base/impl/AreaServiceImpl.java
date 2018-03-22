package com.vincent.bos.service.base.impl;

import com.vincent.bos.dao.base.AreaRepository;
import com.vincent.bos.domain.base.Area;
import com.vincent.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-16 17:03
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

 @Autowired
 private AreaRepository areaRepository;
 @Override
 public void saveArea(ArrayList<Area> list) {
  areaRepository.save(list);
 }

 @Override
 public Page<Area> findAll(Pageable pageable) {

  return areaRepository.findAll(pageable);
 }

 @Override
 public List<Area> findQ(String q) {
 q="%"+q.toUpperCase()+"%";
  return areaRepository.findQ(q);
 }


}
