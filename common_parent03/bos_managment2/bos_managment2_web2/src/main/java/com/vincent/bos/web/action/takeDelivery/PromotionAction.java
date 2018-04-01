package com.vincent.bos.web.action.takeDelivery;

import com.vincent.bos.domain.base.SubArea;
import com.vincent.bos.domain.take_divery.PageBean;
import com.vincent.bos.domain.take_divery.Promotion;
import com.vincent.bos.service.takeDelivery.PromotionService;
import com.vincent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-31 16:42
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends CommonAction<Promotion> {


 private File titleImgFile;
 private String titleImgFileFileName;

 public void setTitleImgFile(File titleImgFile) {
  this.titleImgFile = titleImgFile;
 }

 public void setTitleImgFileFileName(String titleImgFileFileName) {
  this.titleImgFileFileName = titleImgFileFileName;
 }

 @Autowired
 private PromotionService promotionService;

 public PromotionAction() {
  super(Promotion.class);
 }

 @Action(value = "promotionAction_save",
  results = {
   @Result(name = "success",
    location = "/pages/take_delivery/promotion.html",
    type = "redirect"),
   @Result(name = "error", location = "/error.html",
    type = "redirect")})

 public String save() throws IOException {
  System.out.println("save-----------------------------------------");
  Promotion promotion = getModel();

  try {
   if (titleImgFile != null) {

    String saveDirPath = "upload";
    String saveRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);

    // 获取文件后缀名
    String suffix = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));

    String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

    // 复制文件
    FileUtils.copyFile(titleImgFile, new File(saveRealPath + "/" + fileName));

    // 设置封面图片保存的路径
    // 路径格式 : /upload/xxx.jpg

    promotion.setTitleImg("/upload/" + fileName);

    promotion.setActiveScope("1");
    promotionService.save(promotion);
   }
   return SUCCESS;
  } catch (IOException e) {
   e.printStackTrace();
  }
  return ERROR;
 }
 @Action(value = "promotionAction_pageQuery")
 public String pageQuery() throws IOException {

  System.out.println("------------------后台promotion action");
  Pageable pageable = new PageRequest(page - 1, rows);
  Page<Promotion> page = promotionService.pageQuery(pageable);
  pageBeanToJson(page,null);



  return NONE;
 }
}
