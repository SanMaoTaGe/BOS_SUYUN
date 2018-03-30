package com.vincent.bos.service.jobs;

import com.vincent.bos.dao.takeDelivery.WorkbillRepository;
import com.vincent.bos.domain.take_divery.WorkBill;
import com.vincent.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-30 10:17
 */
@Component
public class WorkbillJob {

 @Autowired
 WorkbillRepository workbillRepository;

 public void  sendMail(){

  List<WorkBill> list = workbillRepository.findAll();

  String emailBody = "编号\t快递员\t取件状态\t时间<br/>";

  for (WorkBill workBill : list) {
   emailBody += workBill.getId() + "\t"
    + workBill.getCourier().getName() + "\t"
    + workBill.getPickstate() + "\t"
    + workBill.getBuildtime().toLocaleString() + "<br/>";
  }

  MailUtils.sendMail("workbillinfo",emailBody,"13692280056@163.com");
  System.out.println("email sended....................");
 }

}
