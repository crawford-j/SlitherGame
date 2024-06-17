import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    final int UNIT_SIZE = 25;
    int SCREEN_WIDTH;
    int SCREEN_HEIGHT;
    int GAME_UNITS;
    int DELAY;
    int[] x;
    int[] y;
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'E'; // NESW Directions
    boolean running = false;
    Timer timer;
    Random rand;
    Color snakeColor;

    JButton mainMenuButton;
    JFrame mainFrame;


    GamePanel(Settings settings, JFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Initialize all variables from settings
        switch (settings.gridSize) {
            case "SMALL":
                SCREEN_WIDTH = 300;
                SCREEN_HEIGHT = 300;
                break;
            case "MEDIUM":
                SCREEN_WIDTH = 600;
                SCREEN_HEIGHT = 600;
                break;
            case "LARGE":
                SCREEN_WIDTH = 900;
                SCREEN_HEIGHT = 900;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + settings.gridSize);
        }
        GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
        DELAY = settings.gameSpeed;
        snakeColor = settings.snakeColor;
        rand = new Random();

        // Initialize the arrays based on GAME_UNITS
        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];

        // Set panel properties
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(settings.backgroundColor);
        this.setFocusable(true);
        this.addKeyListener(new DirectionAdapter());

        // Initialize Buttons
        mainMenuButton = new JButton("Main Menu");

        // Set font and design for main menu button
        mainMenuButton.setFont(new Font("Mouse Memoirs", Font.PLAIN, 30));
        mainMenuButton.setForeground(Color.GREEN);
        mainMenuButton.setBackground(Color.BLACK);
        mainMenuButton.setFocusPainted(false);

        // Set visibility to false for both buttons
        mainMenuButton.setVisible(false);

        // Set button bounds
        this.setLayout(null);
        mainMenuButton.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 + 30, 200, 40);

        // Add main menu button
        this.add(mainMenuButton);

        // Add action listeners to the button
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new MainFrame(settings);
            }
        });

        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        this.requestFocusInWindow();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running) {
            // Draw visual grid for game
            for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            // Draw apples
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Draw snake
            for(int i = 0; i < bodyParts; i++){
                if(i == 0){ // Head color
                    g.setColor(snakeColor.brighter());
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else { // Body Color
                    g.setColor(snakeColor);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Mouse Memoirs", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = rand.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = rand.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    public void move(){
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(direction){
            case 'N':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'S':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'W':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'E':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        // Checks if head collides with body
        for(int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Checks if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        // Checks if head touches right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // Checks if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        // Checks if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        if(running == false) {
            timer.stop();
        }

    }
    public void gameOver(Graphics g){
        // Score
        g.setColor(Color.red);
        g.setFont(new Font("Mouse Memoirs", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        // Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Mouse Memoirs", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        // Make restart and main menu buttons visible
    //    restartButton.setVisible(true);
        mainMenuButton.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class DirectionAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch(key){
                case KeyEvent.VK_LEFT:
                    if(direction != 'E'){
                        direction = 'W';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'W'){
                        direction = 'E';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'S'){
                        direction = 'N';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'N'){
                        direction = 'S';
                    }
                    break;
            }
        }
    }
}
