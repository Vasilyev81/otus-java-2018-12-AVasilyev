package ru.otus.backend.chatService.dto;

import java.util.Date;

public class ChatMessage {
    private Date createDate;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage(String text) {
        this.createDate = new Date();
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
