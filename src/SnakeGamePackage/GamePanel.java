package SnakeGamePackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGTH = 600;
    static final int UNIT_SIZE = 25; //this is how big the objects in the game will be
    static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGTH) / UNIT_SIZE; //how many objects will be on the screen
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNIT];  //holds the x coordinate of the snake body parts
    final int[] y = new int[GAME_UNIT]; //holds the y coordinate of the snake body parts
    int bodyParts = 6;
    int appleEaten;
    int appleX;   //y coordinate of where the apple is located
    int appleY;   //x coordinate of where the apple is located
    char direction = 'R';   //move rigth to begin with
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this); //using the action listener interface
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
            /*
            for(int i = 0; i < SCREEN_HEIGTH/ UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGTH); //the vertical line across the screen
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE); // the horizontal line across the screen
            }
            */
            g.setColor(Color.RED); //apple color
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);  //this is the apple size

            for(int i=0; i < bodyParts; i++){   // drawing the body part og the snake
                if(i == 0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i],y[i], UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    //g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i],y[i], UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score :"+ appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score :"+ appleEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE; //casting it to integer so it doesnt break our program
        appleY = random.nextInt((int) (SCREEN_HEIGTH/UNIT_SIZE)) * UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyParts; i > 0; i--){  //shifting the body parts by 1
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':  //going up
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':  //going down
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': //going left
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': // going rigth
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && y[0] == appleY){
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }
    public void checkCollision(){
        for(int i= bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false; //this means the head of the game had collided with the body of the snake x[0] is the head of the game
            }
        }
        if(x[0] < 0){
            running = false;  //if head touches the left border
        }
        if(x[0] > SCREEN_WIDTH){
            running = false;  //if head touches the rigth border
        }
        if(y[0] < 0){
            running = false;   //if head touches the top border
        }
        if(y[0] > SCREEN_HEIGTH){
            running = false;   //if head touches the down border
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //this display the score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score :"+ appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score :"+ appleEaten))/2, g.getFont().getSize());
        //this display if game is over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGTH/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        else {
            repaint();
        }
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
