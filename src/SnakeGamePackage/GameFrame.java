package SnakeGamePackage;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); //will make the game appear in the center
    }
}
