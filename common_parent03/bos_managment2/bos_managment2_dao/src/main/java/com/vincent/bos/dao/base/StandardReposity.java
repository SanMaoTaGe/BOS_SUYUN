package com.vincent.bos.dao.base;

import com.vincent.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-12 20:16
 */


@SuppressWarnings("all")
public interface StandardReposity extends JpaRepository<Standard, Long> {


}
