package com.zsc.mail;

import javax.mail.internet.MimeMultipart;

public class MailMessage {

    //邮件发送人邮箱
    private String sender;

    //邮件接收人邮箱
    private String receiver;

    // 邮件主题
    private String subject;

    // 邮件内容（可以有文本/图片/附件）
    private MimeMultipart allFile;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MimeMultipart getAllFile() {
        return allFile;
    }

    public void setAllFile(MimeMultipart allFile) {
        this.allFile = allFile;
    }
}
