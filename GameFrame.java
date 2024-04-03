import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel()); //Creates the gamePanel
        this.setTitle("Snake"); //Sets the title for the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the action what happens when you close the window
        this.setResizable(false); // Prevents users from resizing the window
        this.pack(); //tells the layout managers and components to size themselves optimally
        this.setVisible(true); // makes window visible
        this.setLocationRelativeTo(null); //sets the window to the center of the screen
    }
}
