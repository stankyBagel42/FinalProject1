package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.finalproject.Level;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static AssetManager assetManager;
    public static Level activeLevel=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager=getApplicationContext().getAssets();
        setContentView(R.layout.activity_main);
        if(MainActivity.activeLevel==null){
            Log.d("ERROR","The active level is null");
        }
        // Set the player as the test user
//        try {
//            Log.d("test","PLAYER ASSIGNED SUCCESSFULLY");
//            Player test = (Player) Manager.objectLoader("PlayerData/testPlayer.ser");
//            Log.d("PLAYER_INFO", test.username);
//            Log.d("PLAYER_INFO",test.levels_completed.toString());
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            Log.d("ERROR", e.getLocalizedMessage());
//            Log.d("ERROR", e.getMessage());
//        }

//        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("test", "Running Test");
//                try {
//                    Test.main();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean create_level=true;
                if(create_level){
                    Level level = new Level(1,"easy");
                    ArrayList<Enemy> wave = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        wave.add(new Enemy("Enemy1"));
                    }
                    level.waves.add(wave);
                    wave.clear();
                    for (int i = 0; i < 5; i++) {
                        wave.add(new Enemy("Enemy1"));
                    }
                    level.waves.add(wave);
                    wave.clear();
                    wave.add(new Enemy("Enemy2"));
                    for (int i = 0; i < 3; i++) {
                        wave.add(new Enemy("Enemy1"));
                        wave.add(new Enemy("Enemy1"));
                    }
                    level.waves.add(wave);
                    try {
                        File file = new File((getApplication()
                                .getApplicationContext().getFileStreamPath("level1.ser")
                                .getPath()));
                        Manager.writeObjectToDisk(level,file.getPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try{
                        Level test=(Level)Manager.objectLoader("LevelData/level1.ser");
                        Log.d("test",Integer.toString(test.level_ID));
                        Log.d("test",Integer.toString(test.current_gold));
                        Log.d("test",Integer.toString(test.current_wave));
                        Log.d("test",Integer.toString(test.lives_remaining));
                        Log.d("test",test.inventory.toString());
                        Log.d("test",test.difficulty);
                    }catch(IOException|ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }

            }
        });
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "Hello");
                // How to start a new activity
                Intent intent = new Intent(view.getContext(), Level_Selection.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

    }


}