package com.vincent.bos.domain.take_divery;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * ClassName:PageBean <br/>
 * Function: <br/>
 * Date: 2018年3月31日 上午10:16:58 <br/>
 */
// cxf框架在传输数据的时候,无法对Page对象进行转换,因为这个类是Spring框架提供的,
// 我们无法在这个类上方增加一个@XmlRootElement注解
// 所以需要自己构建一个对象,来封装分页查询的数据

@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

 private List<T> list;
 private long total;

 public List<T> getList() {
  return list;
 }

 public void setList(List<T> list) {
  this.list = list;
 }

 public long getTotal() {
  return total;
 }

 public void setTotal(long total) {
  this.total = total;
 }

}
