package com.vincent.bos.dao.takeDelivery;

import com.vincent.bos.domain.take_divery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-24 13:03
 */
public interface OrderRepository extends JpaRepository<Order,Long>{
}
