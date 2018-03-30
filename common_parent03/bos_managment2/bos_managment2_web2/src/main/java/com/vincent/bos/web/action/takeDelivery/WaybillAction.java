package com.vincent.bos.web.action.takeDelivery;

import com.vincent.bos.domain.take_divery.WayBill;
import com.vincent.bos.service.takeDelivery.WaybillService;
import com.vincent.bos.web.action.CommonAction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-25 17:41
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WaybillAction extends CommonAction<WayBill> {

 public WaybillAction() {
  super(WayBill.class);
 }

 @Autowired
 private WaybillService waybillService;

 @Action("waybillAction_save")
 public String save() throws IOException {
  System.out.println("- @Action(\"waybillAction_save\")\n");

  String msg = "0";

  try {

   waybillService.save(getModel());
  } catch (Exception e) {
   msg = "1";
   e.printStackTrace();
  }

  HttpServletResponse response = ServletActionContext.getResponse();
  response.setContentType("text/html;charset=UTF-8");
  response.getWriter().write(msg);

  return NONE;
 }
}
