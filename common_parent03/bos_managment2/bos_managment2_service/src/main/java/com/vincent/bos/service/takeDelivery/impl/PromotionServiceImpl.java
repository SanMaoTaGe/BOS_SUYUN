package com.vincent.bos.service.takeDelivery.impl;

import com.vincent.bos.dao.takeDelivery.PromotionRepository;
import com.vincent.bos.domain.take_divery.PageBean;
import com.vincent.bos.domain.take_divery.Promotion;
import com.vincent.bos.service.takeDelivery.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-31 16:41
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

 @Autowired
 private PromotionRepository promotionRepository;

 @Override
 public void save(Promotion promotion) {
  promotionRepository.save(promotion);
 }

 @Override
 public Page<Promotion> pageQuery(Pageable pageable) {
  return promotionRepository.findAll(pageable);
 }

 @Override
 public PageBean<Promotion> pageQuery4Fore(int page, int size) {
  // 查询数据

  PageBean<Promotion> pageBean = new PageBean<>();

  Pageable pageable = new PageRequest(page, size);
  Page<Promotion> p =pageQuery(pageable);
  System.out.println("后台list---------------------------"+p.getContent());
  pageBean.setList(p.getContent());
  pageBean.setTotal(p.getTotalElements());
  return pageBean;



 }
}
