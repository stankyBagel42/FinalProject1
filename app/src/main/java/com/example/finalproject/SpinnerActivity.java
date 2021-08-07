package com.example.finalproject;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String selected;
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        selected=parent.getItemAtPosition(pos).toString();
    }
    public void onNothingSelected(AdapterView<?> parent){
        selected=parent.getItemAtPosition(0).toString();
            }
    public SpinnerActivity(Spinner spinner){
        spinner.setOnItemSelectedListener(this);
    }
}
