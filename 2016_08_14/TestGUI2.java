import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestGUI2{
    JFrame window;
    JLabel label;
    JPanel panel;

    public static void main(String[] args) {
        TestGUI2 newGUI = new TestGUI2();
        newGUI.go();
    }

    public void go(){
        window = new JFrame("Test GUI");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton labelButton = new JButton("Click me to change label text");
        labelButton.addActionListener(new LabelListener());

        JButton colorButton = new JButton("Click me to change label color");
        colorButton.addActionListener(new ColorListener());

        panel = new JPanel();
        panel.setBackground(Color.white);

        label = new JLabel("Default label text");
        panel.add(label);
        window.getContentPane().add(BorderLayout.SOUTH, panel);
        window.getContentPane().add(BorderLayout.EAST, labelButton);
        window.getContentPane().add(BorderLayout.WEST, colorButton);

        window.setSize(500,500);
        window.setVisible(true);
    }

    class LabelListener implements ActionListener{
        public void actionPerformed(ActionEvent llevent){
            label.setText("New label text");
        }
    }

    class ColorListener implements ActionListener{
        public void actionPerformed(ActionEvent clevent){
            panel.setBackground(Color.RED);
            label.setText("New new label text");
        }
    }
}
