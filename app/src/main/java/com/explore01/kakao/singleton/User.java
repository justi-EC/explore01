package com.explore01.kakao.singleton;

public class User {

    private String userName;
    private String uid;
    private String email;
    private boolean disabled; //사용자가 중지되었는지 여부. 중지된 경우 true, 사용 설정된 경우 false. 제공하지 않은 경우 기본값은 false.

    private User() {
    }
    //중첩클래스
    private static class Singleton {
        private static final User INSTANCE = new User();
    }
    //Singleton으로 동일한 방법으로 객체 참조.
    public static User getInstance() {
        return Singleton.INSTANCE;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getUid() {
        return uid;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
