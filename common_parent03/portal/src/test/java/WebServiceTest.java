import com.vincent.bos.domain.base.Customer;
import com.vincent.bos.domain.take_divery.Order;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-24 14:44
 */
public class WebServiceTest {
 @Test
 public void fun01() {
  Collection<? extends Order> order= WebClient
   .create("http://localhost:8080/webService/orderService/findAll")
   .type(MediaType.APPLICATION_JSON)
   .accept(MediaType.APPLICATION_JSON)
   .getCollection(Order.class);
  System.out.println("---------------cc\n"+order);
 }

}
