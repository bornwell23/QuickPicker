import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Achievement {

    public Achievement(){

    }

    public void draw(Graphics graphics, String message){
        System.out.println("Drawing achievement message");
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 20));
        FontMetrics f = graphics.getFontMetrics();
        Rectangle2D r = f.getStringBounds(message, graphics);
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect((int) ((Main.instance.lengthX + Main.instance.padding) - (r.getWidth())), (int) ((Main.instance.lengthX + Main.instance.padding) - (r.getHeight())), (int) r.getWidth() * 2, (int) r.getHeight() * 2, 25, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString(message, (int)(((Main.instance.lengthX + Main.instance.padding))-(r.getWidth())/2), (int)(((Main.instance.lengthX + Main.instance.padding))-(r.getHeight()/2)));
    }
}
