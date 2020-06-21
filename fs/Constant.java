package fs;

import java.awt.Color;
    
public class  Constant {
    enum Direction { NORTH, SOUTH, EAST, WEST };

    static final int BOARD_WIDTH  = 20;
    static final int BOARD_HEIGHT = 20;
    static final Color SNAKE_COLOR =  Color.MAGENTA;
    static final Color FOOD_COLOR =  Color.RED;


    public static boolean isOnBoard(Point p) {
        return isOnBoard(p.x, p.y);
    }

    public static boolean isOnBoard(int x, int y) {
        return !(x<0 ||  x >= BOARD_WIDTH || y < 0 ||  y >= BOARD_HEIGHT);
    }
}
