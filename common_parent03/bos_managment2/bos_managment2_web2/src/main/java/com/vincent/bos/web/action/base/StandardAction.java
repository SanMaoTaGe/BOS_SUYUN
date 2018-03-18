package com.vincent.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.vincent.bos.domain.base.Standard;
import com.vincent.bos.service.base.StandardService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-14 15:39
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class StandardAction extends CommonAction<Standard>  {


 @Autowired
 private StandardService standardService;

 public StandardAction() {

  super(Standard.class);
 }


 @Action("standard_findAll")
 public String findAll() throws IOException {
  Page<Standard> pageBean = standardService.findAll(null);
  pageBeanToJson(pageBean,new JsonConfig());
  return NONE;
 }

 /**
  * 分页查询标准
  *
  * @return
  */
 @Action(value = "standardAction_pageQuery")
 public String pageQuery() throws IOException {
  Pageable pageable = new PageRequest(page - 1, rows);
  Page<Standard> pageBean = standardService.findAll(pageable);

  pageBeanToJson(pageBean,null);

  return NONE;
 }

 /**
  * 保存标准
  *
  * @return
  */
 @Action(value = "standardAction_save", results = {@Result(name = "success", location = "pages/base/standard.html", type = "redirect")})
 public String save() {
  //System.out.println("\n打印结果bbb-->" + standard + "\n");
  standardService.save(getModel());
  return SUCCESS;
 }
}
