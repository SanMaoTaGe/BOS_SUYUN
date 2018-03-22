package com.vincent.bos.service.base.impl;

import com.vincent.bos.dao.base.SubAreaRepository;
import com.vincent.bos.domain.base.FixedArea;
import com.vincent.bos.domain.base.SubArea;
import com.vincent.bos.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-18 19:06
 */
@Transactional
@Service
public class SubAreaServiceImpl implements SubAreaService{

 @Autowired
 private SubAreaRepository subAreaRepository;


 @Override
 public void save(SubArea model) {
  subAreaRepository.save(model);
 }

 @Override
 public Page<SubArea> findAll(Pageable pageable) {
  return subAreaRepository.findAll(pageable);
 }

 @Override
 public List<SubArea> findSubAreaUnassociatedWithFixedArea() {
  List<SubArea> list = subAreaRepository.findByFixedAreaIsNull();

  return list;

 }

 @Override
 public List<SubArea> findSubAreaAssociatedWithFixedArea(Long fixedAreaId) {

  FixedArea fixedArea  = new FixedArea();
  fixedArea.setId(fixedAreaId);
 List<SubArea> list = subAreaRepository.findByFixedArea(fixedArea);
 // List<SubArea> list = subAreaRepository.findbbb(fixedAreaId);
  System.out.println("-----------------------------------------------------------\n\n"+list.toString());

  return list;
 }
}
