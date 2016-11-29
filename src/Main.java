
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
    public AchievementManager achievementManager;
    public Thread t = Thread.currentThread();
    public int padding = 100;
    public static boolean playing = true;
    public static Settings settings;
    public MouseAdapter gameMouse;
    public MouseAdapter settingsMouse;
    public MouseAdapter buttonMouse;
    public int points = 1;

    public Main(){
        tileSize = 100;
        int tileNum = 6;
        lengthX = tileSize*tileNum+(5*(tileNum+1));
        lengthY = tileSize*tileNum+(5*(tileNum+1)) + 50; //+50 for score
    }

    public static void main(String [] args){
        Audio audio = new Audio();
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
        settings = new Settings();
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
                    audio.playMenuSound();
                    System.out.println(instance.paused ? "Game Paused" : "Game Unpaused");
                    instance.repaint();
                }
            }
        });
        instance.buttonMouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(settings.hitSettingsButton(e.getX(), e.getY())){
                    System.out.println("Hit Settings button!");
                    settings.visible = !settings.visible;
                    instance.paused = true;
                    if(settings.visible) {
                        frame.removeMouseListener(instance.gameMouse);
                        frame.addMouseListener(instance.settingsMouse);
                    }
                    else{
                        frame.removeMouseListener(instance.settingsMouse);
                        frame.addMouseListener(instance.gameMouse);
                    }
                    instance.repaint();
                }
                else if(settings.hitQuitButton(e.getX(), e.getY())){
                    System.exit(1);
                }
            }
        };
        instance.gameMouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(instance.canHit) {
                    if (instance.tiles.hitTarget(e.getX(), e.getY())>0) {
                        System.out.println("Hit the target!");
                        audio.playHit();
                        instance.points = 1;
                        instance.hit = true;
                        instance.achievementManager.addHit();
                        instance.t.interrupt();
                    }
                    else {
                        System.out.println("Did not hit the target!");
                        audio.playMiss();
                        instance.achievementManager.addMiss();
                    }
                    int modScore = instance.tiles.hitModifier(e.getX(), e.getY());
                    if(modScore!=0){
                        System.out.println("Hit the modifier!");
                        audio.playSpecialHit();
                        instance.points += modScore;
                        instance.hit = true;
                        instance.achievementManager.addModifierHit();
                        instance.t.interrupt();
                    }
                    instance.repaint();
                }
            }
        };
        instance.settingsMouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        };
        frame.addMouseListener(instance.buttonMouse);
        frame.addMouseListener(instance.gameMouse);
        instance.scoreUI = new Score();
        instance.pauseUI = new Pause();
        instance.achievementManager = new AchievementManager();
        audio.playMusic();

        while(playing) {
            if (!instance.paused) {
                instance.newRound();
            } else {
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
        graphics.fillRoundRect(0, 0, instance.lengthX + instance.padding, instance.lengthY + instance.padding, instance.lengthX / 10, instance.lengthY / 10);
        settings.drawSettingsButton(graphics);
        settings.drawQuitButton(graphics);
        if(scoreUI!=null){
            scoreUI.draw(graphics);
        }
        if(achievementManager.shouldDrawLatest()){
            achievementManager.drawLastAchievement(graphics);
        }
        if(tiles!=null){
            tiles.draw(graphics);
            tiles.drawModifierPoints(graphics);
        }
        if(settings.visible){
            settings.drawSettingsBox(graphics);
        }
        else if(paused){
            pauseUI.draw(graphics);
        }

    }

    public void newRound() {
        System.out.println("Starting new round");
        tiles = new Tiles(lengthX/tileSize, tileSize, Settings.theme[random.nextInt(Settings.theme.length)]);
        repaint();
        canHit = true;
        hit = false;
        tiles.setTarget();
        if(random.nextInt(10)>8) {
            tiles.addModifier();
        }
        else{
            tiles.modifierUp = false;
        }
        try {
            Thread.sleep(1500-score*Settings.difficulty);
        }
        catch (InterruptedException e) {
            //e.printStackTrace();
        }
        canHit = false;
        if(hit){
            score += points;
            if(score%25==0 && score!=0){
                for(int i = 25; i <= score; i+=25){
                    instance.achievementManager.addAchievement("Achievement! Reached score: " + i);
                }
            }
        }
        else{
            --score;
        }
        System.out.println("Ending round");
    }
}
