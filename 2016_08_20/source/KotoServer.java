import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import javax.swing.*;

public class KotoServer{
    //Socket kotoSocket;
    public static void main(String[] args) {
        new KotoServer().go();
    }
    public void go() {

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

    public class KotoMessenger implements Runnable {
        BufferedReader reader;
        Socket sock;
        InputStreamReader isReader;

        public void run() {
            String message = null;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    KotoSender(sock, message);
                } // close while
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        public KotoMessenger(Socket clientSocket) {
            //System.out.println("I'm alive!");
            try {
                sock = clientSocket;
                isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } // close constructor

        public void KotoSender (Socket clientSocket, String message) {
            try {
                //Socket sock = clientSocket;
                String mess = message;
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                writer.println(mess);
                writer.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
