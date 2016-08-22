import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class KotoServer{
    Socket kotoSocket;
    JFrame window;
    JButton sendButton;
    JPanel mainSpace;
    JTextArea messageList;
    JTextArea messageText;
    public static void main(String[] args) {
        new KotoServer().go();
    }
    public void go() {
        window = new JFrame("Kot-Server");
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
        try{
            ServerSocket serverSock = new ServerSocket(6666);
            while(true) {
                Socket clientSocket = serverSock.accept();
                //PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                Thread t = new Thread(new KotoMessenger(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public class sendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
        }
    }

    public class KotoMessenger implements Runnable {
        BufferedReader reader;
        Socket sock;

        public void run() {

            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    messageList.append(message);
                    System.out.println("read " + message);
                } // close while
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        public KotoMessenger(Socket clientSocket) {
            System.out.println("I'm alive!");
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } // close constructor


    }
}
