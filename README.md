# JavaSnakeGame

- **Tools used**
    - Java
    - JPanel
    - JFrame
    - ActionListener

# Step 1

- Create 3 classes
    - Main Class
        
        ```java
        public class SnakeGame {
            public static void main(String[] args){
        
                new GameFrame();
        
            }
        }
        
        ```
        
        Initiate GameFrame
        
    - GameFrame Class
        
        ```java
        import javax.swing.*;
        import java.awt.*;
        
        public class GameFrame extends JFrame {
            GameFrame(){
            
            }
        }
        
        ```
        
        GameFrame class will be responsible for building the window, where our game components will go on
        
    - GamePanel Class
        
        ```java
        
        import java.awt.*;
        import java.awt.event.*;
        
        public class GamePanel extends JPanel implements ActionListener {
        
            @Override
            public void actionPerformed(ActionEvent e) {
        
            }
        }
        
        ```
        
        This class will be responsible for the components and their actions
        

# Step 2

- Create the window for the game by using JFrame Methods
    
    ```java
        GameFrame(){
            this.add(new GamePanel()); //Creates the gamePanel
            this.setTitle("Snake"); //Sets the title for the frame
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the action what happens when you close the window
            this.setResizable(false); // Prevents users from resizing the window
            this.pack(); //tells the layout managers and components to size themselves optimally
            this.setVisible(true); // makes window visible
            this.setLocationRelativeTo(null); //sets the window to the center of the screen
        }
    ```
    

# Step 3

- Create all the methods the game will need in the GamePanel Class
    
    ```java
        public void startGame(){
    
        }
        public void paintComponent(Graphics g){
    
        }
        public void draw(Graphics g){
    
        }
        public void move(){
    
        }
        public void newApple(}{
        
        }
        public void checkApple(){
    
        }
        public void checkCollisions(){
    
        }
        public void scoreCounter(Graphics g){
    	   
        }
        public void gameOver(Graphics g){
    
        }
    ```
    
- Create a class that extends KeyAdapter in the GamePanel class and override the method keyPressed
    
    ```java
        public class MyKeyAdapter extends KeyAdapter{ //Allows java to recognize key presses
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        }
    ```
    
- Create all the attributes needed in the GamePanel Class
    
    ```java
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
    ```
    

# Step 4

- It’s time to create the actual panel where our game will be played on
    
    In the Gamepanel Constructer add the following 
    
    ```java
    GamePanel(){
            random = new Random();
            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); //Sets the size
            this.setBackground(Color.black); //background color
            this.setFocusable(true); // generally used for keyboard inputs allow the components to understand keyboard presses
            this.addKeyListener(new MyKeyAdapter()); // adds the specified key listener to receive Key events from this component
            startGame();
        }
    ```
    
- Set up startGame() and paintComponent() methods
    
```java
    public void startGame(){
            newApple(); //Populate the game with a new apple
            running = true;
            timer = new Timer(DELAY, this); //Listen for the user input to control snake direction, "this" refers to ActionListener
            timer.start();
    }
    
    public void paintComponent(Graphics g){ //paint the components
    				super.paintComponent(g);
    }
```
    
You should get something like this
    
![image](https://github.com/Jonnynavi/SnakeGameJava/assets/118036410/4ecdb0d8-85b9-4391-b49a-582a9ae389d6)
    Now this part is optional but it will create a grid in order for you to see the unit size
    
```java
     public void draw(Graphics g){
          for(int i = 0; i <SCREEN_HEIGHT/UNIT_SIZE;i++){
              g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);//Draws the diagonal lines
              g.drawLine(0, i*UNIT_SIZE,SCREEN_WIDTH, i*UNIT_SIZE);//Draws the horizontal lines
          }
      }
```

Now it should look like this
![image](https://github.com/Jonnynavi/SnakeGameJava/assets/118036410/89d4cc79-347a-4432-b788-6c965700c6b9)
    
- Populate apples in the game
    
    First Create the logic where apples will appear on the panel
    
    ```java
        public void newApple(){ //populate the panel with new apples
            appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; //randomly chooses x coordinates for apple
            appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;//randomly chooses y coordinates for apple
        }
    ```
    
    Next, you will draw the apples within the draw method
    
    ```java
    public void draw(Graphics g){
            for(int i = 0; i <SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE,SCREEN_WIDTH, i*UNIT_SIZE);
            }
            
            g.setColor(Color.red); //sets the color for apples to red
            g.fillOval(appleX,appleY, UNIT_SIZE, UNIT_SIZE); //creates a oval in the randomly generated coordinates
            //fillOval(x, y, width, height)
    }
    ```
    
    That should look something like this
    
![image](https://github.com/Jonnynavi/SnakeGameJava/assets/118036410/7d41cebc-c717-4391-9b2c-b1830022af80)
    
- Draw the snake and write the logic for the move() method
    
    Create the logic for the snake's movement
    
    ```java
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
    ```
    
    Within the Draw method create the snake 
    
    ```java
    //Snake
    for(int i = 0; i < bodyParts; i++){ //Loop through x and y array to create the snake
        if(i == 0) { //Creates the head
            g.setColor(Color.green);
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }else{
            g.setColor(new Color(45,180,0));
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }
    ```
    
    Now in the actionPerformed method add
    
    ```java
    public void actionPerformed(ActionEvent e) { //this invokes when a action occurs aka whenever I press a key
        if(running){
            move(); //snake movement
            checkApple(); //checks if apple has been eaten
            checkCollisions(); //check if snake has collided with walls or itself
        }
        repaint();
    }
    ```
    
    Your snake should now move to the right and stop once it hits the right border
    
![image](https://github.com/Jonnynavi/SnakeGameJava/assets/118036410/e11abed3-4c09-45ae-87d4-c8706f84ac0d)
    
    Now it's time to write what happens when a key is pressed
    
    ```java
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
    ```
    
    You should now be able to control the snake with your arrow keys
    
- Make the snake get larger and another random apple is created after one is eaten
    
    ```java
    public void checkApple(){ //checks if snake has eaten apple
        if(appleY == y[0] && appleX == x[0]){ //if head and apple coordinates are the same
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    ```
    

# Step 5

- Create gameover screen
    
    Wrap an “if” statement for the code that you have written inside the draw method. This will allow us to separate the game screen and the game over screen
    
    ```java
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
        }else{
            gameOver(g);
        }
    }
    ```
    
    In the gameOver method create a black screen with text that states “Game Over”
    
    ```java
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont()); //Allow us the get the metrics for a specific font
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2); //allows us to put the text at the center 
    }
    ```
    
    You should now see the “Game Over” screen once you hit the snake body or borders
    
![image](https://github.com/Jonnynavi/SnakeGameJava/assets/118036410/02e6e740-1b7e-42ae-84b1-b97eb9ea69e5)
    
- Create a score counter
    
    you can copy and paste the gameOver logic into the scoreCounter method 
    
    you only need to make changes to the setFont and the drawString method
    
    ```java
        public void scoreCounter(Graphics g){
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont()); //Allow us the get the metrics for a specific font
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ applesEaten))/2,g.getFont().getSize());//
        }
    ```
    
    you should now have a score counter on the very top 
    
![image](https://github.com/Jonnynavi/SnakeGameJava/assets/118036410/69051fb3-3c7f-4988-8ba6-e17230d920dd)
