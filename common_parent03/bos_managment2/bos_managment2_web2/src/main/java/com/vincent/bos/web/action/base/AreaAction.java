package com.vincent.bos.web.action.base;

import com.vincent.bos.domain.base.Area;
import com.vincent.bos.service.base.AreaService;
import com.vincent.bos.web.action.CommonAction;
import com.vincent.utils.PinYin4jUtils;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-16 15:49
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

 private String q;
 private File file;

 @Autowired
 private AreaService areaService;

 public void setQ(String q) {
  this.q = q;
 }

 public AreaAction() {
  super(Area.class);
 }


 /**
  * 区域分页查询
  * @return
  * @throws IOException
  */
 @Action(value = "areaAction_pageQuery")
 public String pageQuery() throws IOException {
  Pageable pageable = new PageRequest(page - 1, rows);

  Page<Area> pageBean = areaService.findAll(pageable);
  JsonConfig jsonConfig = new JsonConfig();
  jsonConfig.setExcludes(new String[]{"subareas"});

  pageBeanToJson(pageBean, jsonConfig);


  return NONE;
 }

 /**
  * 区域查询,供给增加分区下拉框使用,只需要所有数据,不需要分页效果!
  * @return
  */
 @Action(value = "areaAction_findAll")
 public String findAll(){
  try {
  List<Area> list;
   System.out.println();

   if(StringUtils.isNotEmpty(q)){
  list = areaService.findQ(q);
  }else{

    Page<Area> pageBean = areaService.findAll(null);
    list = pageBean.getContent();
   }
   JsonConfig jsonConfig = new JsonConfig();
   jsonConfig.setExcludes(new String[]{"subareas"});

   listToJson(list,jsonConfig);


  } catch (IOException e) {
   e.printStackTrace();
  }


  return NONE;
 }
 /**
  * 导入区域excel数据
  * @return
  */
 @Action(value = "areaAction_doImport", results = {@Result(name = "success", location = "pages/base/area.html", type = "redirect")})
 public String doImport() {

  try {
   HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
   HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
   ArrayList<Area> list = new ArrayList<>();
   for (Row row : sheet) {
    String province = row.getCell(1).getStringCellValue();
    String city = row.getCell(2).getStringCellValue();
    String district = row.getCell(3).getStringCellValue();
    String postcoce = row.getCell(4).getStringCellValue();

    //去除后面一个字
    province = province.substring(0, province.length() - 1);
    city = city.substring(0, city.length() - 1);
    district = district.substring(0, district.length() - 1);
    String citycode = PinYin4jUtils.hanziToPinyin(city, "");

    final String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
    String shortCode = PinYin4jUtils.stringArrayToString(headByString);

    Area area = new Area();

    area.setProvince(province);
    area.setCity(city);
    area.setDistrict(district);
    area.setPostcode(postcoce);
    area.setCitycode(citycode);
    area.setShortcode(shortCode);

    list.add(area);

   }

   areaService.saveArea(list);

  } catch (IOException e) {
   e.printStackTrace();
  }
  return SUCCESS;
 }


}
