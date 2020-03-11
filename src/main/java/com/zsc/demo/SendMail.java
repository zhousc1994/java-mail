package com.zsc.demo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送带普通的邮件
 */
public class SendMail {


    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.163.com");//设置发送邮件服务器
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

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指明邮件的发件人
        try {
            message.setFrom(new InternetAddress("发送人"));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("接收人"));
            //邮件的标题
            message.setSubject("周工的简历");

            //邮件的文本内容
            message.setContent("你好啊！秘钥为sdsdsdsddsdsdsdsdsdsdsdsffsf,", "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());

            ts.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }







    }
}
