package com.explore01.kakao.singleton;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Post {

    private String title;
    private String contents;
    private String author;
    private String timeStamp;
    private String authorUid;
    private int commentCount;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getCommentCount() {
        return commentCount;
    }


    public Post(String author, String title, String contents, int commentCount) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.timeStamp = timeStamp();
        this.authorUid = User.getInstance().getUid();
        this.commentCount = commentCount;
    }

    private String timeStamp() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd.", Locale.KOREA);

        return dateFormat.format(date);
    }

    public static Post newPost( String userName, String title, String contents, int commentCount) {
        return new Post(userName, title, contents, commentCount);
    }






}
