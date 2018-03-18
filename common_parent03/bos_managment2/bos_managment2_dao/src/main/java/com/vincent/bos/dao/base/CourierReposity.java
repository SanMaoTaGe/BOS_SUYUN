package com.vincent.bos.dao.base;

import com.vincent.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-15 16:29
 */

public interface CourierReposity extends JpaRepository<Courier,Long>,JpaSpecificationExecutor<Courier>{
 @Modifying
 @Query("update Courier set deltag = 1 where id = ?")
 void updateDelTagById(Long id);

}
