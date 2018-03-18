package com.vincent.bos.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-16 20:39
 */

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

 private T model;
 private Class<T> clazz;

 public CommonAction(Class<T> clazz) {
  try {
   model = clazz.newInstance();
  } catch (InstantiationException e) {
   e.printStackTrace();
  } catch (IllegalAccessException e) {
   e.printStackTrace();
  }
  this.clazz = clazz;
 }

 protected int page;
 protected int rows;


 public void setRows(int rows) {
  this.rows = rows;
 }

 public void setPage(int page) {
  this.page = page;
 }

 @Override
 public T getModel() {

  return model;
 }

 public void pageBeanToJson(Page<T> pageBean, JsonConfig jsonConfig) throws IOException {


  List<T> list = pageBean.getContent();
  long total = pageBean.getTotalElements();
  Map<String, Object> map = new HashMap<>();
  map.put("total", total);
  map.put("rows", list);
  String json;
  if (jsonConfig == null) {
   json = JSONObject.fromObject(map).toString();
  } else {
   json = JSONObject.fromObject(map, jsonConfig).toString();
  }

  HttpServletResponse response = ServletActionContext.getResponse();
  response.setContentType("application/json;charset=UTF-8");
  response.getWriter().write(json);

 }

}
