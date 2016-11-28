import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Tiles {
    public Color assignedColor;
    public int size;
    public Tile [][] tiles;
    public Tile target;
    public boolean modifierUp = false;
    public Tile modifier;

    public int modifiers [] = {-10, -5, -1, 2, 2, 2, 2, 3, 3, 3, 5, 5, 10, 10};

    public Tiles(int size, int scale, Color color){
        assignedColor = color;
        this.size = size;
        tiles = new Tile[size][size];
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                tiles[i][j] = new Tile(i, j, scale, color);
            }
        }
    }

    public void draw(Graphics graphics){
        for(int i=0;i<size;++i){
            for(int j=0;j<size;++j){
                tiles[i][j].draw(graphics);
            }
        }
    }

    public void setTarget(){
        Color tempColor = Settings.theme[Main.random.nextInt(Settings.theme.length)];
        while(tempColor==assignedColor){
            tempColor = Settings.theme[Main.random.nextInt(Settings.theme.length)];
        }
        target = tiles[Main.random.nextInt(size)][Main.random.nextInt(size)];
        target.color = tempColor;
    }

    public int hitTarget(int x, int y){
        return (target.x<x&&x<target.x+target.scale&&target.y<y&&y<target.y+target.scale)?1:0;
    }

    public int hitModifier(int x, int y){
        return (modifierUp && modifier.x<x&&x<modifier.x+modifier.scale&&modifier.y<y&&y<modifier.y+modifier.scale)?modifier.points:0;
    }

    public void addModifier(){
        Color tempColor = Settings.theme[Main.random.nextInt(Settings.theme.length)];
        while(tempColor==assignedColor){
            tempColor = Settings.theme[Main.random.nextInt(Settings.theme.length)];
        }
        modifier = tiles[Main.random.nextInt(size)][Main.random.nextInt(size)];
        modifier.color = tempColor;
        modifier.points = modifiers[Main.random.nextInt(modifiers.length)];
        modifierUp = true;
    }

    public void drawModifierPoints(Graphics graphics){
        if(!modifierUp){
            return;
        }
        String points = Integer.toString(modifier.points);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 20));
        FontMetrics f = graphics.getFontMetrics();
        Rectangle2D r = f.getStringBounds(points, graphics);
        if(graphics.getColor().getBlue()+graphics.getColor().getRed()+graphics.getColor().getGreen()<140){
            graphics.setColor(Color.WHITE);
        }
        else{
            graphics.setColor(Color.BLACK);
        }
        graphics.drawString(points, modifier.x + modifier.scale/2 + (int) (r.getWidth() / 2), modifier.y + modifier.scale/2 + (int)(r.getHeight() / 2));
    }
}
