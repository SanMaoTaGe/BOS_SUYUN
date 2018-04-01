package com.vincent.bos.web.action.base;

import com.vincent.bos.domain.base.Area;
import com.vincent.bos.service.base.AreaService;
import com.vincent.bos.web.action.CommonAction;
import com.vincent.utils.FileDownloadEncodeNameUtils;
import com.vincent.utils.PinYin4jUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import javax.sql.DataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  *
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
  *
  * @return
  */
 @Action(value = "areaAction_findAll")
 public String findAll() {
  try {
   List<Area> list;
   System.out.println();

   if (StringUtils.isNotEmpty(q)) {
    list = areaService.findQ(q);
   } else {

    Page<Area> pageBean = areaService.findAll(null);
    list = pageBean.getContent();
   }
   JsonConfig jsonConfig = new JsonConfig();
   jsonConfig.setExcludes(new String[]{"subareas"});

   listToJson(list, jsonConfig);


  } catch (IOException e) {
   e.printStackTrace();
  }


  return NONE;
 }

 /**
  * 导入区域excel数据
  *
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

 @Action("areaAction_exportExcel")
 public String exportExcel() throws IOException {

  //先准备好数据
  Page<Area> pageBean = areaService.findAll(null);
  List<Area> list = pageBean.getContent();

  //建表
  HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

  HSSFSheet sheet = hssfWorkbook.createSheet();

  HSSFRow titleRow = sheet.createRow(0);

  titleRow.createCell(0).setCellValue("省");
  titleRow.createCell(1).setCellValue("市");
  titleRow.createCell(2).setCellValue("区");
  titleRow.createCell(3).setCellValue("邮编");
  titleRow.createCell(4).setCellValue("简码");
  titleRow.createCell(5).setCellValue("城市编码");

  int i = 1;
  for (Area area : list) {

   HSSFRow dataRow = sheet.createRow(i);
   dataRow.createCell(0).setCellValue(area.getProvince());
   dataRow.createCell(1).setCellValue(area.getCity());
   dataRow.createCell(2).setCellValue(area.getDistrict());
   dataRow.createCell(3).setCellValue(area.getPostcode());
   dataRow.createCell(4).setCellValue(area.getShortcode());
   dataRow.createCell(5).setCellValue(area.getCitycode());
   i++;
  }
  //两头一流
  String fileName = "区域数据统计_gaga.xls";

  HttpServletResponse response = ServletActionContext.getResponse();
  ServletContext servletContext = ServletActionContext.getServletContext();
  String mimeType = servletContext.getMimeType(fileName);


  HttpServletRequest request = ServletActionContext.getRequest();
  //处理fileName中文乱码问题
  fileName = FileDownloadEncodeNameUtils.encodeDownloadFilename(fileName, request.getHeader("User-Agent"));


  //两头一流处理
  ServletOutputStream outputStream = response.getOutputStream();
  response.setContentType(mimeType);//头1
  response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

  hssfWorkbook.write(outputStream); //流
hssfWorkbook.close();

  return NONE;
 }

 @Action("areaAction_exportCharts")
 public String areaAction_exportCharts() throws IOException {

  List<Object[]> list = areaService.exportCharts();
  listToJson(list, null);

  return NONE;
 }

 @Autowired
 private DataSource dataSource;

 @Action("areaAction_exportPDF")
 public String areaAction_exportPDF() throws Exception {

  // 读取 jrxml 文件
  String jrxml = ServletActionContext.getServletContext().getRealPath("/jasper/report1.jrxml");
  // 准备需要数据
  Map<String, Object> parameters = new HashMap<String, Object>();
  parameters.put("company", "传智播客");
  // 准备需要数据
  JasperReport report = JasperCompileManager.compileReport(jrxml);

  JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());

  HttpServletResponse response = ServletActionContext.getResponse();
  OutputStream ouputStream = response.getOutputStream();
  // 设置相应参数，以附件形式保存PDF
  response.setContentType("application/pdf");
  response.setCharacterEncoding("UTF-8");
  response.setHeader("Content-Disposition", "attachment; filename=" + FileDownloadEncodeNameUtils.encodeDownloadFilename("工作单.pdf",
   ServletActionContext.getRequest().getHeader("user-agent")));
  // 使用JRPdfExproter导出器导出pdf
  JRPdfExporter exporter = new JRPdfExporter();
  exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
  exporter.exportReport();// 导出
  ouputStream.close();// 关闭流

  return NONE;
 }
}
