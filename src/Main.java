import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalTime;
import java.util.Random;

public class Main extends JPanel{
    public static JFrame frame;
    public static Color [] colors = {Color.WHITE, Color.BLUE, Color.CYAN, Color.RED, Color.YELLOW, Color.ORANGE, Color.PINK, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.LIGHT_GRAY, Color.DARK_GRAY};

    public boolean hit = false;
    public boolean canHit = false;
    public boolean paused = false;
    public static Main instance;
    public int lengthX, lengthY;
    Tiles tiles;
    int tileSize;
    public static Random random = new Random(LocalTime.now().toSecondOfDay());
    public int score = 0;
    public Score scoreUI;
    public Pause pauseUI;
    public Thread t = Thread.currentThread();
    public int padding = 100;

    public Main(){
        tileSize = 100;
        int tileNum = 6;
        lengthX = tileSize*tileNum+(5*(tileNum+1));
        lengthY = tileSize*tileNum+(5*(tileNum+1)) + 50; //+50 for score
    }

    public static void main(String [] args){
        instance = new Main();
        frame = new JFrame("QuickPicker");
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, instance.lengthX+instance.padding, instance.lengthY+instance.padding, instance.lengthX/5, instance.lengthY/5));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(instance.lengthX+instance.padding, instance.lengthY+instance.padding));
        frame.setSize(instance.lengthX+instance.padding, instance.lengthY+instance.padding);
        frame.setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(screenSize.getWidth()/2)-(instance.lengthX/2);
        int height = (int)(screenSize.getHeight()/2)-(instance.lengthY/2);
        frame.setLocation(width, height);
        frame.setContentPane(instance);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                char pressedChar = e.getKeyChar();
                if (pressedChar == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
                if (pressedChar == KeyEvent.VK_SPACE) {
                    instance.paused = !instance.paused;
                    System.out.println(instance.paused?"Game Paused":"Game Unpaused");
                    instance.repaint();
                }
            }
        });
        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(instance.canHit) {
                    if (instance.tiles.hitTarget(e.getX(), e.getY())) {
                        System.out.println("Hit the target!");
                        instance.hit = true;
                        instance.t.interrupt();
                    } else {
                        System.out.println("Did not hit the target!");
                        --instance.score;
                    }
                    instance.repaint();
                }
            }
        };
        frame.addMouseListener(m);
        instance.scoreUI = new Score();
        instance.pauseUI = new Pause();
        while(true){
            if(!instance.paused) {
                instance.newRound();
            }
            else{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics){
        //draw stuff here
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(0, 0, instance.lengthX+instance.padding, instance.lengthY+instance.padding, instance.lengthX/10, instance.lengthY/10);
        if(scoreUI!=null){
            scoreUI.draw(graphics);
        }
        if(tiles!=null){
            tiles.draw(graphics);
        }
        if(paused){
            pauseUI.draw(graphics);
        }
    }

    public void newRound(){
        System.out.println("Starting new round");
        tiles = new Tiles(lengthX/tileSize, tileSize, colors[random.nextInt(colors.length)]);
        repaint();
        canHit = true;
        hit = false;
        tiles.setTarget();
        try {
            Thread.sleep(1500-score*5);
        }
        catch (InterruptedException e) {
            //e.printStackTrace();
        }
        canHit = false;
        if(hit){
            ++score;
        }
        else{
            --score;
        }
        System.out.println("Ending round");
    }
}
