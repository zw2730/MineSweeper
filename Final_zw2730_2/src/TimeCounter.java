import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter extends TimerTask {
    Minesweeper ms;
    public TimeCounter(Minesweeper ms){
        this.ms = ms;
    }
    public void run(){
        if(ms.remain_time>0) {
            ms.remain_time--;
            ms.dfs.remain_time = ms.remain_time;
            ms.timeLabel.setText("Time Remaining: "+String.valueOf(ms.remain_time));
        }
        else{
            ms.dfs.state = -1;
            for(int ii=0;ii<16;ii++){
                for(int jj=0;jj<16;jj++){
                    if(ms.gameButton[ii][jj].getMouseListeners().length>=2) {
                        ms.gameButton[ii][jj].removeMouseListener(ms.gameButton[ii][jj].getMouseListeners()[1]);
                    }
                }
            }
            ms.bottomLabel.setText("Game Over");
        }
    }
}
