package com.vincent.crm.service.impl;

import com.vincent.crm.dao.CustomerRepository;
import com.vincent.crm.domain.Customer;
import com.vincent.crm.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-19 14:38
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

 @Autowired
 private CustomerRepository customerRepository;

 @Override
 public List<Customer> findAll() {

  return customerRepository.findAll();
 }

 @Override
 public List<Customer> findCustomersUnAssociated() {
  List<Customer> list= customerRepository.findByFixedAreaIdIsNull();
  return list;
 }

 @Override
 public List<Customer> findCustomersAssociated(String fixedAreaId) {

  List<Customer> list=  customerRepository.findByFixedAreaId(fixedAreaId);
  return list;
 }

 @Override
 public void assignCustomersToFixedArea(String fixedAreaId, Long[] customerIds) {

  if(StringUtils.isNotEmpty(fixedAreaId)){

   customerRepository.unbindCustomer(fixedAreaId);

  }

  if(customerIds != null && customerIds.length > 0){
   for (Long id:customerIds
        ) {

    customerRepository.bindCustomer(fixedAreaId,id);
   }


  }

 }

 @Override
 public void save(Customer customer) {
  System.out.println("save customer------------------\n"+customer);
  customerRepository.save(customer);
 }

 @Override
 public void active(String telephone) {
  System.out.println("active-------------customerServiceImpl-----telephone---\n"+telephone);

  customerRepository.active(telephone);
 }

 @Override
 public Customer isActived(String telephone) {
  return customerRepository.findByTelephone(telephone);
 }

 @Override
 public Customer login(String telephone, String password) {
  return customerRepository.findByTelephoneAndPassword(telephone,password);
 }


}
