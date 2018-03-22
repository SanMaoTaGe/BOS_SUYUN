package com.vincent.bos.web.action.base;

import com.vincent.bos.domain.base.Customer;
import com.vincent.bos.domain.base.FixedArea;
import com.vincent.bos.service.base.FixedAreaService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


/**
 * @author Vincent
 * @Description:
 * @create 2018-03-21 15:58
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class FixedAreaAction extends CommonAction<FixedArea> {

 @Autowired
 FixedAreaService fixedAreaService;

 private Long[] customerIds;

 public void setCustomerIds(Long[] customerIds) {
  this.customerIds = customerIds;
 }

 public FixedAreaAction() {
  super(FixedArea.class);
 }

 @Action(value = "fixedAreaAction_save", results = {@Result(name = "success", location = "pages/base/fixed_area.html", type = "redirect")})
 public String save() {
  fixedAreaService.save(getModel());

  return SUCCESS;
 }

 /**
  * 显示定区表
  *
  * @return
  * @throws IOException
  */
 @Action("fixedAreaAction_pageQuery")
 public String pageQuery() throws IOException {

  Pageable pageable = new PageRequest(page - 1, rows);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"subareas", "couriers"});

  Page<FixedArea> pageBean = fixedAreaService.findAll(pageable, jsonConfig);

  pageBeanToJson(pageBean, jsonConfig);


  return NONE;
 }

 /**
  * 查找UnAssociated区域的客户
  *
  * @return
  * @throws IOException
  */
 @Action(value = "fixedAreaAction_findCustomersUnAssociated")
 public String findCustomersUnAssociated() throws IOException {
  //向crm WebService发起请求

  List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/webService/customerService/findCustomersUnAssociated")
   .type(MediaType.APPLICATION_JSON)
   .accept(MediaType.APPLICATION_JSON)
   .getCollection(Customer.class);
  listToJson(list, null);
  return NONE;
 }

 /**
  * 查找Associated区域的客户
  *
  * @return
  * @throws IOException
  */
 @Action(value = "fixedAreaAction_findCustomersAssociated")
 public String findCustomersAssociated() throws IOException {
  List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/webService/customerService/findCustomersAssociated")
   .type(MediaType.APPLICATION_JSON)
   .accept(MediaType.APPLICATION_JSON)
   .query("fixedAreaId", getModel().getId())
   .getCollection(Customer.class);
  listToJson(list, null);
  return NONE;
 }


 @Action(value = "fixedAreaAction_assignCustomersToFixedArea", results = {@Result(name = "success", location = "pages/base/fixed_area.html", type = "redirect")})
 public String assignCustomersToFixedArea() {

  //将该定区客户解绑
  WebClient.create("http://localhost:8180/webService/customerService/assignCustomersToFixedArea")
   .type(MediaType.APPLICATION_JSON)
   .accept(MediaType.APPLICATION_JSON)
   .query("fixedAreaId", getModel().getId())
   .query("customerIds", customerIds)
   .put(null);

  //将右边选框和分区绑定
  return SUCCESS;
 }

 private Long courierId;
 private Long takeTimeId;

 public void setCourierId(Long courierId) {
  this.courierId = courierId;

 }

 public void setTakeTimeId(Long takeTimeId) {
  this.takeTimeId = takeTimeId;
 }

 /**
  * 定区关联快递员
  *
  * @return
  */
 @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {@Result(name = "success", location = "pages/base/fixed_area.html", type = "redirect")})
 public String associationCourierToFixedArea() {
  fixedAreaService.associationCourierToFixedArea(courierId, takeTimeId, getModel().getId());

  return SUCCESS;
 }

private Long[] subAreaIds;

 public void setSubAreaIds(Long[] subAreaIds) {
  System.out.println("000000000000");
  this.subAreaIds = subAreaIds;
 }

 /**
  * 关联分区
  * @return
  */
 @Action(value = "fixedAreaAction_assignSubAreasToFixedArea", results = {@Result(name = "success", location = "pages/base/fixed_area.html", type = "redirect")})
 public String assignSubAreasToFixedArea() {
  fixedAreaService.assignSubAreasToFixedArea(getModel().getId(),subAreaIds);

  return SUCCESS;
 }


}
