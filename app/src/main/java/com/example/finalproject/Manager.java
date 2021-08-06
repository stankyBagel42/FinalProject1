import java.io.*;
import java.util.ArrayList;

public class Manager {
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
        if(obj instanceof Player){
            name="GameData/PlayerData/"+name;
        }else if(obj instanceof Level){
            name="GameData/LevelData/"+name;
        }else{
            name="GameData/OtherData/"+name;
        }
        //Create file output stream
        FileOutputStream fileOutStr = new FileOutputStream(name);
        //Create object output stream and write object
        ObjectOutputStream objOutStr = new ObjectOutputStream(fileOutStr);
        objOutStr.writeObject((Object)obj);
        //Close all streams
        objOutStr.close();
        fileOutStr.close();
        System.out.print("Serialized data is saved in a file  - "+name);
    }

    public static Object objectLoader(String filename) throws IOException,
            ClassNotFoundException{
        FileInputStream fileInStr = new FileInputStream(filename);
        ObjectInputStream objInStr = new ObjectInputStream(fileInStr);
        Object obj = (Object) objInStr.readObject();
        objInStr.close();
        fileInStr.close();

        return obj;
    }

    public Manager(Player player){
        this.currentPlayer=player;
    }
}
