import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlackJackOptimizerGUI implements ActionListener {
    JFrame frame;
    public static void main(String[] args) {
        BlackJackOptimizerGUI gui = new BlackJackOptimizerGUI();
        gui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Change colors");
        button.addActionListener(this);

        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        frame.repaint();
    }

    class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            // g.setColor(Color.BLUE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);

            Color randomColor = new Color(red, green, blue);
            g.setColor(randomColor);
            g.fillOval(70, 70, 100, 100);
        }
    }
}

// public class BlackJackOptimizerGUI implements ActionListener {
//     JButton button;

//     public void go() {
//         JFrame frame = new JFrame();
//         button = new JButton("Click to Start BlackJackOptimizer");

//         //this says "Add me to your list of listeners." The argument
//         //must be an object from a class that implements ActionListener in this case
//         button.addActionListener(this);

//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.getContentPane().add(BorderLayout.CENTER, button);
//         frame.setSize(300,300);
//         frame.setVisible(true);
//     }

//     @Override
//     public void actionPerformed(ActionEvent event) {
//         button.setText("Welcome to BlackJackOptimizer.");
//     }

//     public static void main(String[] args) {
//         BlackJackOptimizerGUI gui = new BlackJackOptimizerGUI();
//         gui.go();
//     }
// }

// public class BlackJackOptimizerGUI extends JPanel {
//     public void paintComponent(Graphics g) {
//     	g.setColor(Color.orange);
//     	g.fillRect(20,50,100,100);
//     }
// }