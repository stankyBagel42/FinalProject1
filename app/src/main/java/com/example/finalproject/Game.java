package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Point;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Spinner level_select = findViewById(R.id.level_selection);
        Spinner difficulty_select = findViewById(R.id.difficulty_selection);
        SpinnerActivity level_viewer = new SpinnerActivity(level_select);
        SpinnerActivity difficulty_viewer = new SpinnerActivity(difficulty_select);
        String level = level_viewer.selected;
        String difficulty = difficulty_viewer.selected;
        // Set active level to the selected level/difficulty
        // Set the file path to the active level
        File activeLevel_File = new File(String.format(Locale.US, "Assets/LevelData/%c%s", level.charAt(level.length() - 1), difficulty));
        Level activeLevel;
        // If the level path already exists, it has been played so we load it
        if (activeLevel_File.exists()) {
            try {
                activeLevel = (Level) Manager.objectLoader(activeLevel_File.getName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            activeLevel = new Level(level.charAt(level.length() - 1), difficulty);
        }
        Path path = new Path();
        // The start and end are marked by text views, as well as every turn
        TextView enemy_start=findViewById(R.id.enemy_spawn);
        TextView enemy_end=findViewById(R.id.enemy_end);
        Point[] points = new Point[8];
        int[] point = new int[2];
        enemy_start.getLocationOnScreen(point);
        points[0]=new Point(point[0],point[1]);
        // The turn view and id (will be used for all of the turns)
        TextView turn; int id;
        Resources res = getResources();
        Log.d("test", points[0].toString());
        for (int i = 1; i <= 6; i++) {
            // Use the resources to get the int id for the turn number
            id=res.getIdentifier(String.format(Locale.US,"turn_%d",i),"id",getApplicationContext().getPackageName());
            turn=findViewById(id);
            // find the location of the text view
            turn.getLocationOnScreen(point);
            points[i]=new Point(point[0],point[1]);
            Log.d("test",points[i].toString());
        }

        findViewById(R.id.game_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

    }
}