package com.vincent.bos.service.takeDelivery.impl;

import com.vincent.bos.dao.base.AreaRepository;
import com.vincent.bos.dao.base.FixedAreaRepository;
import com.vincent.bos.dao.takeDelivery.OrderRepository;
import com.vincent.bos.dao.takeDelivery.WorkbillRepository;
import com.vincent.bos.domain.base.Area;
import com.vincent.bos.domain.base.Courier;
import com.vincent.bos.domain.base.FixedArea;
import com.vincent.bos.domain.base.SubArea;
import com.vincent.bos.domain.take_divery.Order;
import com.vincent.bos.domain.take_divery.WorkBill;
import com.vincent.bos.service.takeDelivery.OrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-24 13:00
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

 @Autowired
 private OrderRepository orderRepository;
 @Autowired
 private AreaRepository areaRepository;
 @Autowired
 private FixedAreaRepository fixedAreaRepository;
 @Autowired
 private WorkbillRepository workbillRepository;


 @Override
 public void save(Order order) {
  System.out.println("--------------saveorder in service" + order);
// 把瞬时态的Area转换为持久态的Area
  Area sendArea = order.getSendArea();
  if (sendArea != null) {
   // 持久态对象
   Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(
    sendArea.getProvince(), sendArea.getCity(),
    sendArea.getDistrict());
   order.setSendArea(sendAreaDB);
  }
  Area recArea = order.getRecArea();
  if (recArea != null) {
   // 持久态对象
   Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(
    recArea.getProvince(), recArea.getCity(),
    recArea.getDistrict());
   order.setRecArea(recAreaDB);
  }

  //保存订单
  order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
  order.setOrderTime(new Date());
  orderRepository.save(order);

  //自动分单
  //拿到地址后1.查crm;2.crm查不到就查分区
  String sendAddress = order.getSendAddress();

  if (StringUtils.isNotEmpty(sendAddress)) {
   // ---根据发件地址完全匹配
   // 让crm系统根据发件地址查询定区ID
   // 做下面的测试之前,必须在定区中关联一个客户,下单的页面填写的地址,必须和这个客户的地址一致

   String fixedAreaID = WebClient.create(
    "http://localhost:8180/webService/customerService/findFixedAreaIdByAdddress")
    .type(MediaType.APPLICATION_JSON)
    .query("address", sendAddress)
    .accept(MediaType.APPLICATION_JSON).get(String.class);
   System.out.println("\nfixedAreaID------------------" + fixedAreaID + "\n");

   if (StringUtils.isNotEmpty(fixedAreaID)) {
    FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaID));
    orderAuto(fixedArea, order, workbillRepository);

    //用另一种方法自动下单
   } else {
    Area sendArea2 = order.getSendArea();
    if (sendArea2 != null) {
     Set<SubArea> subareas = sendArea2.getSubareas();

     for (SubArea s : subareas
      ) {
      String keyWords = s.getKeyWords();
      String assistKeyWords = s.getAssistKeyWords();
      //匹配输入地址和分区关键字对应的分区,然后下面作处理:拿到对应的定区
      if (sendAddress.contains(keyWords) || sendAddress.contains(assistKeyWords)) {
       FixedArea fixedArea = s.getFixedArea();
       orderAuto(fixedArea, order, workbillRepository);

      }

     }
    }

   }
   //手动下单
   order.setOrderType("人工分单");
  }
 }

 @Override
 public List<Order> findAll() {
  return orderRepository.findAll();
 }


 //封装自动下单操作
 private static void orderAuto(FixedArea fixedArea, Order order, WorkbillRepository workbillRepository) {
  if (fixedArea != null) {
   //查询快递员
   Set<Courier> couriers = fixedArea.getCouriers();

   if (!couriers.isEmpty()) {
    // 根据快递员的上班时间/收派能力/忙闲程度
    //这里简化,直接用第一个快递员
    Iterator<Courier> iterator = couriers.iterator();

    //指派快递员
    Courier courier = iterator.next();

    order.setCourier(courier);

    //生成工单
    WorkBill workBill = new WorkBill();
    workBill.setAttachbilltimes(0);
    workBill.setBuildtime(new Date());
    workBill.setCourier(courier);
    workBill.setOrder(order);
    workBill.setPickstate("新单");
    workBill.setRemark(order.getRemark());
    workBill.setType("新");

    workbillRepository.save(workBill);
//   发送短信,推送一个通知
    // 中断代码的执行
    order.setOrderType("自动分单");

    return;
   }

  }

 }
}
