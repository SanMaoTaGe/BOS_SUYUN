package com.vincent.bos.web.action.base;

import com.vincent.bos.domain.base.TakeTime;
import com.vincent.bos.service.TakeTimeService;
import com.vincent.bos.web.action.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-22 11:05
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime>{

 public TakeTimeAction() {
  super(TakeTime.class);
 }



  @Autowired
  private TakeTimeService takeTimeService;

  // 查询所有的时间
  @Action(value = "takeTimeAction_findAll")
  public String findAll() throws IOException {

   List<TakeTime> content = takeTimeService.findAll();

   listToJson(content, null);
   return NONE;
  }

 }


