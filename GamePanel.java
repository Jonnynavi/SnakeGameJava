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
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            //Graph Lines
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            //Apples
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Snake
            for (int i = 0; i < bodyParts; i++) { //Loop through x and y array to create the snake
                if (i == 0) { //Creates the head
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            //Score Board
            scoreCounter(g);
        }else{
            scoreCounter(g);
            gameOver(g);
        }
    }
    public void move(){
        for(int i = bodyParts; i > 0; i--){ //shifting the array coordinates all by one spot
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){ //Chooses the direction the head goes by adding or subtracting unit_size when key is pressed
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
        }
    }
    public void newApple(){ //populate the panel with new apples
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //randomly chooses x coordinates for apple
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;//randomly chooses y coordinates for apple
    }
    public void checkApple(){ //checks if snake has eaten apple
        if(appleY == y[0] && appleX == x[0]){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //checks if head collides with body
        for(int i = bodyParts; i>0; i--){
            if(x[0] == x[i] && y[0] == y[i]){
                running = false;
            }
        }
        //checks if head touches left or right border
        if((x[0]<0) || x[0]>SCREEN_WIDTH){
            running = false;
        }
        //checks if head touches top or bottom border
        if(y[0]<0 || y[0]>SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }
    public void scoreCounter(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont()); //Allow us the get the metrics for a specific font
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ applesEaten))/2,g.getFont().getSize());
    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont()); //Allow us the get the metrics for a specific font
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2); //allows us to put the text at the center
    }
    @Override
    public void actionPerformed(ActionEvent e) { //this invokes when a action occurs aka whenever I press a key
        if(running){
            move(); //snake movement
            checkApple(); //checks if apple has been eaten
            checkCollisions(); //check if snake has collided with walls or itself
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{ //Allows java to recognize key presses
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){ //Only allows 90 degree turns
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){ //Only allows 90 degree turns
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){ //Only allows 90 degree turns
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){ //Only allows 90 degree turns
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
