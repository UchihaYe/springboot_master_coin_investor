package com.crypto.invest.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;  // 发件人邮箱，使用配置文件中的邮箱地址

    /**
     * 发送简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);  // 设置发件人
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("简单邮件发送成功！收件人: {}", to);
        } catch (Exception e) {
            log.error("发送简单邮件时发生错误！", e);
            throw e;
        }
    }

    /**
     * 发送HTML格式的邮件
     *
     * @param to          收件人
     * @param subject     主题
     * @param htmlContent HTML内容
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);  // 设置发件人
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("HTML邮件发送成功！收件人: {}", to);
        } catch (MessagingException e) {
            log.error("发送HTML邮件时发生错误！", e);
            throw new RuntimeException("发送HTML邮件失败", e);
        }
    }
} 