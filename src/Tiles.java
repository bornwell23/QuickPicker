import java.awt.*;

public class Tiles {
    public Color assignedColor;
    public int size;
    public Tile [][] tiles;
    public Tile target;

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
        Color tempColor = Main.colors[Main.random.nextInt(Main.colors.length)];
        while(tempColor==assignedColor){
            tempColor = Main.colors[Main.random.nextInt(Main.colors.length)];
        }
        target = tiles[Main.random.nextInt(size)][Main.random.nextInt(size)];
        target.color = tempColor;
    }

    public boolean hitTarget(int x, int y){
        return target.x<x&&x<target.x+target.scale&&target.y<y&&y<target.y+target.scale;
    }
}
