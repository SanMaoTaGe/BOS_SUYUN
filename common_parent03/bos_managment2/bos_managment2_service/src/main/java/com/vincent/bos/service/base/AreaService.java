package com.vincent.bos.service.base;

import com.vincent.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList; /**
 * @author Vincent
 * @Description:
 * @create 2018-03-16 17:03
 */
public interface AreaService {
 void saveArea(ArrayList<Area> list);

 Page<Area> findAll(Pageable pageable);
}