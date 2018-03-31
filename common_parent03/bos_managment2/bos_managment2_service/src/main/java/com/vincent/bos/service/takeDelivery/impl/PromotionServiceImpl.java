package com.vincent.bos.service.takeDelivery.impl;

import com.vincent.bos.dao.takeDelivery.PromotionRepository;
import com.vincent.bos.domain.take_divery.Promotion;
import com.vincent.bos.service.takeDelivery.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
