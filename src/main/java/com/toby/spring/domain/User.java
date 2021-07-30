package com.toby.spring.domain;


import javax.validation.constraints.NotNull;

public class User {
    @NotNull
    private String name;
    private int age;
    private String hobby;

    @NotNull
    private Level level;
    private int recommendCnt;
    private int loginCnt;

    public User() {
    }

    public User(String name, int age, String hobby, Level level, int recommendCnt, int loginCnt) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.level = level;
        this.recommendCnt = recommendCnt;
        this.loginCnt = loginCnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getRecommendCnt() {
        return recommendCnt;
    }

    public void setRecommendCnt(int recommendCnt) {
        this.recommendCnt = recommendCnt;
    }

    public int getLoginCnt() {
        return loginCnt;
    }

    public void setLoginCnt(int loginCnt) {
        this.loginCnt = loginCnt;
    }

    public void upgradeLevel() {
        Level nextLevel=level.nextLevel();
        if(nextLevel==null){
            throw new IllegalStateException("cannot upgrade next Level");
        }else{
            level=nextLevel;
        }
    }
}
