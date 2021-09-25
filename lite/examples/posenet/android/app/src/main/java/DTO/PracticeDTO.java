package DTO;

import java.io.Serializable;

public class PracticeDTO {
    private String name; //운동이름
    private int count; //횟수
    private String level; //good, bad, normal
    private int levelCount; //lv별 횟
    //Level level;수

    public String getName(){
        return this.name;
    }
    public int getCount(){
        return this.count;
    }
    public String getLevel(){
        return this.level;
    }
    public int getLevelCount(){
        return this.levelCount;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setCount(int count){
        this.count = count;
    }
    public void setLevel(String level){
        this.level = level;
    }
    public void setLevelCount(int levelCount){
        this.levelCount = levelCount;
    }
}

enum Level {
    GOOD, BAD, NORMAL;
}