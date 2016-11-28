import java.awt.*;


public class AchievementManager {
    private Achievement head;
    private long lastDisplay;

    public boolean shouldDrawLatest(){
        return System.currentTimeMillis()-lastDisplay<5000;

    }

    public AchievementManager(){
    }

    public void addAchievement(String message){
        if(!exists(message)){
            System.out.println("Achievement: " + message);
            Achievement newAchievement = new Achievement(message);
            newAchievement.next = head;
            head = newAchievement;
            lastDisplay = System.currentTimeMillis();
        }
        else{
            System.out.println("Achievement '" + message + "' already exists");
        }
    }

    public boolean exists(String message){
        Achievement aPtr = head;
        while(aPtr!=null){
            if(aPtr.equals(message)){
                return true;
            }
            aPtr = aPtr.next;
        }
        return false;
    }

    public void drawLastAchievement(Graphics graphics){
        head.draw(graphics);
    }

    public void addHit(){
        ++Stats.totalHits;
        if(Stats.totalHits%100==0){
            addAchievement("Hit " + Stats.totalHits + " targets");
        }
    }

    public void addModifierHit(){
        ++Stats.totalModifierHits;
        if(Stats.totalModifierHits%25==0){
            addAchievement("Hit " + Stats.totalModifierHits + " special targets");
        }
    }

    public void addMiss(){
        ++Stats.totalMisses;
        if(Stats.totalMisses%100==0){
            addAchievement("Missed " + Stats.totalMisses + " targets");
        }
    }
}
