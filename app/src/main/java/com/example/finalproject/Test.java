import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// TO LOAD OBJECTS FROM FILE, CAST CLASS ONTO RETURN VALUE FROM Manager.objectLoader("filename")
// i.e. Player loadedPlayer = (Player) Manager.objectLoader("testPlayer.ser")
// CAN JUST Manager.writeObjectToDisk TO OVERWRITE EXISTING FILES
public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        for (int i = 0; i < 5; i++) {
//            Level tempLevel = new Level(i);
//            ArrayList<Enemy> wave1 = new ArrayList<>(
//                    Arrays.asList(new Enemy("Enemy1"), new Enemy("Enemy1"), new Enemy("Enemy1"))
//            );
//            tempLevel.waves.add(wave1);
//            ArrayList<Enemy> wave2 = new ArrayList<>(
//                    Arrays.asList(new Enemy("Enemy1"), new Enemy("Enemy1"), new Enemy("Enemy1"), new Enemy("Enemy1"), new Enemy("Enemy1"))
//            );
//            tempLevel.waves.add(wave2);
//            ArrayList<Enemy> wave3 = new ArrayList<>(
//                    Arrays.asList(new Enemy("Enemy2"), new Enemy("Enemy2"), new Enemy("Enemy1"), new Enemy("Enemy1"), new Enemy("Enemy1"))
//            );
//            tempLevel.waves.add(wave3);
//            Manager.writeObjectToDisk(tempLevel,String.format("level%d_base",i));
//        }
        for (int i = 0; i < 5; i++) {
            Level tempLevel=(Level) Manager.objectLoader(String.format("GameData/BaseLevels/level%d_base",i));
            System.out.println("=========================================================");
            System.out.printf("Level %d%n",tempLevel.level_ID);
            for (ArrayList<Enemy> wave:tempLevel.waves) {
                System.out.println("----------------------------");
                System.out.print("Wave: ");
                for (Enemy e:wave) {
                    System.out.print(e.variant);
                    System.out.print(", ");
                }
                System.out.print("\n");
            }

        }
    }
}
