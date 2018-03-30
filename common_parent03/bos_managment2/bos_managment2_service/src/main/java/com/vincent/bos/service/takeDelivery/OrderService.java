package com.vincent.bos.service.takeDelivery;

import com.vincent.bos.domain.take_divery.Order;
import org.aspectj.weaver.ast.Or;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-24 13:00
 */

@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public interface OrderService {


 @Path(value = "/save")
 @POST
 void save(Order order);

 @Path("/findAll")
 @GET
 List<Order> findAll();
}
