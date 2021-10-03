import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper extends JFrame {
    public JPanel gamePanel;
    //private JPanel bottomPanel;
    public JLabel bottomLabel;
    public JPanel topPanel;
    public JButton[][] gameButton;
    public GameData gd;
    public int[][] flags;
    public int remain_bomb;
    public int remain_unClick;
    public int remain_time;
    public JLabel timeLabel;
    public JPanel timePanel;
    public JMenuBar menu;
    public Timer tm;
    //public JPanel main;
    //Minesweeper myself;
    public DataForStorage dfs;



    public Minesweeper(Game g){
        //main = getContentPane();
        this.setSize(600, 700);
        dfs = new DataForStorage();
        gd = new GameData();
        dfs.data = gd.data;
        flags = new int[16][16];
        remain_bomb = 40;
        remain_unClick = 16*16;
        remain_time=1000;
//        Timer tm = new Timer();
//        tm.schedule(new TimeCounter(this),0,1000);
        timeLabel = new JLabel("Time Remaining: 1000");
        timePanel = new JPanel();
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,1));
        menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem item1 = new JMenuItem("New");
        item1.addActionListener(new NewActionListener(g));
        JMenuItem item2 = new JMenuItem("Open");
//        item2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
        class ExitActionListener implements ActionListener{
            public Minesweeper ms;
            ExitActionListener(Minesweeper ms){
                this.ms = ms;
            }
            public void actionPerformed(ActionEvent e) {
                //dispatchEvent(new WindowEvent(ms,WindowEvent.WINDOW_CLOSING));
                System.exit(0);
                //Runtime.getRuntime().halt(0);
                //System.out.println("closing");
            }
        }

        JMenuItem item3 = new JMenuItem("Save");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = JOptionPane.showInputDialog("Type id of the game");
                //System.out.println(id);
                int id = Integer.parseInt(idString);
                try{
                    Socket socket = new Socket("localhost",8001);
                    DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
                    outputToServer.writeInt(id);
                    socket.shutdownOutput();
//                    ServerSocket serversocket = new ServerSocket(8002);
//                    Socket socketback = serversocket.accept();
                    ObjectInputStream InputFromServer = new ObjectInputStream(socket.getInputStream());
                    Object object = InputFromServer.readObject();
                    DataForStorage loadGame = (DataForStorage) object;
                    socket.close();
                    //socketback.close();
                    g.loadGame(loadGame);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Socket socket = new Socket("localhost",8000);
                    ObjectOutputStream outputToServer = new ObjectOutputStream(socket.getOutputStream());
                    outputToServer.writeObject(dfs);
                    socket.shutdownOutput();
                    //ServerSocket serversocket = new ServerSocket(8003);
                    //Socket socketback = serversocket.accept();
                    DataInputStream InputFromServer = new DataInputStream(socket.getInputStream());
                    int Id = InputFromServer.readInt();
                    JOptionPane.showMessageDialog(null,"Id of the saved game is: "+Id);
                    System.exit(0);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem item4 = new JMenuItem("Exit");
        item4.addActionListener(new ExitActionListener(this));
        file.add(item1);
        file.add(item2);
        file.add(item3);
        file.add(item4);
        //bottomPanel = new JPanel();
        bottomLabel = new JLabel(String.valueOf(remain_bomb));
        //bottomPanel.add(bottomLabel);
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(16,16,0,0));
        gameButton = new JButton[16][16];
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
//                ImageIcon img = new ImageIcon("10.png");
//                JLabel l = new JLabel(img);
//
//                //ImagePanel img = new ImagePanel("10.png");

                ImageButton imgBt = new ImageButton();
                Icon ic = new ImageIcon("10.png");
                imgBt.setIcon(ic);
                imgBt.addMouseListener(new GameButtonMouseListener(this));
//                ImageLabel imgbottom = new ImageLabel("");
//                Icon icb = new ImageIcon("0.png");
//                imgbottom.setIcon(icb);
//                JPanel gridPanel = new JPanel();
//                gridPanel.setLayout(null);
//                gridPanel.add(imgtop);
//                //p.add(l,BorderLayout.CENTER);
//                Image img = new ImageIcon("10.png").getImage();
//                //Image scaledImage = img.getScaledInstance(p.getWidth(),p.getHeight(),Image.SCALE_SMOOTH);
//                JLabel l = new JLabel(scaledImage);
//                p.add(scaledImage);
                gameButton[i][j] = imgBt;
                gamePanel.add(gameButton[i][j]);

                //gamePanel.add(imgbottom,i,j);
            }
        }
        timePanel.add(timeLabel);
        topPanel.add(menu);
        topPanel.add(timePanel);
        add(gamePanel,BorderLayout.CENTER);
        add(bottomLabel,BorderLayout.SOUTH);
        add(topPanel,BorderLayout.NORTH);
        tm = new Timer();
        tm.schedule(new TimeCounter(this),0,1000);
        //myself = this;
    }

    public Minesweeper(Game g, DataForStorage loadData){
        this.setSize(600, 700);
        this.dfs = loadData;
        gd = new GameData();
        gd.data = loadData.data;
        //dfs.data = gd.data;
        flags = new int[16][16];
        remain_bomb = 40;

        remain_unClick = loadData.unclicked;
        remain_time=loadData.remain_time;
//        Timer tm = new Timer();
//        tm.schedule(new TimeCounter(this),0,1000);
        timeLabel = new JLabel("Time Remaining: "+remain_time);
        timePanel = new JPanel();
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,1));
        menu = new JMenuBar();
        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem item1 = new JMenuItem("New");
        item1.addActionListener(new NewActionListener(g));
        JMenuItem item2 = new JMenuItem("Open");
        class ExitActionListener implements ActionListener{
            public Minesweeper ms;
            ExitActionListener(Minesweeper ms){
                this.ms = ms;
            }
            public void actionPerformed(ActionEvent e) {
                //dispatchEvent(new WindowEvent(ms,WindowEvent.WINDOW_CLOSING));
                System.exit(0);
                //Runtime.getRuntime().halt(0);
                //System.out.println("closing");
            }
        }

        JMenuItem item3 = new JMenuItem("Save");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = JOptionPane.showInputDialog("Type id of the game");
                //System.out.println(id);
                int id = Integer.parseInt(idString);
                try{
                    Socket socket = new Socket("localhost",8001);
                    DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());
                    outputToServer.writeInt(id);
                    socket.shutdownOutput();
//                    ServerSocket serversocket = new ServerSocket(8002);
//                    Socket socketback = serversocket.accept();
                    ObjectInputStream InputFromServer = new ObjectInputStream(socket.getInputStream());
                    Object object = InputFromServer.readObject();
                    DataForStorage loadGame = (DataForStorage) object;
                    socket.close();
                    //socketback.close();
                    g.loadGame(loadGame);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Socket socket = new Socket("localhost",8000);
                    ObjectOutputStream outputToServer = new ObjectOutputStream(socket.getOutputStream());
                    outputToServer.writeObject(dfs);
                    socket.shutdownOutput();
                    //ServerSocket serversocket = new ServerSocket(8003);
                    //Socket socketback = serversocket.accept();
                    DataInputStream InputFromServer = new DataInputStream(socket.getInputStream());
                    int Id = InputFromServer.readInt();
                    JOptionPane.showMessageDialog(null,"Id of the saved game is: "+Id);
                    System.exit(0);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem item4 = new JMenuItem("Exit");
        item4.addActionListener(new ExitActionListener(this));
        file.add(item1);
        file.add(item2);
        file.add(item3);
        file.add(item4);
        //bottomPanel = new JPanel();
        bottomLabel = new JLabel(String.valueOf(remain_bomb));
        //bottomPanel.add(bottomLabel);
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(16,16,0,0));
        gameButton = new JButton[16][16];
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
//                ImageIcon img = new ImageIcon("10.png");
//                JLabel l = new JLabel(img);
//
//                //ImagePanel img = new ImagePanel("10.png");
                if(loadData.clicked[i][j]==1){
                    ImageButton imgBt = new ImageButton();
                    int n = gd.data[i][j];
                    if(n==-1){
                        n = 9;
                    }
                    Icon ic = new ImageIcon(n+".png");
                    imgBt.setIcon(ic);
                    gameButton[i][j] = imgBt;
                    gamePanel.add(gameButton[i][j]);
                    continue;
                }
                ImageButton imgBt = new ImageButton();
                Icon ic = new ImageIcon("10.png");
                imgBt.setIcon(ic);
                if(loadData.state==0) {
                    imgBt.addMouseListener(new GameButtonMouseListener(this));
                }
//                ImageLabel imgbottom = new ImageLabel("");
//                Icon icb = new ImageIcon("0.png");
//                imgbottom.setIcon(icb);
//                JPanel gridPanel = new JPanel();
//                gridPanel.setLayout(null);
//                gridPanel.add(imgtop);
//                //p.add(l,BorderLayout.CENTER);
//                Image img = new ImageIcon("10.png").getImage();
//                //Image scaledImage = img.getScaledInstance(p.getWidth(),p.getHeight(),Image.SCALE_SMOOTH);
//                JLabel l = new JLabel(scaledImage);
//                p.add(scaledImage);
                gameButton[i][j] = imgBt;
                gamePanel.add(gameButton[i][j]);

                //gamePanel.add(imgbottom,i,j);
            }
        }
        timePanel.add(timeLabel);
        topPanel.add(menu);
        topPanel.add(timePanel);
        add(gamePanel,BorderLayout.CENTER);
        add(bottomLabel,BorderLayout.SOUTH);
        add(topPanel,BorderLayout.NORTH);
        tm = new Timer();
        if(loadData.state==0) {
            tm.schedule(new TimeCounter(this), 0, 1000);
        }
    }

//    public void newGame(){
//        myself = new Minesweeper();
//    }

//    public static void main(String[] args){
//        Minesweeper game = new Minesweeper();
//        game.setVisible(true);
//    }
}
