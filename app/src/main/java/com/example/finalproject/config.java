package com.example.finalproject;

import android.graphics.Color;

// This is where the constants of the game are stored
public class config {
    // IMAGE RESIZING
    public static double towerScaleX=0.5;
    public static double towerScaleY=0.5;
    public static double enemyScaleX=0.5;
    public static double enemyScaleY=0.5;
    // ENEMY TINTS
    // Different digits are in hex to represent these values:
    // 0xAARRGGBB
    public static int enemy1tint = 0xFFFFFFFF;
    public static int enemy2tint = 0xFFFF0000;
    public static int enemy3tint = 0xFF0000FF;

    // GAME STATS
    public static int startingLife = 20;
    public static int waveRewardBase = 50;
    // TOWER STATS
    public static int tower1Cost = 10;
    public static double tower1AtkSpeed = 2;
    public static double tower1AtkRange = 50;
    public static double tower1AtkDamage = 5;
    public static int tower2Cost = 50;
    public static double tower2AtkSpeed = 0;
    public static double tower2AtkRange = 100;
    public static double tower2AtkDamage = 0;
    // attackTimerBase / atk_speed = frames between attacks
    public static double attackTimerBase = 30;
    // ENEMY STATS
    public static double enemy1Reward = 2;
    public static double enemy1speed = 0.1;
    public static double enemy1health = 10;
    public static double enemy2Reward = 4;
    public static double enemy2speed = 0.1;
    public static double enemy2health = 15;
    public static double enemy3Reward = 8;
    public static double enemy3speed = 0.15;
    public static double enemy3health = 15;
    // DIFFICULTY WAVES
    public static int easy_waves = 10;
    public static int normal_waves = 20;
    public static int hard_waves = 30;

}
