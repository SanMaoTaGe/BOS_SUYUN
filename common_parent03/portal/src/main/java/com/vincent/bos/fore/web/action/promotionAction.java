package com.vincent.bos.fore.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.vincent.bos.domain.take_divery.PageBean;
import com.vincent.bos.domain.take_divery.Promotion;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author Vincent
 * @Description:
 * @create 2018-04-01 17:44
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class promotionAction extends ActionSupport{
 private static final long serialVersionUID = 4309775879562173426L;

 // 属性驱动获取分页查询的页码
 // 属性驱动获取分页查询每页条数
 private int pageSize;
 private int pageIndex;

 public void setPageIndex(int pageIndex) {
  this.pageIndex = pageIndex;
 }

 public void setPageSize(int pageSize) {
  this.pageSize = pageSize;
 }

 @Action("promotionAction_pageQuery")
 public String pageQuery() throws IOException {
  System.out.println("前台promotion action---------------------------------------");
  // 调用WebService请求后台,查询促销信息
  PageBean<Promotion> pageBean = WebClient
   .create("http://localhost:8080/webService/promotionService/pageQuery")
   .query("page", pageIndex)//
   .query("size", pageSize)//
   .accept(MediaType.APPLICATION_JSON)//
   .get(PageBean.class);
  // 将结果转为json,写回页面
  String json = JSONObject.fromObject(pageBean).toString();
  HttpServletResponse response = ServletActionContext.getResponse();
  response.setContentType("application/json;charset=UTF-8");
  response.getWriter().write(json);
  return NONE;
 }


}
