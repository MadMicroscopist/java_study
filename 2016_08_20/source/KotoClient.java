import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class KotoClient {
    Socket kotoSocket;
    JFrame window;
    JButton sendButton;
    JPanel mainSpace;
    JTextArea messageList;
    JTextArea messageText;
    public static void main(String[] args) {
        KotoClient kot = new KotoClient();
        kot.buildGUI();
        //kot.go();
    }

    public KotoClient() {
        try{
            kotoSocket = new Socket("127.0.0.1", 6666);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void buildGUI() {

        window = new JFrame("Kot-Client");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messageText = new JTextArea(1, 20);
        messageText.setLineWrap(true);
            messageText.setWrapStyleWord(true);

            JScrollPane messageTextScroller = new JScrollPane(messageText);
            messageTextScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            messageTextScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        messageList = new JTextArea(10, 20);
        messageList.setLineWrap(true);
            messageList.setWrapStyleWord(true);

            JScrollPane messageListScroller = new JScrollPane(messageList);
            messageListScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            messageListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new sendButtonListener());

        mainSpace = new JPanel();
            mainSpace.add(messageListScroller);
            mainSpace.add(messageTextScroller);
            mainSpace.add(sendButton);

        window.getContentPane().add(BorderLayout.CENTER, mainSpace);
            window.setSize(500,600);
            window.setVisible(true);

        Thread listener = new Thread(new kotoListener());
        listener.start();
    }

    public class kotoListener implements Runnable {
        public void run() {
            String message = null;
            BufferedReader reader;
            try {
                InputStreamReader isReader = new InputStreamReader(kotoSocket.getInputStream());
                reader = new BufferedReader(isReader);
                while ((message = reader.readLine()) != null) {
                    messageList.append(message);
                    System.out.println("read " + message);
                } // close while
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class sendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            String message = messageText.getText().toString();

            try {
                PrintWriter kotPisaka = new PrintWriter(kotoSocket.getOutputStream());
                kotPisaka.println(message);
                kotPisaka.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            messageText.setText("");
        }
    }
}
