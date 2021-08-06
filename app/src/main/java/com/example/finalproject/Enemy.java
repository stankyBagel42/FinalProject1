import java.io.Serializable;

public class Enemy implements Serializable {
    private double hp_val;
    public String variant;
    // This is the distance the enemy has traveled along the track for a given level
    // 0.0 is the start of the track, 1.0 is the end
    public double location;
    public double speed;
    private double xLocation;
    private double yLocation;

    public double getxLocation() {
        return xLocation;
    }

    public void setxLocation(double xLocation) {
        this.xLocation = xLocation;
    }

    public double getyLocation() {
        return yLocation;
    }

    public void setyLocation(double yLocation) {
        this.yLocation = yLocation;
    }

    public double getHp_val() {
        return hp_val;
    }

    public void setHp_val(double hp_val) {
        this.hp_val = hp_val;
    }

    private void setSpeed(){
        switch (this.variant) {
            case "Enemy1" -> this.setHp_val(config.enemy1speed);
            case "Enemy2" -> this.setHp_val(config.enemy2speed);
            case "Enemy3" -> this.setHp_val(config.enemy3speed);
        }
    }

    private void setMax_Health(){
        switch (this.variant) {
            case "Enemy1" -> this.setHp_val(config.enemy1health);
            case "Enemy2" -> this.setHp_val(config.enemy2health);
            case "Enemy3" -> this.setHp_val(config.enemy3health);
        }
    }

    public void update_loc(){
        this.location+=this.speed;
    }

    public Enemy(String variant){
        this.location=0;
        this.variant=variant;
        this.setMax_Health();
        this.setSpeed();
    }

    public Enemy(String variant, double location){
        this.location=location;
        this.variant=variant;
        this.setMax_Health();
        this.setSpeed();
    }


}
