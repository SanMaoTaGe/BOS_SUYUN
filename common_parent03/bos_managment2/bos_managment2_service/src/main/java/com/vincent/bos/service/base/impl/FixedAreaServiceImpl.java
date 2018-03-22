package com.vincent.bos.service.base.impl;

import com.vincent.bos.dao.TakeTimeRepository;
import com.vincent.bos.dao.base.CourierReposity;
import com.vincent.bos.dao.base.FixedAreaRepository;
import com.vincent.bos.dao.base.SubAreaRepository;
import com.vincent.bos.domain.base.Courier;
import com.vincent.bos.domain.base.FixedArea;
import com.vincent.bos.domain.base.SubArea;
import com.vincent.bos.domain.base.TakeTime;
import com.vincent.bos.service.base.FixedAreaService;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-21 16:02
 */
@Service
@Transactional
public class FixedAreaServiceImpl  implements FixedAreaService{

 @Autowired
 private FixedAreaRepository fixedAreaRepository;
 @Autowired
 private CourierReposity courierReposity;
 @Autowired
 private TakeTimeRepository takeTimeRepository;
 @Autowired
 private SubAreaRepository subAreaRepository;

 @Override
 public void save(FixedArea model) {
  fixedAreaRepository.save(model);
 }

 @Override
 public Page<FixedArea> findAll(Pageable pageable, JsonConfig jsonConfig) {
  return fixedAreaRepository.findAll(pageable);
 }

 @Override
 public void associationCourierToFixedArea(Long courierId, Long takeTimeId, Long fixedAreaId) {
  //找到三个家伙
  // 持久态的对象
  FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
  Courier courier = courierReposity.findOne(courierId);
  TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);

  //bind
  courier.setTakeTime(takeTime);
  fixedArea.getCouriers().add(courier);

 }

 @Override
 public void assignSubAreasToFixedArea(Long fixedAreaId ,Long[] subAreaIds) {

  //unbind
  FixedArea fixedArea1 = fixedAreaRepository.findOne(fixedAreaId);
  Set<SubArea> subareas = fixedArea1.getSubareas();
  for (SubArea s:subareas
       ) {
   s.setFixedArea(null);
  }


  //bind

  for (Long s: subAreaIds
       ) {
   SubArea subArea = subAreaRepository.findOne(s);
   subArea.setFixedArea(fixedArea1);
  }
 }


}
