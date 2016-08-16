import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TestNetworkServer {

        // variables for GUI
        JFrame window;
        JPanel mainSpace;

        private JTextArea spaceToType;

        public static void main(String[] args) {
            new TestNetworkServer().buildGUI();
        }

        public void buildGUI(){
            window = new JFrame("Test window for file save");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            //copy-paste!!!!!
            BorderLayout layout = new BorderLayout();
            JPanel background = new JPanel(layout);
            background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            //copy-paste END

            JButton clearButton = new JButton("Clear text");
            clearButton.addActionListener(new clearButtonListener());
            JButton startButton = new JButton("Start server");
            startButton.addActionListener(new startButtonListener());

            spaceToType = new JTextArea(6, 20);
            spaceToType.setLineWrap(true);
            spaceToType.setWrapStyleWord(true);

            JScrollPane testScroller = new JScrollPane(spaceToType);
            testScroller.setVerticalScrollBarPolicy(
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            testScroller.setHorizontalScrollBarPolicy(
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            mainSpace = new JPanel();
            mainSpace.add(testScroller);
            mainSpace.add(clearButton);
            mainSpace.add(startButton);

            //MENU!!!!!
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem loadMenuItem = new JMenuItem("Load");

            JMenuItem saveMenuItem = new JMenuItem("Save");
            loadMenuItem.addActionListener(new LoadMenuListener());
            saveMenuItem.addActionListener(new SaveMenuListener());

            fileMenu.add(loadMenuItem);
            fileMenu.add(saveMenuItem);
            menuBar.add(fileMenu);
            window.setJMenuBar(menuBar);
            //MENU END

            window.getContentPane().add(BorderLayout.CENTER, mainSpace);
            window.setSize(500,600);
            window.setVisible(true);
        }

        public class clearButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent ev) {
                spaceToType.setText("");
                spaceToType.requestFocus();            //put cursor for text input
            }
        }

        public class startButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent ev) {
                try {
                    ServerSocket serverSock = new ServerSocket(4242);
                    while (true) {
                        Socket sock = serverSock.accept();

                        PrintWriter writer = new PrintWriter(sock.getOutputStream());
                        String hello = spaceToType.getText();
                        writer.println(hello);
                        writer.close();
                        System.out.println(hello);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public class LoadMenuListener implements ActionListener {
                public void actionPerformed(ActionEvent ev) {
                    JFileChooser fileLoad = new JFileChooser();
                    fileLoad.showOpenDialog(window);
                    loadFile(fileLoad.getSelectedFile());
                }
        }

        public class SaveMenuListener implements ActionListener {
                public void actionPerformed(ActionEvent ev) {
                    JFileChooser fileSave = new JFileChooser();
                    fileSave.showSaveDialog(window);
                    saveFile(fileSave.getSelectedFile());
                }
        }

        private void saveFile(File file) {

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(spaceToType.getText());
                writer.close();
            } catch(IOException ex) {
                System.out.println("couldn't write your text out");
                ex.printStackTrace();
            }
        }

        private void loadFile(File file) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null){
                    spaceToType.setText(line + "\n");
                }
            } catch(Exception ex){
                System.out.println("couldn't read the file");
                ex.printStackTrace();
            }
        } //close method
    }
