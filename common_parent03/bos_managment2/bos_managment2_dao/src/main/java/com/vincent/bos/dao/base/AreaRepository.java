package com.vincent.bos.dao.base;

import com.vincent.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-16 17:06
 */
public  interface AreaRepository extends JpaRepository<Area,Long>{

 @Query(value = "from Area where province like ?1 or  city like ?1  or  district like ?1  or  postcode like ?1  or  citycode like ?1  or  shortcode like ?1")
 List<Area> findQ(String q);

 Area findByProvinceAndCityAndDistrict(String province, String city, String district);

 @Query("select a.province,count(*) from Area  a group by a.province")
 List<Object[]> exportCharts();
}
