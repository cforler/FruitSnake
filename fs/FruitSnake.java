package fs;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

public class FruitSnake extends JFrame {
    private Board board;
    private final int WIDTH=600;
    private final int HEIGHT=600;

    
    public FruitSnake() {
        JLabel header = new JLabel();
        add(header, BorderLayout.NORTH);      
        board = new Board(header);
        board.setBorder(BorderFactory.createLineBorder(Color.black));
        add(board, BorderLayout.CENTER);
        setTitle("Fruit Snake");
        setSize(WIDTH+2, HEIGHT+40);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    public static void main(String[] args) {
        new FruitSnake();
    }
     
}
