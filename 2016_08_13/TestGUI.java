import javax.swing.*;

public class TestGUI{
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("test");
        window.getContentPane().add(button);
        window.setSize(500,500);
        window.setVisible(true);
    }
}
