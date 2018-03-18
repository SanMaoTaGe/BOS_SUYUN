package com.vincent.bos.dao.test;

import com.vincent.bos.dao.base.StandardReposity;
import com.vincent.bos.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-12 20:10
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyStandardRepositoryTest {

/* @Autowired
 private StandardReposity standardReposity;

 @Test
 public void fun01() {
  List<Standard> list = standardReposity.findAll();
  System.out.println("list---of Standard:------------\n" + list);
 }

 *//**
  * 增
  *//*
 @Test
 public void fun02() {
  Standard standard = new Standard();
  standard.setName("zhansan");
  standard.setMaxWeight(1);
  standardReposity.save(standard);

 }

 @Test
 public void testUpdate() {
  Standard standard = new Standard();
  // 进行更改操作,必须传入主键
  standard.setId(1L);
  standard.setName("lisi");
  standard.setMaxLength(500);
  standardReposity.save(standard);
 }


 @Test
 public void testFindByName() {

  // 使用用户名进行查询

  List<Standard> list = standardReposity.findByName("zhansan");
  for (Standard standard : list) {
   System.out.println("\n--------------------------\n"+standard+"\n");
  }

 }
 @Test
 public void fun07() {
  List<Standard> list = standardReposity.fun01("zhansan", 1);
  System.out.println("\n--------------------------\n"+list+"\n");
 }*/


}
