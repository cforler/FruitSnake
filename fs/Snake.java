package fs; 

import java.awt.Color;
import java.awt.Color;
import java.util.LinkedList;
       
public class Snake {
    LinkedList<Point> q;
    Constant.Direction dir;
    final Color c;
    
    /******************************************************************/

    public Snake(Point p) {
        q = new LinkedList<Point>();
        q.add(p);
        dir = Constant.Direction.NORTH;
        c = Constant.SNAKE_COLOR;
    }

    public Snake(int x, int y) {
        this(new Point(x,y));
    }
        
}
