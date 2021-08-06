import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Tower implements Serializable {
    public String variant;
    public String targetting;
    private ArrayList<Tower> nearby_allies;
    public double xLocation;
    public double yLocation;
    private Enemy target;
    private ArrayList<Enemy> in_range;
    private double atk_speed;
    private double atk_range;
    private double atk_damage;
    private double atk_timer;
    // Whether or not the tower is being supported by another tower
    public Boolean supported;

    public Enemy getTarget() {
        return target;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public double getAtk_speed() {
        return atk_speed;
    }

    public void setAtk_speed(double atk_speed) {
        this.atk_speed = atk_speed;
    }

    public double getAtk_range() {
        return atk_range;
    }

    public void setAtk_range(double atk_range) {
        this.atk_range = atk_range;
    }

    public double getAtk_damage() {
        return atk_damage;
    }

    public void setAtk_damage(double atk_damage) {
        this.atk_damage = atk_damage;
    }

    private double dist(double x1, double y1, double x2, double y2){
        return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    // Update this.in_range to include only enemies which are in range of this tower
    private void find_enemies(ArrayList<Enemy> enemies, ArrayList<Tower> allies) {
        if (this.variant.equals("Tower1")) {
            ArrayList<Enemy> in_range = new ArrayList<>();
            // Loop thru all of the enemies on screen and add the ones who are within attacking distance to "in_range"
            for (Enemy enemy : enemies) {
                double enemyXLocation = enemy.getxLocation();
                double enemyYLocation = enemy.getyLocation();
                double distance = dist(this.xLocation, this.yLocation, enemyXLocation, enemyYLocation);
                if (distance < this.atk_range) {
                    in_range.add(enemy);
                }
            }
            this.in_range = in_range;
        }else if(this.variant.equals("Tower2")){
            ArrayList<Tower> nearby_allies = new ArrayList<>();
            for (Tower ally:allies) {
                double allyXLocation = ally.xLocation;
                double allyYLocation = ally.yLocation;
                double distance = dist(this.xLocation,this.yLocation,allyXLocation,allyYLocation);
                if (distance < this.atk_range){
                    nearby_allies.add(ally);
                }
            }
            this.nearby_allies=nearby_allies;
        }
    }

    // Of all of the enemies within range, get the enemy who is select by the current targetting mode
    private void find_target(){
        Enemy target=null;
        if(this.targetting.equals("Near")){
            // Find the closest enemy, and set the target to that enemy
            double min_distance=Double.POSITIVE_INFINITY;
            for (Enemy enemy:this.in_range) {
                double enemyXLocation=enemy.getxLocation();
                double enemyYLocation=enemy.getyLocation();
                double distance = dist(this.xLocation,this.yLocation,enemyXLocation,enemyYLocation);
                if(distance<min_distance){
                    min_distance=distance;
                    target=enemy;
                }
            }
        }else if(this.targetting.equals("Far")){
            // Find the farthest enemy, and set the target to that enemy
            double max_distance=Double.NEGATIVE_INFINITY;
            for (Enemy enemy:this.in_range) {
                double enemyXLocation=enemy.getxLocation();
                double enemyYLocation=enemy.getyLocation();
                double distance = dist(this.xLocation,this.yLocation,enemyXLocation,enemyYLocation);
                if(distance>max_distance){
                    max_distance=distance;
                    target=enemy;
                }
            }
        }else if(this.targetting.equals("Strong")){
            // Find the strongest (by current Health) enemy, and set the target to that enemy
            double max_health=Double.NEGATIVE_INFINITY;
            for (Enemy enemy:this.in_range) {
                if(enemy.getHp_val()>max_health){
                    max_health=enemy.getHp_val();
                    target=enemy;
                }
            }
        }else if(this.targetting.equals("Weak")){
            // Find the weakest (by current Health) enemy, and set the target to that enemy
            double min_health=Double.POSITIVE_INFINITY;
            for (Enemy enemy:this.in_range) {
                if(enemy.getHp_val()<min_health){
                    min_health=enemy.getHp_val();
                    target=enemy;
                }
            }
        }
        this.target=target;
    }

    // Counts down an internal variable (time until next attack) by this.atk_speed until it reaches 0 then it attacks
    private void attack(){
        // Make sure there is a target in range
        if(this.variant.equals("Tower1") && target!=null){
                if(!this.supported){
                    this.atk_timer-=this.atk_speed;
                }else{
                    // Supported towers get 125% attack speed
                    this.atk_timer-=1.25*this.atk_speed;
                }
                if(this.atk_timer<=0){
                    // Set the target's hp val to it's current hp val - attack damage
                    this.target.setHp_val(this.target.getHp_val() - this.atk_damage);
                    this.atk_timer = config.attackTimerBase;
                }
        }else if(this.variant.equals("Tower2")){
                for (Tower ally:this.nearby_allies) {
                    if(!ally.supported){
                        ally.supported=true;
                    }
                }
            }
        }

    private void setStats(){
        switch (this.variant){
            case "Tower1" -> {
                this.setAtk_damage(config.tower1AtkDamage);
                this.setAtk_speed(config.tower1AtkSpeed);
                this.setAtk_range(config.tower1AtkRange);
            }case "Tower2" ->{
                this.setAtk_damage(config.tower2AtkDamage);
                this.setAtk_speed(config.tower2AtkSpeed);
                this.setAtk_range(config.tower2AtkRange);
            }
        }
    }

    public void update(ArrayList<Enemy> enemies,ArrayList<Tower> towers){
        this.find_enemies(enemies,towers);
        this.find_target();
        this.attack();
    }

    public Tower(String variant, double xLoc, double yLoc){
        this.in_range=new ArrayList<>();
        this.nearby_allies=new ArrayList<>();
        this.variant=variant;
        this.xLocation=xLoc;
        this.yLocation=yLoc;
        this.target=null;
        this.setStats();
    }

    public Tower(String variant){
        this.in_range=new ArrayList<>();
        this.nearby_allies=new ArrayList<>();
        this.variant=variant;
        this.target=null;
        this.setStats();
    }
}
