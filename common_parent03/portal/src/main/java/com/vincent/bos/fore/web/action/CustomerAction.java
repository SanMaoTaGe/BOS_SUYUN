package com.vincent.bos.fore.web.action;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.vincent.bos.fore.domain.Customer;
import com.vincent.utils.MailUtils;
import com.vincent.utils.SmsUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

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
 public String sendSMS() {

  String code = RandomStringUtils.randomNumeric(6);

  System.out.println("code:------->" + code);
  System.out.println("phone:------->" + customer.getTelephone());

  //存入session
  ServletActionContext.getRequest().getSession().setAttribute("ServerCode", code);

  try {
   SmsUtils.sendSms(customer.getTelephone(), code);
  } catch (ClientException e) {
   e.printStackTrace();
  }
  return NONE;
 }

 private String checkcode;

 public void setCheckcode(String checkcode) {
  this.checkcode = checkcode;
 }

 @Autowired
 private RedisTemplate<String, String> redisTemplate;

 @Action(value = "customerAction_regist", results = {@Result(name = "success", location = "/signup-success.html", type = "redirect"), @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
 public String regist() {
  System.out.println("-----------------------regist");
  String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("ServerCode");
//校验注册验证码
  if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode) && checkcode.equals(serverCode)) {
   //注册
   WebClient.create("http://localhost:8180/webService/customerService/save")
    .type(MediaType.APPLICATION_JSON) // 传递的格式
    .post(customer);

   //信息OK后,发送激活邮件
   //生成验证码
   String activeCode = RandomStringUtils.randomNumeric(32);
   //利用redis储存验证码
   //有效时间1天
   redisTemplate.opsForValue().set(customer.getTelephone(), activeCode, 1, TimeUnit.DAYS);


   String emailText = "感谢注册,请点解以下链接文字激活您的账号"
    + "<a href='http://localhost:8280/customerAction_active.action?activeCode="
    + activeCode
    + "&telephone="
    + customer.getTelephone()
    + "'>此链接</a>激活您的账号";

   //调用工具类发邮件
   MailUtils.sendMail("来自长风的注册邮件", emailText, "13692280056@163.com");
   return SUCCESS;
  }
  return ERROR;
 }

 private String activeCode;

 public void setActiveCode(String activeCode) {
  this.activeCode = activeCode;
 }

 @Action(value = "customerAction_active", results = {@Result(name = "success", location = "/login.html", type = "redirect"), @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
 public String active() {
  System.out.println("-----------------active---------------\n");
  String serverActiveCode = redisTemplate.opsForValue().get(customer.getTelephone());

  if (StringUtils.isNotEmpty(activeCode) && StringUtils.isNotEmpty(serverActiveCode) && activeCode.equals(serverActiveCode)) {
   //到crm中激活
   System.out.println("--------------------------通过了激活码的初级验证");
   WebClient.create("http://localhost:8180/webService/customerService/active")
    .type(MediaType.APPLICATION_JSON)
    .query("telephone",customer.getTelephone())
    .accept(MediaType.APPLICATION_JSON)
    .put(null);
   return SUCCESS;
  }
  return ERROR;
 }

 /**
  * 登录
  *
  * @return
  */
 @Action(value = "customerAction_login", results = {
  @Result(name = "success", location = "/myhome.html", type = "redirect"),
  @Result(name = "error", location = "/login.html", type = "redirect"),
  @Result(name = "unActived", location = "/login.html", type = "redirect")
 })
 public String login() {
  System.out.println("-----------------login---------------\n");
  //拿到验证码,验证由工具类存到session,key为validateCode
  String serverValidateCode = (String) ServletActionContext.getRequest().getSession().getAttribute(("validateCode"));
  if (StringUtils.isNotEmpty(serverValidateCode) && StringUtils.isNotEmpty(checkcode) && serverValidateCode.equals(checkcode)) {
   //验证码正确,做进一步动作
   //确认是否激活了
   Customer cc = WebClient.create("http://localhost:8180/webService/customerService/isActived")
    .type(MediaType.APPLICATION_JSON)
    .query("telephone", this.customer.getTelephone())
    .accept(MediaType.APPLICATION_JSON)
    .get(Customer.class);

   //用户
   if (cc != null && cc.getType() != null) {

    //符合条件,提取信息并跳转
    if (cc.getType() == 1) {
     Customer c2 = WebClient.create("http://localhost:8180/webService/customerService/login")
      .type(MediaType.APPLICATION_JSON)
      .query("telephone", this.customer.getTelephone())
      .query("password", this.customer.getPassword())
      .accept(MediaType.APPLICATION_JSON)
      .get(Customer.class);

     //根据账号密码找用户,如果找到说明账号和密码对
     if (c2 != null) {
      ServletActionContext.getRequest().getSession().setAttribute("user", c2);
      return SUCCESS;
      //登录信息有误
     } else {
      return ERROR;
     }

    } else {

     //用户存在,但激活状态未变更
     return "unActived";
    }
   }
  }
  //验证码验证失败,重新登录
  return ERROR;

 }

}
