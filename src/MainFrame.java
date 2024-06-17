import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel cards;
    private CardLayout cardLayout;
    private Settings userSettings;

    public MainFrame(Settings settings) {
        // Initialize the settings object
        userSettings = settings;

        // Create the CardLayout and set it as the layout manager for the frame
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        getContentPane().add(cards);

        // Create and add the menu panel to the cards panel
        JPanel menuPanel = createMenuPanel();
        cards.add(menuPanel, "MenuPanel");

        // Create and add the settings panel to the cards panel
        JPanel settingsPanel = createSettingsPanel();
        cards.add(settingsPanel, "SettingsPanel");

        // Show the menu panel by default
        cardLayout.show(cards, "MenuPanel");

        // Set frame properties
        setTitle("Slither");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Main Menu Panel
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(800, 800));
        menuPanel.setBackground(Color.BLACK);

        // Create Title frame and add it to the top of panel
        JLabel title = new JLabel(" ~ Slither ~ ", SwingConstants.CENTER);
        Font mouseL = new Font("Mouse Memoirs", Font.BOLD, 80);
        Font mouseS = new Font("Mouse Memoirs", Font.PLAIN, 30);
        title.setFont(mouseL);
        title.setForeground(Color.GREEN);
        title.setBorder(new LineBorder(Color.GREEN, 2));

        // Create panel for title
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setPreferredSize(new Dimension(800, 100));
        titlePanel.setBackground(Color.BLACK);
        titlePanel.add(title);

        // Create constraints for title label
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titlePanel.add(title, titleConstraints);

        // Set layout for main panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(titlePanel, constraints);

        // Create panel for the buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.BLACK);
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        JButton playButton = new JButton(" Play ");
        playButton.setFont(mouseS);
        playButton.setForeground(Color.GREEN);
        playButton.setBackground(Color.BLACK);
        playButton.setBorder(new LineBorder(Color.GREEN, 2));
        buttonPanel.add(playButton, buttonConstraints);

        buttonConstraints.gridx = 1;
        JButton settingsButton = new JButton(" Settings ");
        settingsButton.setFont(mouseS);
        settingsButton.setForeground(Color.GREEN);
        settingsButton.setBackground(Color.BLACK);
        settingsButton.setBorder(new LineBorder(Color.GREEN, 2));
        buttonPanel.add(settingsButton, buttonConstraints);

        buttonConstraints.gridx = 2;
        JButton exitButton = new JButton(" Exit ");
        exitButton.setFont(mouseS);
        exitButton.setForeground(Color.RED);
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorder(new LineBorder(Color.RED, 2));
        buttonPanel.add(exitButton, buttonConstraints);

        // Define constraints for the button panel
        constraints.gridy = 2;
        menuPanel.add(buttonPanel, constraints);

        // Add action listeners to buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user settings from MainFrame
                Settings settings = getUserSettings();

                //Create a new JFrame for the game
                JFrame gameFrame = new JFrame("Slither");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setResizable(false);

                // Create te game panel with the user settings
                GamePanel gamePanel = new GamePanel(userSettings, gameFrame);

                // Set the game frame size and add the game panel to it
                gameFrame.getContentPane().add(gamePanel);
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);

                // Dispose of the main menu frame
                dispose();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition to settings panel
                cardLayout.show(cards, "SettingsPanel");
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit game
                System.exit(0);
            }
        });

        return menuPanel;
    }

    // Settings Panel
    public JPanel createSettingsPanel() {
        Font mouseS = new Font("Mouse Memoirs", Font.PLAIN, 30);
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setPreferredSize(new Dimension(800, 800));
        settingsPanel.setBackground(Color.BLACK);

        // Create constraints for the settings panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 10, 10, 10); // Padding

        // Add buttons to the settings panel
        JButton snakeColorButton = new JButton(" Set Snake Color ");
        snakeColorButton.setFont(mouseS);
        snakeColorButton.setForeground(Color.GREEN);
        snakeColorButton.setBackground(Color.BLACK);
        snakeColorButton.setBorder(new LineBorder(Color.GREEN, 2));
        settingsPanel.add(snakeColorButton, constraints);

        JButton gameSpeedButton = new JButton(" Set Game Speed ");
        gameSpeedButton.setFont(mouseS);
        gameSpeedButton.setForeground(Color.GREEN);
        gameSpeedButton.setBackground(Color.BLACK);
        gameSpeedButton.setBorder(new LineBorder(Color.GREEN, 2));
        settingsPanel.add(gameSpeedButton, constraints);

        JButton gridSizeButton = new JButton(" Set Grid Size ");
        gridSizeButton.setFont(mouseS);
        gridSizeButton.setForeground(Color.GREEN);
        gridSizeButton.setBackground(Color.BLACK);
        gridSizeButton.setBorder(new LineBorder(Color.GREEN, 2));
        settingsPanel.add(gridSizeButton, constraints);

        JButton backgroundColorButton = new JButton(" Set Background Color ");
        backgroundColorButton.setFont(mouseS);
        backgroundColorButton.setForeground(Color.GREEN);
        backgroundColorButton.setBackground(Color.BLACK);
        backgroundColorButton.setBorder(new LineBorder(Color.GREEN, 2));
        settingsPanel.add(backgroundColorButton, constraints);

        JButton backButton = new JButton(" Back ");
        backButton.setFont(mouseS);
        backButton.setForeground(Color.RED);
        backButton.setBackground(Color.BLACK);
        backButton.setBorder(new LineBorder(Color.RED, 2));
        settingsPanel.add(backButton, constraints);

        // Add action listeners to buttons
        snakeColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSettings.snakeColor = JColorChooser.showDialog(null, "Choose Snake Color", userSettings.snakeColor);
            }
        });
        gameSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String speed = JOptionPane.showInputDialog(null, "Enter Game Speed in Milliseconds (ex. 100):");
                userSettings.gameSpeed = Integer.parseInt(speed);
            }
        });
        gridSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String size = JOptionPane.showInputDialog(null, "Enter Game Size (Small, Medium, or Large):");
                switch (size.toUpperCase().trim()) {
                    case ("SMALL"):
                         userSettings.gridSize = "SMALL";
                        break;
                    case ("MEDIUM"):
                        userSettings.gridSize = "MEDIUM";
                        break;
                    case ("LARGE"):
                        userSettings.gridSize = "LARGE";
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid Game Size. Please enter: Small, Medium, or Large.");
                        actionPerformed(e);
                }
            }
        });
        backgroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSettings.backgroundColor = JColorChooser.showDialog(null, "Choose Background Color", userSettings.backgroundColor);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Transition back to the menu panel
                cardLayout.show(cards, "MenuPanel");
            }
        });


        return settingsPanel;
    }
    // Getter for user settings
    public Settings getUserSettings() {
        return userSettings;
    }
}
