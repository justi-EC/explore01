package com.explore01.kakao.singleton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comment {

    private String author;
    private String text;
    private String timeStamp;
    private String uid;

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public Comment() {
    }

    public Comment(String author, String text) {
        this.author = author;
        this.text = text;
        this.timeStamp = timeStamp();
        this.uid = User.getInstance().getUid();
    }

    private static String timeStamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd.", Locale.KOREA);

        return dateFormat.format(date);
    }

    public static Comment newComment( String userName, String message) {
        return new Comment(userName, message);
    }

}
