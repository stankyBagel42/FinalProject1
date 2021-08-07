package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        TextView current_gold = (TextView) findViewById(R.id.current_coins);
        CharSequence text = Integer.toString(MainActivity.activeLevel.current_gold);
        current_gold.setText(text);
        ArrayList<Tower> tower1_array = new ArrayList<>();
        ArrayList<Tower> tower2_array = new ArrayList<>();
        for (Tower t : MainActivity.activeLevel.inventory) {
            if (t.variant.equals("Tower1")) {
                tower1_array.add(t);
            } else if (t.variant.equals("Tower2")) {
                tower2_array.add(t);
            }
        }
        if (tower1_array.size() > 0) {
            Button buy1 = (Button) findViewById(R.id.buy1);
            text = "Place";
            buy1.setText(text);
        }
        if (tower2_array.size() > 0) {
            Button buy2 = (Button) findViewById(R.id.buy2);
            text = "Place";
            buy2.setText(text);
        }
        findViewById(R.id.back_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.buy1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button buy1 = (Button) findViewById(R.id.buy1);
                if (buy1.getText().equals("Place")) {
                    Intent intent = new Intent(view.getContext(), Game.class);
                    Game.placing_tower = true;
                    Game.being_placed = new Tower(tower1_array.get(0));
                    // Remove the tower from the inventory whenever it is "placed"
                    MainActivity.activeLevel.inventory.remove(tower1_array.get(0));
                    CharSequence text="Buy";
                    buy1.setText(text);
                    finish();
                    startActivity(intent);
                } else {
                    if (MainActivity.activeLevel.current_gold >= config.tower1Cost) {
                        MainActivity.activeLevel.current_gold -= config.tower1Cost;
                        MainActivity.activeLevel.inventory.add(new Tower("Tower1"));
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = String.format(Locale.US, "You have to earn %d gold to buy this tower!", config.tower1Cost);
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
        });
        findViewById(R.id.buy2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.activeLevel.current_gold >= config.tower2Cost) {
                    MainActivity.activeLevel.current_gold -= config.tower2Cost;
                    MainActivity.activeLevel.inventory.add(new Tower("Tower2"));
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = String.format(Locale.US, "You have to earn %d gold to buy this tower!", config.tower2Cost);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

    }
}