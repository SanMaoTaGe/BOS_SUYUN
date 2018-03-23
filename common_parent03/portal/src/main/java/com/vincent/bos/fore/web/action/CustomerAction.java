package com.vincent.bos.fore.web.action;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.vincent.bos.fore.domain.Customer;
import com.vincent.utils.SmsUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-22 21:08
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

 private Customer customer = new Customer();
 @Override
 public Customer getModel() {
  return customer;
 }
 @Action(value = "customerAction_sendSMS")
  public String sendSMS(){

  String code = RandomStringUtils.randomNumeric(6);

  System.out.println("code:------->"+code);
  System.out.println("phone:------->"+customer.getTelephone());

  //存入session
  ServletActionContext.getRequest().getSession().setAttribute("ServerCode",code);

  try {
   SmsUtils.sendSms(customer.getTelephone(),code);
  } catch (ClientException e) {
   e.printStackTrace();
  }
  return NONE;
  }

private String checkcode;

 public void setCheckcode(String checkcode) {
  this.checkcode = checkcode;
 }

 @Action(value = "customerAction_regist", results = {@Result(name = "success", location = "/signup-success.html", type = "redirect"),@Result(name = "error", location = "/signup-fail.html", type = "redirect")})
 public String regist(){
  System.out.println("-----------------------regist");
  String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("ServerCode");
  System.out.println("-----------------------serverCode1---"+serverCode);
  System.out.println("-----------------------checkCode1----"+checkcode);
  System.out.println("-----------------------customer1----"+customer);

  if (StringUtils.isNotEmpty(serverCode)&&StringUtils.isNotEmpty(checkcode)&&checkcode.equals(serverCode)){
   WebClient.create("http://localhost:8180/webService/customerService/save")
   .type(MediaType.APPLICATION_JSON) // 传递的格式
    .post(customer);
   return SUCCESS;
  }
  return ERROR;
 }


}
