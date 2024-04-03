import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; //Think of graph paper, each square is considered a unit_size
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //The size of our game units
    static final int DELAY = 75; //the speed of the game, the higher the number the slower it is
    final int x[] = new int[GAME_UNITS]; //holds all the x coordinates for the snake
    final int y[] = new int[GAME_UNITS]; //holds all the y coordinates for the snake
    int bodyParts = 6; //the starting size of the snake
    int applesEaten; //amount of apples the snake has eaten
    int appleX; //x coordinates for the apple
    int appleY; //y coordinates for the apple
    char direction = 'R'; //The direction the snake is going
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //Sets the size
        this.setBackground(Color.black); //background color
        this.setFocusable(true); // generally used for keyboard inputs allow the components to understand keyboard presses
        this.addKeyListener(new MyKeyAdapter()); // adds the specified key listener to receive Key events from this component
        startGame();
    }
    public void startGame(){
        newApple(); //Populate the game with a new apple
        running = true;
        timer = new Timer(DELAY, this); //Listen for the user input to control snake direction, this refers to ActionListener
        timer.start();
    }
    public void paintComponent(Graphics g){

    }
    public void draw(Graphics g){

        for(int i = 0; i <SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
        }
    }
    public void move(){

    }
    public void newApple(){ //populate the panel with new apples

    }
    public void checkApple(){

    }
    public void checkCollisions(){

    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter{ //Allows java to recognize key presses
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
        }
    }
}
