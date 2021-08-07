package com.example.finalproject;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;

public class Manager extends AppCompatActivity {
    public Level currentLevel;
    public Player currentPlayer;

    public void selectLevel(int levelID, String difficulty){
        File tempfile=new File(String.format("GameData/LevelData/level%d%s",levelID,difficulty));
        if(tempfile.exists()){
            try {
                this.currentLevel=(Level) Manager.objectLoader(tempfile.getName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error Loading Level From Memory");
            }
        }else{
            this.currentLevel=new Level(levelID,difficulty);
        }
    }

    // Found at https://stackoverflow.com/questions/54292078/saving-and-loading-an-object-in-java-that-isnt-a-string-or-int/54293309
    // parameter is Object, allowing us to save any object to file
    public static void writeObjectToDisk(Object obj, String name) throws IOException {
//        name= Environment.getStorageDirectory()+"/"+name;
        //Create file output stream
        Log.d("Manager",name);
        File file = new File(name);
        FileOutputStream fileOutStr = new FileOutputStream(file);
        //Create object output stream and write object
        ObjectOutputStream objOutStr = new ObjectOutputStream(fileOutStr);
        objOutStr.writeObject((Object)obj);
        //Close all streams
        objOutStr.close();
        fileOutStr.close();
        Log.d("Manager","Serialized data is saved in a file  - "+name);
    }

    public static Object objectLoader(String filename) throws IOException,
            ClassNotFoundException{
        AssetManager assetManager= MainActivity.assetManager;
        InputStream is = assetManager.open(filename);
        ObjectInputStream objInStr = new ObjectInputStream(is);
        Object obj = objInStr.readObject();
        objInStr.close();
        is.close();

        return obj;
    }

    public Manager(Player player){
        this.currentPlayer=player;
    }
}
