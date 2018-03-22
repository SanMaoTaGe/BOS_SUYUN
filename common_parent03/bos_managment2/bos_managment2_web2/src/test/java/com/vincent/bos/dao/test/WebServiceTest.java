package com.vincent.bos.dao.test;

import com.vincent.bos.domain.base.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-19 17:46
 */
public class WebServiceTest {

 @Test
 public void fun01() {
  Collection<? extends Customer> cc= WebClient
   .create("http://localhost:8180/webService/customerService/findAll")
   .type(MediaType.APPLICATION_JSON)
   .accept(MediaType.APPLICATION_JSON)
   .getCollection(Customer.class);
  System.out.println("---------------cc\n"+cc);
 }
}
