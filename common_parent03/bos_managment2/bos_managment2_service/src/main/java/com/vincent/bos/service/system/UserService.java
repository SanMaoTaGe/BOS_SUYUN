package com.vincent.bos.service.system;

import com.vincent.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author Vincent
 * @Description:
 * @create 2018-03-29 16:31
 */
public interface UserService {

 Page<User> findAll(Pageable pageable);

 void save(User model, Long[] roleIds);
}
