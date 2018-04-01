package com.vincent.bos.service.takeDelivery;

import com.vincent.bos.domain.take_divery.PageBean;
import com.vincent.bos.domain.take_divery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-31 16:41
 */
public interface PromotionService {
 void save(Promotion promotion);

 Page<Promotion> pageQuery(Pageable pageable);

 @GET
 @Path("/pageQuery")
 @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
 @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
 PageBean<Promotion> pageQuery4Fore(@QueryParam("page") int page,
                                    @QueryParam("size") int size);

}
