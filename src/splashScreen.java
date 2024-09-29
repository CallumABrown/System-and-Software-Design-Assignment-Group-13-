import javax.swing.*;
import java.awt.*;

public class splashScreen extends JWindow {
    private int duration;

    public splashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBackground(Color.WHITE);

        ImageIcon splashImage = new ImageIcon("resources/splash.gif");
        int width = splashImage.getIconWidth();
        int height = splashImage.getIconHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        setBounds(x, y, width, height);

        // Create a JLayeredPane to layer components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));

        // Create and add splashBackground
        JLabel splashBackground = new JLabel(splashImage);
        splashBackground.setBounds(0, 0, width, height); // Make sure it covers the whole area
        layeredPane.add(splashBackground, JLayeredPane.DEFAULT_LAYER);

        // Create and add splashName
        JLabel splashName = new JLabel(new ImageIcon("resources/logo.png"));
        splashName.setBounds(0, 0, width, height); // Make sure it covers the whole area
        layeredPane.add(splashName, JLayeredPane.PALETTE_LAYER);

        JLabel topText = new JLabel("System and Software Design Assignment", SwingConstants.CENTER);
        topText.setBounds(0, 10, width, 30); // Position at the top
        topText.setFont(new Font("Arial", Font.BOLD, 10));
        topText.setForeground(Color.WHITE);
        layeredPane.add(topText, JLayeredPane.MODAL_LAYER);

        JLabel bottomText = new JLabel("By Adam Filipczuk, Steve Drewery, Callum Brown, Gauruv Grover", SwingConstants.CENTER);
        bottomText.setBounds(0, height - 60, width, 30); // Position at the bottom
        bottomText.setFont(new Font("Arial", Font.BOLD, 14));
        bottomText.setForeground(Color.BLACK);
        layeredPane.add(bottomText, JLayeredPane.MODAL_LAYER);

        // Create and add progressBar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBounds(0, height - 30, width, 20);
        layeredPane.add(progressBar, JLayeredPane.PALETTE_LAYER);

        // Set the layeredPane as the content pane
        setContentPane(layeredPane);
        pack(); // Resize the window to fit the content

        // Display the splash screen
        setVisible(true);

        // Simulate progress
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(duration / 100); // Simulate something being done
                progressBar.setValue(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setVisible(false);
    }

    public void displayAndClose() {
        showSplash();
        System.exit(0);
    }

    public static void main(String[] args) {
        splashScreen splashScreen1 = new splashScreen(2500);
        splashScreen1.displayAndClose();
    }
}
