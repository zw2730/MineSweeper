import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class GameButtonMouseListener implements MouseListener {
    private Minesweeper ms;
    public GameButtonMouseListener(Minesweeper ms){
        this.ms = ms;
    }
    public void recursiveClick(int x, int y){
        if(ms.flags[x][y]==1){
            ms.remain_bomb++;
            ms.bottomLabel.setText(String.valueOf(ms.remain_bomb));
        }
        Icon icz = new ImageIcon("0.png");
        ms.gameButton[x][y].setIcon(icz);
        ms.gameButton[x][y].removeMouseListener(ms.gameButton[x][y].getMouseListeners()[1]);
        ms.dfs.clicked[x][y] = 1;
        ms.remain_unClick--;
        ms.dfs.unclicked = ms.remain_unClick;
        if(ms.remain_unClick==40){
            ms.dfs.state = 1;
            for(int ii=0;ii<16;ii++){
                for(int jj=0;jj<16;jj++){
                    if(ms.gameButton[ii][jj].getMouseListeners().length>=2) {
                        ms.gameButton[ii][jj].removeMouseListener(ms.gameButton[ii][jj].getMouseListeners()[1]);
                    }
                }
            }
            ms.bottomLabel.setText("Game Success!");
            ms.tm.cancel();
        }
        for(int i=x-1;i<=x+1;i++){
            for(int j=y-1;j<=y+1;j++){
                if(i==x&&j==y){
                    continue;
                }
                if(i<0||j<0||i>=16||j>=16){
                    continue;
                }
                if(ms.gameButton[i][j].getMouseListeners().length==2){
                    int n = ms.gd.data[i][j];
                    if(n==0){
                        recursiveClick(i,j);
                    }
                    else {
                        if(ms.flags[i][j]==1){
                            ms.remain_bomb++;
                            ms.bottomLabel.setText(String.valueOf(ms.remain_bomb));
                        }
                        Icon ic = new ImageIcon(n + ".png");
                        ms.gameButton[i][j].setIcon(ic);
                        ms.gameButton[i][j].removeMouseListener(ms.gameButton[i][j].getMouseListeners()[1]);
                        ms.dfs.clicked[i][j] = 1;
                        ms.remain_unClick--;
                        ms.dfs.unclicked = ms.remain_unClick;
                        if(ms.remain_unClick==40){
                            ms.dfs.state = 1;
                            for(int ii=0;ii<16;ii++){
                                for(int jj=0;jj<16;jj++){
                                    if(ms.gameButton[ii][jj].getMouseListeners().length>=2) {
                                        ms.gameButton[ii][jj].removeMouseListener(ms.gameButton[ii][jj].getMouseListeners()[1]);
                                    }
                                }
                            }
                            ms.bottomLabel.setText("Game Success!");
                            ms.tm.cancel();
                        }
                    }
                }
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        JButton clickButton = (JButton) e.getSource();
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(clickButton==ms.gameButton[i][j]){
                    if(e.getButton()==MouseEvent.BUTTON1){
                        int n = ms.gd.data[i][j];
                        if(ms.flags[i][j]==1){  //flags cannot be left clicked!
                            return;
                        }
                        if(n==-1){
                            //System.out.println(2);
                            for(int ii=0;ii<16;ii++){
                                for(int jj=0;jj<16;jj++){
                                    if(ms.gd.data[ii][jj]==-1){
                                        Icon icbomb = new ImageIcon("9.png");
                                        ms.gameButton[ii][jj].setIcon(icbomb);
                                        ms.dfs.clicked[ii][jj] = 1;
                                        ms.dfs.state = -1;
                                        //System.out.println(1);
                                    }
                                    //System.out.println(1);
                                    if(ms.gameButton[ii][jj].getMouseListeners().length>=2) {
                                        //System.out.println(3);
                                        ms.gameButton[ii][jj].removeMouseListener(ms.gameButton[ii][jj].getMouseListeners()[1]);
                                        //ms.gameButton[ii][jj].removeMouseListener(ms.gameButton[ii][jj].getMouseListeners()[ct]);
                                        //ms.gameButton[ii][jj].removeAll();
                                        //System.out.println(ii+" "+jj);
                                    }
                                }
                            }
                            ms.bottomLabel.setText("Game Over");
                            ms.tm.cancel();
                            return;
                        }
                        else if(n==0){
                            recursiveClick(i,j);
                        }
                        else {
                            Icon ic = new ImageIcon(n + ".png");
                            clickButton.setIcon(ic);
                            clickButton.removeMouseListener(this);
                            ms.dfs.clicked[i][j] = 1;
                            ms.remain_unClick--;
                            ms.dfs.unclicked = ms.remain_unClick;
                            if (ms.remain_unClick == 40) {
                                ms.dfs.state = 1;
                                for (int ii = 0; ii < 16; ii++) {
                                    for (int jj = 0; jj < 16; jj++) {
                                        if (ms.gameButton[ii][jj].getMouseListeners().length >= 2) {
                                            ms.gameButton[ii][jj].removeMouseListener(ms.gameButton[ii][jj].getMouseListeners()[1]);
                                        }
                                    }
                                }
                                ms.bottomLabel.setText("Game Success!");
                                ms.tm.cancel();
                            }
                        }
                    }
                    else{
                        if(ms.flags[i][j]==0){
                            Icon icf = new ImageIcon("11.png");
                            ms.gameButton[i][j].setIcon(icf);
                            ms.flags[i][j] = 1;
                            ms.remain_bomb--;
                            ms.bottomLabel.setText(String.valueOf(ms.remain_bomb));
                        }
                        else{
                            Icon ic = new ImageIcon("10.png");
                            ms.gameButton[i][j].setIcon(ic);
                            ms.flags[i][j] = 0;
                            ms.remain_bomb++;
                            ms.bottomLabel.setText(String.valueOf(ms.remain_bomb));
                        }
                    }
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
}
