package com.nowcoder.util;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * @program: wenda
 * @Date: 2018/9/6
 * @Author: chandler
 * @Description:
 */
@Service
public class MailSender implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            String nick = MimeUtility.encodeText("问答系统管理员");
            InternetAddress from = new InternetAddress(nick + "<421789740@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        // 邮箱账号
        mailSender.setUsername("421789740@qq.com");
        // QQ邮箱的授权码
        mailSender.setPassword("ewwpzpegjefdbhid");
        // QQ邮箱SMTP服务器地址
        mailSender.setHost("smtp.qq.com");
        // QQ邮箱端口号
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        // 设置编码格式
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        // 开启SSL加密
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        // 开启请求认证
        javaMailProperties.put("mail.smtp.auth", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
