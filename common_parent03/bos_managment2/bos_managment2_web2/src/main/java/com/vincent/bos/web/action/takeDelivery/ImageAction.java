package com.vincent.bos.web.action.takeDelivery;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-25 20:22
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport {
 private static final long serialVersionUID = 2640985690637555354L;

 // 属性驱动获取文件
 private File imgFile;

 public void setImgFile(File imgFile) {
  this.imgFile = imgFile;
 }

 // 属性驱动获取文件名
 private String imgFileFileName;

 public void setImgFileFileName(String imgFileFileName) {
  this.imgFileFileName = imgFileFileName;
 }

 @Action("imageAction_upload")
 public String upload() throws IOException {
  System.out.println("--------------------upload");
  Map<String, Object> map = new HashMap<>();

  try {
   String dirpath = "/upload";
   // D:aa/upload/a.jpg
   // 获取保存图片的文件夹的绝对磁盘路径
   ServletContext servletContext = ServletActionContext.getServletContext();

   String realPath = servletContext.getRealPath(dirpath);
   // D:aa/upload/a.jpg
   // 获取保存图片的文件夹的绝对磁盘路径

   String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
   // 使用UUId生成文件名
   String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
   //new 一个新文件
   File destFile = new File(realPath + "/" + fileName);

   //保存
   FileUtils.copyFile(imgFile, destFile);

   String contextPath = servletContext.getContextPath();

   map.put("error", 0);
   map.put("url", contextPath + "/upload/" + fileName);
  } catch (IOException e) {
   e.printStackTrace();
   map.put("error", 1);
   map.put("message", e.getMessage());
  }

  String json = JSONObject.fromObject(map).toString();

  HttpServletResponse response = ServletActionContext.getResponse();
  response.setContentType("application/json;charset=UTF-8");
  response.getWriter().write(json);
  return NONE;
 }
}
