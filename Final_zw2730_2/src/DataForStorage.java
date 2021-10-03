import java.io.Serializable;

public class DataForStorage implements Serializable {
    public int[][] data;
    public int[][] clicked;
    public int unclicked;
    //public int[][] flaged;
    public int remain_time;
    public int state; // 0:continue, -1: Game Over, 1: Game Success!
    public DataForStorage(){
        data = new int[16][16];
        clicked = new int[16][16];
        //flaged = new int[16][16];
        unclicked = 16*16;
        remain_time = 1000;
        state = 0;
    }
}
