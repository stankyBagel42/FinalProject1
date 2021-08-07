package com.example.finalproject;

import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Hashtable;

public class Player  extends AppCompatActivity implements Serializable{
    public static Player currentUser;
    private int experience;
    private Hashtable<String,Double> stats;
    public String username;
    public Hashtable<Integer,Boolean> levels_completed;

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
        this.stats.remove(stat_name);
        this.stats.put(stat_name,value);
    }

    public boolean can_play(int level_id){
        return level_id==0 || levels_completed.get(level_id-1);
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
        for (int i = 0; i < Array.getLength(R.array.levels); i++) {
            levels_completed.put(i,Boolean.FALSE);
        }
    }

    public Player(){

    }
}
