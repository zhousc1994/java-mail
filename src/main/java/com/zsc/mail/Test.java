package com.zsc.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class Test {

    public static void main(String[] args) {

        // 发送文本
        MailMessage mailMessage = new MailMessage();

        // 创建文件内容
//        try {
//            MimeMultipart mailContent = MailUtil.createMailContent(BodyPartUtil.createTextPart("你好，练绪忠"));
//            mailMessage.setAllFile(mailContent);
//            MailUtil.sendMail(mailMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        // 发送附件
//        try {
//            MimeMultipart mailContent = MailUtil.createMailContent(BodyPartUtil.createAnnexPart("src/main/resources/证明.docx"));
//            mailMessage.setAllFile(mailContent);
//            MailUtil.sendMail(mailMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        // 发送内嵌图片
//        try {
//            MimeBodyPart imgPart = BodyPartUtil.createImgPart("src/main/resources/th.jpg", "th.jpg");
//            MimeMultipart mailContent = MailUtil.createMailContent(BodyPartUtil.createTextPart("请注意，我不是广告<img src='cid:th.jpg'>"),imgPart);
//            mailMessage.setAllFile(mailContent);
//            MailUtil.sendMail(mailMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
        // 发送三种格式
        try {
            MimeBodyPart imgPart = BodyPartUtil.createImgPart("src/main/resources/th.jpg", "th.jpg");
            MimeMultipart mailContent = MailUtil.createMailContent(BodyPartUtil.createTextPart("请注意，我不是广告<img src='cid:th.jpg'>"),imgPart);
            mailMessage.setAllFile(mailContent);
            MailUtil.sendMail(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
