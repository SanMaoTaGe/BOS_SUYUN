package com.vincent.bos.fore.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.vincent.bos.domain.base.Area;
import com.vincent.bos.domain.take_divery.Order;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-24 10:57
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

 private Order order = new Order();

 @Override
 public Order getModel() {
  return order;
 }

 private String sendAreaInfo;
 private String recAreaInfo;

 public void setSendAreaInfo(String sendAreaInfo) {
  this.sendAreaInfo = sendAreaInfo;

 }

 public void setRecAreaInfo(String recAreaInfo) {
  this.recAreaInfo = recAreaInfo;
 }

 @Action(value = "orderAction_add", results = {@Result(name = "success", location = "index.html", type = "redirect"), @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
 public String add() {
  System.out.println("-------------------orderAction--------add");

  //area:省市区
  Area sendArea = areaInforToArea(sendAreaInfo);
  order.setSendArea(sendArea);
  Area recArea = areaInforToArea(recAreaInfo);
  order.setRecArea(recArea);

  //保存订单到后台
  WebClient.create("http://localhost:8080/webService/orderService/save")
   .type(MediaType.APPLICATION_JSON)
   .accept(MediaType.APPLICATION_JSON)
   .post(order);

  return SUCCESS;
 }

 //封装area工具类

 /**
  * @param infor
  * @return Area
  */
 private static Area areaInforToArea(String areaInfor) {
  Area area = new Area();
  if (StringUtils.isNotEmpty(areaInfor)) {
   //处理省市区
   String[] split = areaInfor.split("/");
   for (int i = 0; i < split.length; i++) {
    split[i] = split[i].substring(0, split[i].length() - 1);
   }
   area.setProvince(split[0]);
   area.setCity(split[1]);
   area.setDistrict(split[2]);
  }
  return area;
 }


}
