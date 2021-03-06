package com.autumnframework.cms.extern.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author Junlan Shuai[shuaijunlan@gmail.com].
 * @date Created on 15:14 2017/11/6.
 */
@Component
public class WebEmail {
    private static Logger logger = LogManager.getLogger(WebEmail.class);

    @Resource(name = "webEmailSender")
    private JavaMailSenderImpl mailSender;
    @Resource(name = "smg")
    private SimpleMailMessage MailMessage;

    public void send(String subject, String content) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessage.setContent(content, "text/html;charset=utf-8");
            messageHelper.setFrom(MailMessage.getFrom());
            messageHelper.setSubject(subject); //主题
//            messageHelper.setText(content);   //内容
            messageHelper.setTo(MailMessage.getTo()); //发送给
            messageHelper.setCc(MailMessage.getFrom()); //抄送

            mailSender.send(mimeMessage);    //发送邮件

        } catch (Exception e) {
            logger.error("the email send error ! {}", e);
        }
    }
}
