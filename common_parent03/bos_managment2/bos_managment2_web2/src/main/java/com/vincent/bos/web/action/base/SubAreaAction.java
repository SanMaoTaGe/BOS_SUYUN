package com.vincent.bos.web.action.base;

import com.vincent.bos.domain.base.SubArea;
import com.vincent.bos.service.base.SubAreaService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
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

import java.io.IOException;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-18 19:06
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class SubAreaAction extends CommonAction<SubArea> {

 @Autowired
 private SubAreaService subAreaService;

 public SubAreaAction() {
  super(SubArea.class);
 }

 @Action(value = "subareaAction_save", results = {@Result(name = "success", location = "pages/base/sub_area.html", type = "redirect")})
 public String save() {
  subAreaService.save(getModel());

  return SUCCESS;
 }

 @Action(value = "subareaAction_pageQuery", results = {@Result(name = "success", location = "pages/base/sub_area.html", type = "redirect")})
 public String pageQuery() throws IOException {
  //page,rows是页面框架传过来的数据,分别代表当前页数和本页显示数量
  Pageable pageable = new PageRequest(page - 1, rows);
  Page<SubArea> pageBean = subAreaService.findAll(pageable);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"subareas"});

  pageBeanToJson(pageBean, jsonConfig);

  return NONE;
 }

 /**
  * 未关联定区的
  * @return
  */
 @Action("subAreaAction_findSubAreaUnassociatedWithFixedArea")
 public String findSubAreaUnassociatedWithFixedArea() throws IOException {
  List<SubArea> list =subAreaService.findSubAreaUnassociatedWithFixedArea();

  JsonConfig jsonConfig =  new JsonConfig();
  jsonConfig.setExcludes(new String[]{"subareas"});
  listToJson(list,jsonConfig);
  return NONE;
 }

 /**
  * 已关联定区
  * @return
  */
 @Action("subAreaAction_findSubAreaAssociatedWithFixedArea")
 public String findSubAreaAssociatedWithFixedArea() throws IOException {

  List<SubArea> list =subAreaService.findSubAreaAssociatedWithFixedArea(getModel().getId());
  JsonConfig jsonConfig =  new JsonConfig();
  jsonConfig.setExcludes(new String[]{"subareas","couriers"});
  listToJson(list,jsonConfig);
  return NONE;
 }

}
