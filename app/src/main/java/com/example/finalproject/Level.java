package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable {
    public int level_ID;
    public ArrayList<Tower> inventory;
    public ArrayList<Tower> towers;
    public ArrayList<ArrayList<Enemy>> waves;
    public int current_wave;
    public int lives_remaining;
    public int current_gold;
    public String difficulty;

    // Update All Towers+Enemies
    // Check If any enemies died
    // If they did, add gold equal to their level and delete the objects
    public void update() {
        for (Tower t : this.towers) {
            t.update(this.waves.get(this.current_wave), this.towers);
        }
        ArrayList<Enemy> dead_enemies=new ArrayList<>();
        // Loop thru all of the enemies, add them to dead enemies if they have died and reward gold
        // otherwise just update their location
        for (Enemy e : this.waves.get(this.current_wave)) {
            if (e.getHp_val() > 0) {
                e.update_loc();
            } else {
                if (e.variant.equals("Enemy1")) {
                    this.current_gold += config.enemy1Reward;
                } else if (e.variant.equals("Enemy2")) {
                    this.current_gold += config.enemy2Reward;
                } else if (e.variant.equals("Enemy3")) {
                    this.current_gold += config.enemy3Reward;
                }
                dead_enemies.add(e);
            }
            // If an enemy made it past your defences, it is "dead" (removed from the wave) and remove a life
            if (e.location>=1.0){
               dead_enemies.add(e);
               this.lives_remaining-=1;
            }
        }
        // Remove all killed enemies from the list of enemies in this wave
        for (Enemy e:dead_enemies) {
            this.waves.get(this.current_wave).remove(e);
        }
    }

    public Level(int level_ID, String difficulty) {
        this.level_ID = level_ID;
        this.difficulty = difficulty;
        this.current_wave = 0;
        this.current_gold = 0;
        this.inventory = new ArrayList<>();
        this.inventory.add(new Tower("Tower1"));
        this.towers = new ArrayList<>();
        this.waves = new ArrayList<>();
        this.lives_remaining = config.startingLife;
    }

    // Used to create a "Base Level"
    public Level(int level_ID) {
        this.level_ID = level_ID;
        this.current_wave = 0;
        this.current_gold = 0;
        this.inventory = new ArrayList<>();
        this.inventory.add(new Tower("Tower1"));
        this.towers = new ArrayList<>();
        this.waves = new ArrayList<>();
        this.lives_remaining = config.startingLife;
    }
}
