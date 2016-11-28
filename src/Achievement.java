import java.awt.*;

public class Achievement {
    String message;
    Achievement next;

    public Achievement(String message){
        this.message = message;
    }

    public boolean equals(String message){
        return this.message.equals(message);
    }

    public boolean equals(Achievement achievement){
        return this.message.equals(achievement.message);
    }

    public void draw(Graphics graphics){
        System.out.println("Drawing achievement message: " + message);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 20));
        FontMetrics f = graphics.getFontMetrics();
        graphics.setColor(Color.WHITE);
        graphics.drawString(message, (((Main.instance.lengthX+Main.instance.padding)/2)-(f.stringWidth(message)/2)), 50);
    }
}
