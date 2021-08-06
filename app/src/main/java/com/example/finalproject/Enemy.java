package com.example.finalproject;

import java.io.Serializable;

public class Enemy implements Serializable {
    private double hp_val;
    public String variant;
    // This is the distance the enemy has traveled along the track for a given level
    // 0.0 is the start of the track, 1.0 is the end
    public double location;
    public double speed;
    private double xLocation;
    private double yLocation;

    public double getxLocation() {
        return xLocation;
    }

    public void setxLocation(double xLocation) {
        this.xLocation = xLocation;
    }

    public double getyLocation() {
        return yLocation;
    }

    public void setyLocation(double yLocation) {
        this.yLocation = yLocation;
    }

    public double getHp_val() {
        return hp_val;
    }

    public void setHp_val(double hp_val) {
        this.hp_val = hp_val;
    }

    private void setSpeed(){
        if(this.variant.equals("Enemy1")){
            this.speed=config.enemy1speed;
        }else if(this.variant.equals("Enemy2")){
            this.speed=config.enemy2speed;
        }else if(this.variant.equals("Enemy3")){
            this.speed=config.enemy3speed;
        }
    }

    private void setMax_Health(){
        if(this.variant.equals("Enemy1")){
            this.setHp_val(config.enemy1health);
        }else if(this.variant.equals("Enemy2")){
            this.setHp_val(config.enemy2health);
        }else if(this.variant.equals("Enemy3")){
            this.setHp_val(config.enemy3health);
        }
    }

    public void update_loc(){
        this.location+=this.speed;
    }

    public Enemy(String variant){
        this.location=0;
        this.variant=variant;
        this.setMax_Health();
        this.setSpeed();
    }

    public Enemy(String variant, double location){
        this.location=location;
        this.variant=variant;
        this.setMax_Health();
        this.setSpeed();
    }


}
