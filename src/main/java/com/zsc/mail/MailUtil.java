package com.zsc.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * 邮件工具类
 */
public class MailUtil {

    public static final String CONFIG_PATH = System.getProperty("user.dir") + "/src/main/resources/";

    // 发送邮件的回话
    private static Session session;

    
    static {
        try {
            Properties properties = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG_PATH+"mail.properties"));
            properties.load(bufferedReader);
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 发送人邮件用户名、授权码
                    return new PasswordAuthentication(properties.getProperty("mail.username"), properties.getProperty("mail.authcode"));
                }
            });
            session.setDebug(Boolean.valueOf(properties.getProperty("mail.session.debug")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     * @param message 邮件实体
     */
    public static void sendMail (MailMessage message){
        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        try {
            mimeMessage.setFrom(new InternetAddress(message.getSender()));
            //邮件接收人，可以同时发送给很多人，我们这里只发给自己；
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(message.getReceiver()));
            mimeMessage.setSubject(message.getSubject()); //邮件主题
            mimeMessage.setContent(message.getAllFile());
            mimeMessage.saveChanges();

            //2、通过session得到transport对象
            Transport ts = session.getTransport();
            //3、使用邮箱的用户名和授权码连上邮件服务器
            ts.connect();
            ts.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            ts.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 邮件内容只有文本或附件
     * @param part 具体文本内容对象
     * @return
     */
    public static MimeMultipart createMailContent(MimeBodyPart part){
        MimeMultipart multipart = new MimeMultipart();
        try {
            multipart.addBodyPart(part);
            multipart.setSubType("related");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return multipart;
    }

    /**
     * 邮件内容有文本及内嵌图片
     * @param text 具体文本内容对象
     * @param imgs 内嵌图片，可多传
     * @return
     */
    public static MimeMultipart createMailContent(MimeBodyPart text,MimeBodyPart ...imgs){
        MimeMultipart multipart = new MimeMultipart();
        try {
            for (MimeBodyPart img : imgs) {
                multipart.addBodyPart(img);
            }
            multipart.addBodyPart(text);
            multipart.setSubType("related");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return multipart;
    }

    /**
     * 邮件内容有文本及内嵌图片及附件
     * @param text 具体文本内容对象
     * @param imgs 内嵌图片，可多传(可不传)
     * @param annexs 附件，可多传
     * @return 邮件内容
     */
    public static MimeMultipart createMailContent(MimeBodyPart text, List<MimeBodyPart> annexs, MimeBodyPart ...imgs){
        //拼装邮件正文内容
        MimeMultipart textPart = new MimeMultipart();
        MimeMultipart allFile = null;
        try {
            if(imgs.length != 0){
                for (MimeBodyPart img : imgs) {
                    textPart.addBodyPart(img);
                }
            }
            textPart.addBodyPart(text);
            textPart.setSubType("related");

            //将拼装好的正文内容设置为主体
            MimeBodyPart contentText =  new MimeBodyPart();
            contentText.setContent(textPart);

            //拼接附件
            allFile =new MimeMultipart();
            for (MimeBodyPart annex :annexs){
                allFile.addBodyPart(annex);
            }
            allFile.addBodyPart(contentText);//正文
            allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed；
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return allFile;
    }
}
