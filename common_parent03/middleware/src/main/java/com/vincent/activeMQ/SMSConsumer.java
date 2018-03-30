package com.vincent.activeMQ;

import com.aliyuncs.exceptions.ClientException;
import com.vincent.utils.SmsUtils;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Vincent
 * @Description:
 * @create 2018-03-25 15:49
 */
@Component
public class SMSConsumer implements MessageListener {

 @Override
 public void onMessage(Message message) {
  MapMessage mapMessage = (MapMessage) message;

  try {
   String code = mapMessage.getString("code");
   String tel = mapMessage.getString("tel");
   SmsUtils.sendSms(tel, code);
   System.out.println("\ntel&code----" + tel + "--------" + code);

  } catch (JMSException e) {
   e.printStackTrace();
  } catch (ClientException e) {
   e.printStackTrace();
  }
 }
}
