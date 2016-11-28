import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Settings {
    public boolean visible = false;
    public static int difficulty = 5;
    public static Settings instance;
    public static Color [] theme = Themes.neon;
    public static boolean fullScreen = false;
    public static int tileSize = 50;
    public static int boardSize = 5; //1->10, with 10 being largest, round down to ensure all tiles are represented properly. do math to ensure this
    public static boolean soundOn = false;

    public Settings() {
        instance = this;
    }

    public void drawSettingsButton(Graphics graphics){
        System.out.println("Drawing settings button");
        graphics.setColor(Color.WHITE);
        graphics.fillOval(Main.instance.lengthX - 60, 30, 30, 30);
        graphics.setColor(Color.BLACK);
        graphics.drawString("S", Main.instance.lengthX - 50, 50);
    }

    public void drawQuitButton(Graphics graphics){
        System.out.println("Drawing quit button");
        graphics.setColor(Color.WHITE);
        graphics.fillOval(Main.instance.lengthX - 25, 30, 30, 30);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(Main.instance.lengthX - 25, 30, Main.instance.lengthX + 5, 60);
        graphics.drawLine(Main.instance.lengthX + 5, 30, Main.instance.lengthX - 25, 60);
    }

    public boolean hitSettingsButton(int x, int y){
        return Main.instance.lengthX - 60 <= x && x <= Main.instance.lengthX - 30 && y >= 30 && y <= 60;
    }

    public boolean hitQuitButton(int x, int y){
        return Main.instance.lengthX - 25 <= x && x <= Main.instance.lengthX + 5 && y >= 30 && y <= 60;
    }

    public void drawSettingsBox(Graphics graphics){
        System.out.println("Drawing settings menu!");
        if(instance==null){
            return;
        }
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 50));
        FontMetrics f = graphics.getFontMetrics();

        String message1 = "Settings";
        Rectangle2D r1 = f.getStringBounds(message1, graphics);

        graphics.setFont(new Font("Times New Roman", Font.BOLD, 20));
        f = graphics.getFontMetrics();

        String message2 = "Sound";
        Rectangle2D r2 = f.getStringBounds(message2, graphics);

        String message3 = "Theme";
        Rectangle2D r3 = f.getStringBounds(message3, graphics);

        String message4 = "Tile Size";
        Rectangle2D r4 = f.getStringBounds(message4, graphics);

        String message5 = "Board Size";
        Rectangle2D r5 = f.getStringBounds(message5, graphics);

        String message6 = "Difficulty";
        Rectangle2D r6 = f.getStringBounds(message6, graphics);

        String message7 = "Full Screen";
        Rectangle2D r7 = f.getStringBounds(message7, graphics);

        Rectangle2D boxR = new Rectangle((int)(r1.getWidth()+r2.getWidth()+r3.getWidth()+r4.getWidth()+r5.getWidth()+r6.getWidth()+r7.getWidth()), (int)(r1.getHeight()+r2.getHeight()+r3.getHeight()+r4.getHeight()+r5.getHeight()+r6.getHeight()+r7.getHeight()));

        int settingsBoxStartX = (int) ((Main.instance.lengthX + Main.instance.padding)/2 - (boxR.getWidth()/2));
        int settingsBoxStartY = (int) ((Main.instance.lengthX + Main.instance.padding)/2 - (boxR.getHeight()/2));
        int offset = 0;

        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(settingsBoxStartX, settingsBoxStartY, (int) boxR.getWidth(), (int) boxR.getHeight() + 50, 25, 25);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Times New Roman", Font.BOLD, 50));
        graphics.drawString(message1, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r1.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r1.getHeight();

        graphics.setFont(new Font("Times New Roman", Font.BOLD, 20));
        graphics.drawString(message2, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r2.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r2.getHeight();

        graphics.drawString(message3, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r3.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r3.getHeight();

        graphics.drawString(message4, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r4.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r4.getHeight();

        graphics.drawString(message5, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r5.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r5.getHeight();

        graphics.drawString(message6, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r6.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r6.getHeight();

        graphics.drawString(message7, settingsBoxStartX + (int) (boxR.getWidth() / 2 - r7.getWidth() / 2), settingsBoxStartY + 50 + offset);
        offset += r7.getHeight();
    }
}
