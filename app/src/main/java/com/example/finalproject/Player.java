package com.example.finalproject;

import java.io.Serializable;
import java.util.Hashtable;

public class Player implements Serializable {
    private int experience;
    private Hashtable<String,Double> stats;
    public String username;
    public Hashtable<Level,Boolean> levels_completed;

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Hashtable<String, Double> getStats() {
        return stats;
    }

    public void setStat(String stat_name,double value) {
        this.stats.replace(stat_name,value);
    }

    public Player(String name){
        this.username=name;
        this.experience=0;
        this.stats=new Hashtable<>();
        this.stats.put("Time Played",0.0);
        this.stats.put("Damage Dealt",0.0);
        this.stats.put("Enemies Slain",0.0);
        this.stats.put("Gold Earned",0.0);
        this.stats.put("Waves Cleared",0.0);
    }
}
