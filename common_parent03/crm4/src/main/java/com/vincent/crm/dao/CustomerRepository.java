package com.vincent.crm.dao;

import com.vincent.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-19 14:52
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
 List<Customer> findByFixedAreaIdIsNull();

 List<Customer> findByFixedAreaId(String fixedAreaId);


 @Query("update Customer set fixedAreaId=null where fixedAreaId=?")
 @Modifying
 void unbindCustomer(String fixedAreaId);

 @Query("update Customer set fixedAreaId=? where id=?")
 @Modifying
 void bindCustomer(String fixedAreaId, Long customerId);

 @Query("update Customer set type = 1 where telephone = ?")
 @Modifying
 void active(String telephone);


 Customer findByTelephoneAndPassword(String telephone, String password);

 Customer findByTelephone(String telephone);
}
