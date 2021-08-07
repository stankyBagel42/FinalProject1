package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static AssetManager assetManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetManager=getApplicationContext().getAssets();
        setContentView(R.layout.activity_main);
        // Set the player as the test user
        try {
            Log.d("test","PLAYER ASSIGNED SUCCESSFULLY");
            Player test = (Player) Manager.objectLoader("PlayerData/testPlayer.ser");
            Log.d("PLAYER_INFO", test.username);
            Log.d("PLAYER_INFO",test.levels_completed.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.d("ERROR", e.getLocalizedMessage());
            Log.d("ERROR", e.getMessage());
        }

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