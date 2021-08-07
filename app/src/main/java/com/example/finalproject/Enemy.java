package com.example.finalproject;

import java.io.Serializable;

import android.graphics.Path;

import androidx.appcompat.app.AppCompatActivity;

public class Enemy extends AppCompatActivity implements Serializable {
    public static Path enemyPath;
    public int animation_count;
    private double hp_val;
    public String variant;
    // This is the distance the enemy has traveled along the track for a given level
    // 0.0 is the start of the track, 1.0 is the end
    public double location;
    public double speed;
    public float[] coordLocation;
    private double xLocation;
    private double yLocation;

    public double getxLocation() {
        return xLocation;
    }

    public void setxLocation(double xLocation) {
        this.xLocation = xLocation;
        this.coordLocation=new float[2];
        this.coordLocation[0]= (float) this.xLocation;
        this.coordLocation[1]= (float) this.yLocation;
    }

    public double getyLocation() {
        return yLocation;
    }

    public void setyLocation(double yLocation) {
        this.yLocation = yLocation;
        this.coordLocation=new float[2];
        this.coordLocation[0]= (float) this.xLocation;
        this.coordLocation[1]= (float) this.yLocation;
    }

    public double getHp_val() {
        return hp_val;
    }

    public void setHp_val(double hp_val) {
        this.hp_val = hp_val;
    }

    private void setSpeed() {
        if (this.variant.equals("Enemy1")) {
            this.speed = config.enemy1speed;
        } else if (this.variant.equals("Enemy2")) {
            this.speed = config.enemy2speed;
        } else if (this.variant.equals("Enemy3")) {
            this.speed = config.enemy3speed;
        }
    }

    private void setMax_Health() {
        if (this.variant.equals("Enemy1")) {
            this.setHp_val(config.enemy1health);
        } else if (this.variant.equals("Enemy2")) {
            this.setHp_val(config.enemy2health);
        } else if (this.variant.equals("Enemy3")) {
            this.setHp_val(config.enemy3health);
        }
    }

    public void update_loc() {
        this.location += this.speed;
    }

    public Enemy(String variant) {
        this.animation_count = 0;
        this.location = 0;
        this.variant = variant;
        this.setMax_Health();
        this.setSpeed();
    }

    public Enemy(String variant, double location) {
        this.animation_count = 0;
        this.location = location;
        this.variant = variant;
        this.setMax_Health();
        this.setSpeed();
    }

    public Enemy() {

    }


}
