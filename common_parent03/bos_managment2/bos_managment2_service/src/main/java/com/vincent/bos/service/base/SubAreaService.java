package com.vincent.bos.service.base;

import com.vincent.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-18 19:05
 */
public interface SubAreaService {
 void save(SubArea model);

 Page<SubArea> findAll(Pageable pageable);

 List<SubArea> findSubAreaUnassociatedWithFixedArea();

 List<SubArea> findSubAreaAssociatedWithFixedArea(Long fixedAreaId);
}
