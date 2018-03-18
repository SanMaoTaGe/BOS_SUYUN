package com.vincent.bos.service.base;

import com.vincent.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-15 16:24
 */

public interface CourierService {
 void save(Courier courier);

 Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);


 void delete(String ids);
}
