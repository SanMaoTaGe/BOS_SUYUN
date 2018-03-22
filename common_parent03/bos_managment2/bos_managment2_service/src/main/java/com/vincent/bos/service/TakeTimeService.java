package com.vincent.bos.service;

import com.vincent.bos.dao.TakeTimeRepository;
import com.vincent.bos.domain.base.TakeTime;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-22 11:07
 */
public interface TakeTimeService {

 List<TakeTime> findAll();
}
