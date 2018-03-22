package com.vincent.bos.service.base;

import com.vincent.bos.domain.base.FixedArea;
import net.sf.json.JsonConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author Vincent
 * @Description:
 * @create 2018-03-21 16:01
 */
public interface  FixedAreaService {

 void save(FixedArea model);

 Page<FixedArea> findAll(Pageable pageable, JsonConfig jsonConfig);

 void associationCourierToFixedArea(Long courierId, Long takeTimeId, Long fixedAreaId);


 void assignSubAreasToFixedArea(Long id, Long[] subAreaIds);
}
