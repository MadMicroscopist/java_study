import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class KotoServerConsole {

    public static void main(String[] args) {
        new KotoServer().go();
    } //close method

    public void go() {
        try{
            ServerSocket kotServerSocket = new ServerSocket(6666);
            while(true) {
                Socket kotClientSocket = kotServerSocket.accept();
                Thread kotClientTread = new Thread(new KotoMessenger(kotClientSocket));
                kotClientTread.start();
                kotClientTread.sleep(200);
                System.out.println("got a connection");
                OutputStreamWriter osKot = new OutputStreamWriter(kotClientSocket.getOutputStream());
                BufferedWriter kotPisaka = new BufferedWriter(osKot);
                kotPisaka.write("Hello, %username! \n");
                kotPisaka.flush();
            } //close while loop
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    } //close method

    public class KotoMessenger implements Runnable {
        Socket kotClientSock;
        InputStreamReader inKotReader;
        BufferedReader inReader;
        public KotoMessenger (Socket kotClientSocket) {
            System.out.println("tread online!");
            try {
                kotClientSock = kotClientSocket;
                inKotReader = new InputStreamReader(kotClientSock.getInputStream());
                inReader = new BufferedReader(inKotReader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } //close constructor

        public void run() {
            System.out.println("tread online");
            try {
                String message = null;
                while ( (message = inReader.readLine()) != null) {
                    System.out.println("read " + message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } //close run method
    } //close inner class
}
