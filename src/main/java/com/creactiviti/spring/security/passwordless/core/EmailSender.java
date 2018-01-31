package com.creactiviti.spring.security.passwordless.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author Arik Cohen
 * @since Jan 30, 2018
 */
public class EmailSender implements Sender {
  
  @Value("${passwordless.email.from}")
  private String from;
  
  private final JavaMailSender mailSender;
  
  public EmailSender(JavaMailSender aMailSender) {
    mailSender = aMailSender;
  }

  @Override
  public void send (String aUserId, String aToken) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    
    mailMessage.setFrom(from);
    mailMessage.setTo(aUserId);
    mailMessage.setSubject("Your signin link");
    mailMessage.setText(String.format("Hello!\nAccess your account here: http://localhost:8080/signin/%s?uid=%s",aToken,aUserId));
    
    mailSender.send(mailMessage);
  }

}
