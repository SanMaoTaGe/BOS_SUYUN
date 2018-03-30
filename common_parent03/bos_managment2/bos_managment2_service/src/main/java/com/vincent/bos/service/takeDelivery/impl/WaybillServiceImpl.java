package com.vincent.bos.service.takeDelivery.impl;

import com.vincent.bos.dao.takeDelivery.WaybillRepository;
import com.vincent.bos.domain.take_divery.WayBill;
import com.vincent.bos.service.takeDelivery.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-25 17:50
 */
@Service
@Transactional
public class WaybillServiceImpl implements WaybillService {

 @Autowired
 private WaybillRepository waybillRepository;

 @Override
 public void save(WayBill model) {
  waybillRepository.save(model);
 }
}
