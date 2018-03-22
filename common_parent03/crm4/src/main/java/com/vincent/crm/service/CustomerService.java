package com.vincent.crm.service;

import com.vincent.crm.domain.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-19 14:37
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public interface CustomerService {
 @GET
 @Path("/findAll")
 List<Customer> findAll();

 @Path("/findCustomersUnAssociated")
 @GET
  //该方法没有指定定区的id,是因为SpringJPA定义了一个xxIsNull的查询,可以直接利用
 List<Customer> findCustomersUnAssociated();

 @Path("/findCustomersAssociated")
 @GET
 List<Customer> findCustomersAssociated(@QueryParam("fixedAreaId")String fixedAreaId);

 @Path("/assignCustomersToFixedArea")
 @PUT
 void assignCustomersToFixedArea(@QueryParam("fixedAreaId") String fixedAreaId,
                                 @QueryParam("customerIds") Long[] customerIds);




}
