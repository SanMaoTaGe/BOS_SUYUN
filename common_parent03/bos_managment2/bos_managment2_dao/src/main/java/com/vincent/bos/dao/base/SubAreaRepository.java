package com.vincent.bos.dao.base;

import com.vincent.bos.domain.base.FixedArea;
import com.vincent.bos.domain.base.Standard;
import com.vincent.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-18 19:04
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long> {

 List<SubArea> findByFixedAreaIsNull();

 List<SubArea> findByFixedArea(FixedArea fixedArea);
//这个也可以
 /*@Query("from SubArea where fixedArea.id=?")
 List<SubArea> findbbb(Long fixedAreaId);*/
}
