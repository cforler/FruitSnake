package fs; 

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Food {
    final Color c;
    final Point p;
    boolean image;
    BufferedImage img;
    
    /******************************************************************/

    public Food(Point p) {
        this.p = p;
        c = Constant.FOOD_COLOR;
        try {
            img = ImageIO.read(new File("fs/images/apple.png"));
            image = true;
        } catch(IOException e) { System.out.println(e); image = false; }
    }
    
    public Food(int x, int y) {
        this(new Point(x,y));
    }
        
}
