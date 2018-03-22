package com.vincent.bos.dao;

import com.vincent.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-22 11:08
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime,Long>{
}
