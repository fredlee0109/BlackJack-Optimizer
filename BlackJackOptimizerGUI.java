import javax.swing.*;
import java.awt.event.*;

public class Test implements ActionListener {
    JButton button;

    public void go() {
        JFrame frame = new JFrame();
        button = new JButton("Click to Start BlackJackOptimizer");

        //this says "Add me to your list of listeners." The argument
        //must be an object from a class that implements ActionListener in this case
        button.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        button.setText("Welcome to BlackJackOptimizer.");
    }

    public static void main(String[] args) {
        Test gui = new Test();
        gui.go();
    }
}

// public class Test extends JPanel {
//     public void paintComponent(Graphics g) {
//     	g.setColor(Color.orange);
//     	g.fillRect(20,50,100,100);
//     }
// }