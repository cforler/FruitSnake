package fs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Controls extends KeyAdapter {
    Board board;

    public Controls(Board board) {
        this.board = board;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_P: 
        case KeyEvent.VK_SPACE: pause(); break;
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP: board.snake.dir=Constant.Direction.NORTH; break;
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN: board.snake.dir=Constant.Direction.SOUTH; break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT: board.snake.dir=Constant.Direction.WEST; break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT: board.snake.dir=Constant.Direction.EAST; break;
        }
    }
    
    
    private void pause() {
        board.paused = !board.paused;
        if(board.paused)
            board.header.setText("Paused " + board.getHeaderMessage());
        else
            board.header.setText(board.getHeaderMessage());
    }
}
