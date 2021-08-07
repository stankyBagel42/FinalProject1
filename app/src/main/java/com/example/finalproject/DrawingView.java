package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {
    public boolean stopped;
    public Bitmap[] enemyAnimations;
    public Bitmap[] towerVariants;
    public Bitmap[][] enemyVariants;
    private Paint canvasPaint;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
        stopped = false;
    }
    public Bitmap makeTintedBitmap(Bitmap src, int color) {
        Bitmap result = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas c = new Canvas(result);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(color,0));
        c.drawBitmap(src, 0, 0, paint);
        return result;
    }
    private void setupDrawing() {
        Log.d("test", "setupDrawing was called");
        canvasPaint = new Paint();
        enemyAnimations = new Bitmap[14];
        enemyVariants = new Bitmap[3][14];
        towerVariants = new Bitmap[2];
        enemyAnimations[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bd1);
        enemyAnimations[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bd2);
        enemyAnimations[2] = BitmapFactory.decodeResource(getResources(), R.drawable.bd3);
        enemyAnimations[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bd4);
        enemyAnimations[4] = BitmapFactory.decodeResource(getResources(), R.drawable.bd5);
        enemyAnimations[5] = BitmapFactory.decodeResource(getResources(), R.drawable.bd6);
        enemyAnimations[6] = BitmapFactory.decodeResource(getResources(), R.drawable.bd7);
        enemyAnimations[7] = BitmapFactory.decodeResource(getResources(), R.drawable.bd8);
        enemyAnimations[8] = BitmapFactory.decodeResource(getResources(), R.drawable.bd9);
        enemyAnimations[9] = BitmapFactory.decodeResource(getResources(), R.drawable.bd10);
        enemyAnimations[10] = BitmapFactory.decodeResource(getResources(), R.drawable.bd11);
        enemyAnimations[11] = BitmapFactory.decodeResource(getResources(), R.drawable.bd12);
        enemyAnimations[12] = BitmapFactory.decodeResource(getResources(), R.drawable.bd13);
        enemyAnimations[13] = BitmapFactory.decodeResource(getResources(), R.drawable.bd14);
        towerVariants[0] = BitmapFactory.decodeResource(getResources(), R.drawable.tower1);
        towerVariants[1] = BitmapFactory.decodeResource(getResources(), R.drawable.tower2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 14; j++) {
                if(i==0){
                    enemyVariants[i][j]=Bitmap.createScaledBitmap(enemyAnimations[j],
                            (int) Math.floor(enemyAnimations[j].getWidth()*config.enemyScaleX),
                            (int) Math.floor(enemyAnimations[j].getHeight()*config.enemyScaleY),
                            false);
                }else if(i==1){
                    enemyVariants[i][j]=Bitmap.createScaledBitmap(makeTintedBitmap(enemyAnimations[j],config.enemy2tint),
                            (int) Math.floor(enemyAnimations[j].getWidth()*config.enemyScaleX),
                            (int) Math.floor(enemyAnimations[j].getHeight()*config.enemyScaleY),
                            false);
                }else{
                    enemyVariants[i][j]=Bitmap.createScaledBitmap(makeTintedBitmap(enemyAnimations[j],config.enemy3tint),
                            (int) Math.floor(enemyAnimations[j].getWidth()*config.enemyScaleX),
                            (int) Math.floor(enemyAnimations[j].getHeight()*config.enemyScaleY),
                            false);
                }
            }
        }
        // Scale down
        Bitmap temp_bm;
        for (int i = 0; i < towerVariants.length; i++) {
            temp_bm = towerVariants[i];
            towerVariants[i] = Bitmap.createScaledBitmap(temp_bm,
                    (int) Math.floor(temp_bm.getWidth() * config.towerScaleX),
                    (int) Math.floor(temp_bm.getHeight() * config.towerScaleY),
                    false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Get the list of current towers and enemies on the screen
        ArrayList<Tower> towers = MainActivity.activeLevel.towers;
        ArrayList<Enemy> enemies = MainActivity.activeLevel.waves.get(MainActivity.activeLevel.current_wave);
        // Draw every tower on the screen
        for (Tower t : towers) {
            if (t.variant.equals("Tower1")) {
                canvas.drawBitmap(towerVariants[0],(int)t.xLocation,(int)t.yLocation,canvasPaint);
                Log.d("test", "onDraw: DREW A TOWER");
            } else if (t.variant.equals("Tower2")) {
                canvas.drawBitmap(towerVariants[1], (int) t.xLocation, (int) t.yLocation, canvasPaint);
            }
        }
        // Draw every enemy on the screen
        for (Enemy e : enemies) {
            Log.d("test", "onDraw: DREW AN ENEMY");
            if (e.variant.equals("Enemy1")) {
                canvas.drawBitmap(enemyVariants[0][e.animation_count],(int)e.getxLocation(),(int)e.getyLocation(),canvasPaint);
                //DRAW BASED OFF OF VARIANT (the color/tint) AND THE ANIMATION PROGRESS (enemy.animation_count)
            } else if (e.variant.equals("Enemy2")) {
                canvas.drawBitmap(enemyVariants[1][e.animation_count],(int)e.getxLocation(),(int)e.getyLocation(),canvasPaint);
            }else if (e.variant.equals("Enemy3")) {
                canvas.drawBitmap(enemyVariants[2][e.animation_count],(int)e.getxLocation(),(int)e.getyLocation(),canvasPaint);
            }
        }
        if (!stopped) {
            // Call onDraw again cuz infinite.
            invalidate();
            requestLayout();
        }
    }

}
