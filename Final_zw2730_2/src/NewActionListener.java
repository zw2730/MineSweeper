import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Timer;

public class NewActionListener implements ActionListener {
    private Game g;
    public NewActionListener(Game g){
        this.g = g;
    }
    public void actionPerformed(ActionEvent e){
        g.newGame();
        //ms.newGame();
//        ms.setVisible(false);
//        ms = null;
//        Minesweeper newGame = new Minesweeper();
//        newGame.setVisible(true);

//        ms.gd = new GameData();
//        ms.flags = new int[16][16];
//        ms.remain_bomb = 40;
//        ms.remain_unClick = 16*16;
//        ms.remain_time=100;
//        ms.timeLabel = new JLabel("Time Remaining: 100");
//        ms.timePanel = new JPanel();
//        ms.topPanel = new JPanel();
//        ms.topPanel.setLayout(new GridLayout(2,1));
//        ms.menu = new JMenuBar();
//        JMenu file = new JMenu("File");
//        ms.menu.add(file);
//        JMenuItem item1 = new JMenuItem("New");
//        item1.addActionListener(new NewActionListener(ms));
//        JMenuItem item2 = new JMenuItem("Open");
//        JMenuItem item3 = new JMenuItem("Save");
//        JMenuItem item4 = new JMenuItem("Exit");
//        file.add(item1);
//        file.add(item2);
//        file.add(item3);
//        file.add(item4);
//        ms.bottomLabel = new JLabel(String.valueOf(ms.remain_bomb));
//        ms.gamePanel = new JPanel();
//        ms.gamePanel.setLayout(new GridLayout(16,16,0,0));
//        ms.gameButton = new JButton[16][16];
//        for(int i=0;i<16;i++){
//            for(int j=0;j<16;j++){
//
//
//                ImageButton imgBt = new ImageButton();
//                Icon ic = new ImageIcon("10.png");
//                imgBt.setIcon(ic);
//                imgBt.addMouseListener(new GameButtonMouseListener(ms));
//
//                ms.gameButton[i][j] = imgBt;
//                ms.gamePanel.add(ms.gameButton[i][j]);
//
//            }
//        }
//        ms.timePanel.add(ms.timeLabel);
//        ms.topPanel.add(ms.menu);
//        ms.topPanel.add(ms.timePanel);
//        ms.add(ms.gamePanel,BorderLayout.CENTER);
//        ms.add(ms.bottomLabel,BorderLayout.SOUTH);
//        ms.add(ms.topPanel,BorderLayout.NORTH);
//        java.util.Timer tm = new Timer();
//        tm.schedule(new TimeCounter(ms),0,1000);
    }
}
