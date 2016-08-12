import java.awt.*;

public class Score {

    public Score(){

    }

    public void draw(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 20));
        FontMetrics f = graphics.getFontMetrics();
        String score = "Score: " + Main.instance.score;
        graphics.drawString(score, (((Main.instance.lengthX+Main.instance.padding)/2)-(f.stringWidth(score)/2)), 20);
    }
}
