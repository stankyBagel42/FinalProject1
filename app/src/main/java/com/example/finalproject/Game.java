package com.example.finalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.audiofx.Equalizer;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Game extends AppCompatActivity {
    public static boolean placing_tower = false;
    public static Tower being_placed;
    private DrawingView drawView;
    private Date date;
    private float time_since_spawn;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String level=null;
        String difficulty=null;
        setContentView(R.layout.activity_game);
        drawView = (DrawingView) findViewById(R.id.drawing_view);
        if(Level_Selection.level_viewer!=null){
            level = Level_Selection.level_viewer.selected;
        }
        if(Level_Selection.difficulty_viewer!=null){
            difficulty = Level_Selection.difficulty_viewer.selected;
        }
        // Set active level to the selected level/difficulty
        // Set the file path to the active level
        File activeLevel_File = new File(String.format(Locale.US, "Assets/LevelData/level%c", level.charAt(level.length() - 1)));
        Level activeLevel = new Level(level.charAt(level.length() - 1), difficulty);
        // If the level path already exists, it has been played so we load it
        if (activeLevel_File.exists()) {
            try {
                activeLevel = (Level) Manager.objectLoader(activeLevel_File.getName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        findViewById(R.id.game_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!placing_tower) {
                    Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                    startActivity(intent);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "You must finish placing the tower before doing other things. You may click store again to cancel the placing";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        findViewById(R.id.store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If they are placing a tower and then come back to the store, cancel the first placement
                placing_tower = false;
                Intent intent = new Intent(view.getContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.next_wave_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!placing_tower) {
                    // Get rid of the "next wave" button from view
                    MainActivity.activeLevel.stopped=false;
                    findViewById(R.id.next_wave_button).setVisibility(View.INVISIBLE);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "You must finish placing the tower before doing other things. You may click store again to cancel the placing";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                };
            }
        });

        ImageView imageView = findViewById(R.id.imageView);
        final Bitmap bitmap = (((BitmapDrawable) imageView.getDrawable()).getBitmap());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Log.d("test", "onTouch: "+event.toString());
                Log.d("test", "onTouch: LOCATION: ("+event.getX()+","+event.getY()+")");
                if (placing_tower) {
                    int x = (int) event.getY();
                    int y = (int) event.getX();
                    if(x<bitmap.getWidth()-2 && x>2 && y>2 && y<bitmap.getHeight()-2){
                        int pixel;
                        try{
                            pixel = bitmap.getPixel(x, y);
                        }catch(IllegalArgumentException e){
                            e.printStackTrace();
                            Log.d("test", "onTouch: CAUGHT EXCEPTION DURING PIXEL RETRIEVAL");
                            Log.d("test","INPUT: ("+x+","+")");
                            throw e;
                        }
                        boolean valid = true;
                        // Pixels around the selected spot by the user (5x5 Search radius)
                        for (int i = -2; i <= 2; i++) {
                            for (int j = -2; j <= 2; j++) {
                                if (Math.abs(Color.red(pixel) - 102) < 5 && Math.abs(Color.green(pixel) - 100) < 5 && Math.abs(Color.blue(pixel) - 100) < 5) {
                                    valid = false;
                                    Log.d("test", "onTouch: Invalid Location");
                                }
                            }
                        }
                        // If none of the pixels in a 5x5 centered on the selected pixel is the road, it can be placed
                        if (valid) {
                            //PLACE THE TOWER
                            being_placed.xLocation = x;
                            being_placed.yLocation = y;
                            placing_tower = false;
                            MainActivity.activeLevel.towers.add(being_placed);
                            Log.d("test", "onTouch: Placed tower");
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "You cannot place a tower there, it is too close to the path!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                    else{
                        Context context = getApplicationContext();
                        CharSequence text = "You cannot place a tower there, it is off screen!";
                        Log.d("TOWER_FAIL","("+event.getX()+","+event.getY()+")");
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    MainActivity.activeLevel.inventory.add(new Tower("Tower1"));
                }
                return false;
            }
        });
        drawView.invalidate();
        drawView.requestLayout();

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        super.onPause();
        drawView.stopped=true;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        super.onResume();
        drawView.stopped=false;
        drawView.invalidate();
        drawView.requestLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Path path = new Path();
        // The start and end are marked by text views, as well as every turn
        TextView enemy_start = findViewById(R.id.enemy_spawn);
        TextView enemy_end = findViewById(R.id.enemy_end);
//        Point[] points = new Point[8];
        int[] point = new int[2];
        enemy_start.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        // Define all of the turning points

        TextView turn_1 = findViewById(R.id.turn_1);
        TextView turn_2 = findViewById(R.id.turn_2);
        TextView turn_3 = findViewById(R.id.turn_3);
        TextView turn_4 = findViewById(R.id.turn_4);
        TextView turn_5 = findViewById(R.id.turn_5);
        TextView turn_6 = findViewById(R.id.turn_6);
        turn_1.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        turn_2.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        turn_3.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        turn_4.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        turn_5.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        turn_6.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        enemy_end.getLocationOnScreen(point);
        path.lineTo(point[0], point[1]);
        // Save the path in the static enemy path
        Enemy.enemyPath = path;
        float[] temp = new float[]{0.0f,0.0f};
        for (int i = 0; i < MainActivity.activeLevel.waves.size(); i++) {
            for (Enemy e: MainActivity.activeLevel.waves.get(i)){
                PathMeasure pm = new PathMeasure(path,false);
                pm.getPosTan((float)e.location*pm.getLength(),temp,null);
                e.setxLocation(temp[0]);
                e.setyLocation(temp[1]);
            }
        }
        ArrayList<float[]> actual_points=new ArrayList<>();
        float[] approximate_path_points=new float[6];
        approximate_path_points=path.approximate(0.5f);
        for (int i = 0; i < approximate_path_points.length-1; i+=2) {
            temp[0]=approximate_path_points[i];
            temp[1]=approximate_path_points[i+1];
            actual_points.add(temp.clone());
        }
        for (float[] points:actual_points) {
            Log.d("enemy_path", Arrays.toString(points));
        }

    }

    // Update the game state
    public void update(){
        if(!MainActivity.activeLevel.stopped){
            // Update the active level (move enemies, update towers, etc.)
            MainActivity.activeLevel.update();

        }else{
            findViewById(R.id.next_wave_button).setVisibility(View.VISIBLE);
        }
    }
}