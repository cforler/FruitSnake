package fs; 

import java.awt.Color;
       
public class Food {
    final Color c;
    final Point p;
    
    /******************************************************************/

    public Food(Point p) {
        this.p = p;
        c = Constant.FOOD_COLOR;
    }

    public Food(int x, int y) {
        this(new Point(x,y));
    }
        
}
