package com.vincent.bos.web.action.base;

import com.vincent.bos.domain.base.Courier;
import com.vincent.bos.domain.base.Standard;
import com.vincent.bos.service.base.CourierService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-15 15:49
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {

 @Autowired
 private CourierService courierService;

 private String ids;

 public CourierAction() {
  super(Courier.class);
 }


 public void setIds(String ids) {
  this.ids = ids;
 }


 /**
  * 快递员查询
  *
  * @return
  */
 @Action(value = "courierAction_save", results = {@Result(name = "success", location = "pages/base/courier.html", type = "redirect")})
 public String save() {
  courierService.save(getModel());

  return SUCCESS;
 }

 /**
  * 删除快递员(设置标志)
  *
  * @return
  */
 @Action(value = "courierAction_delete", results = {@Result(name = "success", location = "pages/base/courier.html", type = "redirect")})
 public String delete() {

  courierService.delete(ids);
  return SUCCESS;
 }

 /**
  * 快递员分页查询
  *
  * @return
  * @throws IOException
  */
 @Action(value = "courierAction_pageQuery")
 public String pageQuery() throws IOException {
  Specification<Courier> specification = new Specification<Courier>() {

   @Override
   public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    String courierNum = getModel().getCourierNum();
    Standard standard = getModel().getStandard();
    String company = getModel().getCompany();
    String type = getModel().getType();
    ArrayList<Predicate> list = new ArrayList<>();

    //非空判断
    if (StringUtils.isNotEmpty(courierNum)) {
     Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
     list.add(p1);
    }

    if (StringUtils.isNotEmpty(company)) {
     Predicate p2 = cb.like(root.get("company").as(String.class), "%" + company + "%");
     list.add(p2);
    }

    if (StringUtils.isNotEmpty(type)) {
     Predicate p3 = cb.equal(root.get("type").as(String.class), type);
     list.add(p3);
    }

    if (standard != null) {
     String name = standard.getName();
     if (StringUtils.isNotEmpty(name)) {
      //连表查询
      Join<Object, Object> join = root.join("standard");
      Predicate p4 = cb.equal((join.get("name")).as(String.class), name);
      list.add(p4);
     }
    }

    //如果没有输入条件
    if (list.size() == 0) {

     return null;
    }
    // 构造数组-->将list集合转为数组
    // Predicate[] arr = new Predicate[list.size()];
    // list.toArray(arr);

    Predicate[] arr = list.toArray(new Predicate[list.size()]);

    return cb.and(arr);

   }
  };

  Pageable pageable = new PageRequest(page - 1, rows);
  Page<Courier> pageBean = courierService.findAll(specification, pageable);

  List<Courier> list = pageBean.getContent();

  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"fixedAreas", "takeTime"});

  pageBeanToJson(pageBean, jsonConfig);

  return NONE;
 }

 @Action(value = "courierAction_findCourierWorking")
 public String findCourierWorking() throws IOException {

  Specification<Courier> specification = new Specification<Courier>() {
   @Override
   public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    Predicate deltag = cb.isNull(root.get("deltag").as(Character.class));
    return deltag;
   }
  };
  Page<Courier> pageBean = courierService.findAll(specification, null);

  List<Courier> list = pageBean.getContent();

  JsonConfig jsonConfig = new JsonConfig();

  jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime" });

  listToJson(list,jsonConfig);

  return NONE;
 }

}
