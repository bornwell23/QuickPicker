import java.awt.*;

public class Tile {
    int x, y, scale;
    Color color;
    int points = 1;

    public Tile(int i, int j, int scale, Color color){
        this.x = (i*scale)+5+(i*5) + Main.instance.padding/2;
        this.y = (j*scale)+5+(j*5)+50 + Main.instance.padding/2; //+50 is for score
        this.scale = scale;
        this.color = color;
    }

    public void draw(Graphics graphics){
        graphics.setColor(color);
        graphics.fillRoundRect(x, y, scale, scale, scale/5, scale/5);
    }
}
