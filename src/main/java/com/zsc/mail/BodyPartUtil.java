package com.zsc.mail;

import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.UnsupportedEncodingException;

/**
 * 获取所有邮件文本类型
 */
public class BodyPartUtil {

    /**
     * 创建图片内容
     * @param url 本地文件路径
     * @param contentID 对应文件中的 imgID
     */
    public static MimeBodyPart createImgPart(String url,String contentID) throws MessagingException {
        MimeBodyPart body = new MimeBodyPart();
        body.setDataHandler(new DataHandler(new FileDataSource(url)));
        body.setContentID(contentID);
        return body;
    }

    /**
     * 创建文本内容
     * @param content 文本内容
     */
    public static MimeBodyPart createTextPart(String content) throws MessagingException {
        MimeBodyPart body = new MimeBodyPart();
        body.setContent(content,"text/html;charset=utf-8");
        return body;
    }

    /**
     * 创建附件内容
     * @param fileUrl 附件所在本地路径
     */
    public static MimeBodyPart createAnnexPart(String fileUrl) throws UnsupportedEncodingException, MessagingException {
        MimeBodyPart body = new MimeBodyPart();
        FileDataSource fileDataSource = new FileDataSource("src/main/resources/smartOs.txt");
        body.setDataHandler(new DataHandler(fileDataSource));
        BASE64Encoder enc = new BASE64Encoder();
        String fileName="=?UTF-8?B?"+enc.encode(fileDataSource.getFile().getName().getBytes("utf-8"))+"?=";
        body.setFileName( fileName);
        return body;
    }
}
