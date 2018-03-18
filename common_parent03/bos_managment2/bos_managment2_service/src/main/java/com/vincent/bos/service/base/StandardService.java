package com.vincent.bos.service.base;

import com.vincent.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author Vincent
 * @Description:
 * @create 2018-03-14 15:30
 */
public interface StandardService {

 void save(Standard standard);

 Page<Standard> findAll(Pageable pageable);
}
