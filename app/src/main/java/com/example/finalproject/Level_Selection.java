package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Level_Selection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        // Represent the spinners in code so we can access their selected values
        Spinner level_select = findViewById(R.id.level_selection);
        Spinner difficulty_select = findViewById(R.id.difficulty_selection);
        SpinnerActivity level_viewer = new SpinnerActivity(level_select);
        SpinnerActivity difficulty_viewer = new SpinnerActivity(difficulty_select);
        findViewById(R.id.back_level_selection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.level_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String level=level_viewer.selected;
                if(Player.currentUser.can_play(level.charAt(level.length()-1))){
                    Intent intent = new Intent(view.getContext(), Game.class);
                    startActivity(intent);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "You have to beat the level before in order to play the next level";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,text,duration);
                    toast.show();
                }
            }
        });

    }
}
