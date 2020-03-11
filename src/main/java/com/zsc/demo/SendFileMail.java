package com.zsc.demo;

import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 发送复杂邮件
 */
public class SendFileMail {

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

        //4.连接上之后我们需要发送邮件；
        try {
            MimeMessage mimeMessage = imageMail(session);
            ts.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            ts.close();
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static MimeMessage imageMail(Session session) throws MessagingException, UnsupportedEncodingException {
        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("发送人"));
        //邮件接收人，可以同时发送给很多人，我们这里只发给自己；
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("接收人"));
        mimeMessage.setSubject("我也不知道是个什么东西就发给你了"); //邮件主题

          /*
        编写邮件内容
        1.图片
        2.附件
        3.文本
         */
        //图片
        MimeBodyPart body1 = new MimeBodyPart();
        body1.setDataHandler(new DataHandler(new FileDataSource("src/main/resources/th.jpg")));
        body1.setContentID("th.jpg"); //图片设置ID

        //文本
        MimeBodyPart body2 = new MimeBodyPart();
        body2.setContent("请注意，我不是广告<img src='cid:th.jpg'>","text/html;charset=utf-8");

        //附件
        MimeBodyPart body3 = new MimeBodyPart();
        body3.setDataHandler(new DataHandler(new FileDataSource("src/main/resources/smartOs.txt")));
        body3.setFileName("smartOs.txt"); //附件设置名字

        MimeBodyPart body4 = new MimeBodyPart();
        body4.setDataHandler(new DataHandler(new FileDataSource("src/main/resources/证明.docx")));
//        body4.setFileName("证明.docx"); //附件设置名字
        // 解决文件名乱码
        BASE64Encoder enc = new BASE64Encoder();
        String fileName="=?UTF-8?B?"+enc.encode("证明.docx".getBytes("utf-8"))+"?=";
        body4.setFileName( fileName);

        //拼装邮件正文内容
        MimeMultipart multipart1 = new MimeMultipart();
//        multipart1.addBodyPart(body1);
        multipart1.addBodyPart(body4);
        multipart1.setSubType("related"); //1.文本和图片内嵌成功！

        //new MimeBodyPart().setContent(multipart1); //将拼装好的正文内容设置为主体
//        MimeBodyPart contentText =  new MimeBodyPart();
//        contentText.setContent(multipart1);

        //拼接附件
//        MimeMultipart allFile =new MimeMultipart();
//        allFile.addBodyPart(body3); //附件
//        allFile.addBodyPart(body4); //附件
//        allFile.addBodyPart(contentText);//正文
//        allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed；

        //放到Message消息中
        mimeMessage.setContent(multipart1);
        mimeMessage.saveChanges();//保存修改

        return mimeMessage;

    }

}
