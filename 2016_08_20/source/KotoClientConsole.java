import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class KotoClientConsole {
    Socket kotClientSocket;
    public KotoClientConsole(){
        try {
            kotClientSocket = new Socket ("127.0.0.1", 6666);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new KotoClientConsole().go();
    } //close main method

    public void go() {
        Thread kotick = new Thread(new KotPisaka());
        kotick.start();
        try {
            kotick.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //System.out.println("Go went gone!");
    } // close method

    public class KotPisaka implements Runnable {
        OutputStreamWriter kotOutWriter;
        BufferedWriter kotPisaka;
        public KotPisaka() {
            try{
                kotOutWriter = new OutputStreamWriter(kotClientSocket.getOutputStream());
                kotPisaka = new BufferedWriter(kotOutWriter);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } //close conctructor
        public void run() {
            try {
                kotPisaka.write("Ping me, pls");
                InputStreamReader isKot = new InputStreamReader(kotClientSocket.getInputStream());
                BufferedReader kotChitacka = new BufferedReader(isKot);
                String message = null;
                while( (message=kotChitacka.readLine() )!=null){
                    System.out.println(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } //close run method
    } //close class
}
