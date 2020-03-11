package com.zsc.demo;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * MIME（多用途互联网邮件扩展类型）
 * MimeBodyPart类:表示的是一个MIME消息，它和MimeMessage类一样都是从Part接口继承过来。
 * MimeMultipart类:是抽象类 Multipart的实现子类,它用来组合多个MIME消息。一个MimeMultipart对象可以包含多个代表MIME消息的MimeBodyPart对象。
 */
public class SendMailMime {

    /**
     * 创建包含内嵌图片的邮件
     */
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.qq.com");//设置发送邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码


        //创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 发送人邮件用户名、授权码
                return new PasswordAuthentication("发送用户名", "授权码");
            }
        });
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport();
            //3、使用邮箱的用户名和授权码连上邮件服务器
            ts.connect();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //创建邮件
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("发送人"));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("接收人"));
            //邮件的标题
            message.setSubject("练大佬的生活照sdsdsdsdsddsds");

            // 准备图片数据
            MimeBodyPart image = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource("src/resources/th.jpg"));
            image.setDataHandler(dh);
            image.setContentID("th.jpg");

            // 准备正文数据
            MimeBodyPart text = new MimeBodyPart();
            text.setContent("dsdasdasdasdsa的图片<img src='cid:th.jpg'>，好看吗？收到请回复dsdssdsdsdsdsdsddsdsdsdsdsds", "text/html;charset=UTF-8");

            // 描述数据关系
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text);
            mm.addBodyPart(image);
            mm.setSubType("related");

            //设置到消息中，保存修改
            message.setContent(mm);
            message.saveChanges();

            ts.sendMessage(message, message.getAllRecipients());
            ts.close();

        }catch (MessagingException e){
            e.printStackTrace();
        }

    }
}
