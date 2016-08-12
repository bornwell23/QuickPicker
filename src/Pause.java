import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Pause {

    public Pause(){

    }

    public void draw(Graphics graphics){
        System.out.println("Drawing pause message");
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 50));
        FontMetrics f = graphics.getFontMetrics();
        String message = "Paused";
        Rectangle2D r = f.getStringBounds(message, graphics);
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect((int) (((Main.instance.lengthX + Main.instance.padding) / 2) - (r.getWidth())), (int) (((Main.instance.lengthX + Main.instance.padding) / 2) - (r.getHeight())), (int) r.getWidth() * 2, (int) r.getHeight() * 2, 25, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString(message, (int)(((Main.instance.lengthX + Main.instance.padding)/2)-(r.getWidth())/2), (((Main.instance.lengthX + Main.instance.padding)/2)));
    }
}
