package com.vincent.bos.service.base.impl;

import com.vincent.bos.dao.TakeTimeRepository;
import com.vincent.bos.domain.base.TakeTime;
import com.vincent.bos.service.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-22 11:07
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
 @Autowired
 TakeTimeRepository takeTimeRepository;

 @Override
 public List<TakeTime> findAll() {
  return takeTimeRepository.findAll();
 }
}
