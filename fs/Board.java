package fs; 

import java.awt.Graphics;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Board extends JPanel {
    enum Tile { EMPTY, SNAKE, FOOD };
    
    static final int SQUARE_LEN = 30;
    private static final int INTERVAL     =  100;
    private static final int BASIC_SPAWN_CHANCE  = 5;
    private static final int BIG_SPAWN_CHANCE    = 5;
    
    Tile[][] board;
    
    boolean over; 
    boolean paused;
    Snake snake;
    Food food;
    JLabel header;
    private Random rand;

    private Timer timer;
    private int score;
    private int topScore;
    
 
    
    /******************************************************************/
    
    public Board(JLabel header) {
        rand = new Random();
        this.header = header;
        addKeyListener(new Controls(this));
        timer = new Timer(INTERVAL, new Ticks());
        topScore=0;
        initBoard();
    }
     
    
    private void initBoard(){
        over=false;
        paused=false;
        score=0;
        board = new Tile[Constant.BOARD_HEIGHT][Constant.BOARD_WIDTH];
        for (int y = 0; y < Constant.BOARD_HEIGHT; y++) 
            for (int x = 0; x < Constant.BOARD_WIDTH; x++)
                board[y][x] = Tile.EMPTY;
        snake = new Snake(Constant.BOARD_WIDTH/2, Constant.BOARD_HEIGHT/2);
        setSnake(snake.q.getFirst());
        food = getFood();
        header.setText(getHeaderMessage());
        setFocusable(true);
        start();
    }
    
    /******************************************************************/
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
    /******************************************************************/
   
     private void  drawGrid(Graphics g) {
         for (int i = 0; i < Constant.BOARD_HEIGHT; i++) 
             for (int j = 0; j < Constant.BOARD_WIDTH; j++) 
                 drawSquare(g,i,j,Color.WHITE);
     }

    /******************************************************************/

    private void  updatePoint(Point p) {
        if(Constant.isOnBoard(p.x+1,p.y)) {
           p.x +=1;
        } else if(Constant.isOnBoard(p.x,p.y+1)) {
            p.y +=1;
        }
        else {
            p.x=0;
            p.y=0; 
        }
    }
      
    /******************************************************************/

    private Food getFood() {
        int x = rand.nextInt(Constant.BOARD_WIDTH);
        int y = rand.nextInt(Constant.BOARD_HEIGHT);
        Point p = new Point(x,y);
        
        while(getTile(p) != Tile.EMPTY) updatePoint(p);
        
        setFood(p);
        return new Food(p);
    }
    
    
     /******************************************************************/
    
    private void drawSquare(Graphics g, Point p, Color c) {
        drawSquare(g, p.x,p.y,c); }
        
    /******************************************************************/
    
    private void drawSquare(Graphics g, int x, int y, Color c) {
        int xa = x * SQUARE_LEN ;
        int ya = y * SQUARE_LEN ;
        g.setColor(c);     
        g.fillRect(xa+1, ya +1, SQUARE_LEN-1, SQUARE_LEN - 1);
    } 

    /******************************************************************/   
    
    private void drawCircle(Graphics g, Point p, Color c) {
        drawCircle(g, p.x,p.y,c);
    }

    private void drawLeftEye(Graphics g) {
        Point head = snake.q.peek();
        int xa = head.x * SQUARE_LEN + SQUARE_LEN/6 ;
        int ya = head.y * SQUARE_LEN + SQUARE_LEN/3;
        g.setColor(Color.BLACK);     
        g.fillOval(xa+1, ya +1, SQUARE_LEN/4, SQUARE_LEN/4);  
    }


    private void drawRightEye(Graphics g) {
        Point head = snake.q.peek();
        int xa = head.x * SQUARE_LEN + SQUARE_LEN/2 ;
        int ya = head.y * SQUARE_LEN + SQUARE_LEN/3;
        g.setColor(Color.BLACK);     
        g.fillOval(xa+1, ya +1, SQUARE_LEN/4, SQUARE_LEN/4);  
    }
    
       
    /******************************************************************/
    
    
    private void drawCircle(Graphics g, int x, int y, Color c) {
        int xa = x * SQUARE_LEN ;
        int ya = y * SQUARE_LEN ;
        g.setColor(c);     
        g.fillOval(xa+1, ya +1, SQUARE_LEN-1, SQUARE_LEN - 1);
    } 

     /******************************************************************/
    
    private int getTop() {
        return (int) getSize().getHeight() - Constant.BOARD_HEIGHT * SQUARE_LEN;
    }
     

     /******************************************************************/

    private void drawSnake(Graphics g) {
        for(Point p : snake.q) drawSquare(g, p, snake.c);
        drawLeftEye(g);
        drawRightEye(g);
     }

    /******************************************************************/
    
    private void drawFood(Graphics g) { drawCircle(g, food.p,food.c); }
    
    /******************************************************************/
    
    private void doDrawing(Graphics g) {
        drawGrid(g);
        drawSnake(g);
        drawFood(g);
    }
    
    /******************************************************************/

    public void start() {
        timer.start();
    }
    
     /******************************************************************/

    private void gameOver() {
        timer.stop();
        header.setText("GAME OVER! " + getHeaderMessage());
        JOptionPane.showMessageDialog(null, "GAME OVER!\n"+"Your score: "
                                      +score  ,"GAME OVER! :(",
                                      JOptionPane.WARNING_MESSAGE);
        if(score > topScore) topScore = score; 
        initBoard();
    }


    /******************************************************************/

    private Tile getTile(Point p) { return board[p.y][p.x];  }

    private void setTile(Point p, Tile t) { board[p.y][p.x] = t;  }
    private void setSnake(Point p) { setTile(p, Tile.SNAKE); }
    private void setEmpty(Point p) { setTile(p, Tile.EMPTY); }
    private void setFood(Point p) { setTile(p, Tile.FOOD); }
    
    
     /******************************************************************/

    private boolean moveSnake() {
        Point head = snake.q.peek();
        Point newHead;
        switch(snake.dir) {
        case NORTH: newHead = new Point(head.x, head.y-1); break;
        case SOUTH: newHead = new Point(head.x, head.y+1); break;

        case WEST: newHead = new Point(head.x-1, head.y); break;
        case EAST: newHead = new Point(head.x+1, head.y); break;

        default: return true;
        }

        if (! Constant.isOnBoard(newHead)) return true;
        switch(getTile(newHead)) {
        case EMPTY:
            Point last = snake.q.removeLast();
            setEmpty(last);
            break;
        case FOOD:
            score += snake.q.size();
            header.setText(getHeaderMessage());
            food = getFood();
            break;
        case SNAKE: return true;
        }
        
        snake.q.addFirst(newHead);        
        setSnake(newHead);
        return false;
    }
    
    
    
     /******************************************************************/

    private void update() {
        if(paused || over) return;
        over = moveSnake();
    }
    
    /******************************************************************/
    
    String getHeaderMessage() {
        return "Score: " + score +"   TopScore: " + topScore;
    }
    
    
    /******************************************************************/
    
    private class Ticks implements ActionListener {
        @Override
         public void actionPerformed(ActionEvent e) { tick(); }
    }
    
    /******************************************************************/
    
    private void tick() {
        update();
        repaint();
        if(over) gameOver();
    }
}
