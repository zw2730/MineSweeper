import jdk.nashorn.internal.runtime.ListAdapter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GameData  {
    public int[][] data; //-1:bomb, 0-8:number of bombs at surrounding positions.
    public GameData(){
        data = new int[16][16];
        Set<Integer> res = this.getRandomSet(40,16*16);
        for (int d:
             res) {
            int i = d/16;
            int j = d%16;
            //System.out.println(i+" "+j);
            data[i][j] = -1;//Bomb
        }
        for(int x=0;x<16;x++){
            for(int y=0;y<16;y++){
                int cnt=0;
                if(data[x][y]==-1){
                    continue;
                }
                for(int i=x-1;i<=x+1;i++){
                    for(int j=y-1;j<=y+1;j++){
                        if(i==x&&j==y){
                            continue;
                        }
                        if(i<0||j<0||i>=16||j>=16){
                            continue;
                        }
                        if(data[i][j]==-1){
                            cnt++;
                        }
                    }
                }
                data[x][y] = cnt;
            }
        }
    }
    public Set<Integer> getRandomSet(int size, int max) {
        Random random = new Random();
        Set<Integer> result = new LinkedHashSet<Integer>();
        while (result.size() < size) {
            Integer next = random.nextInt(max-1) + 1;
            result.add(next);
        }
        //System.out.println(result);
        return result;
    }
//    public static void main(String[] args){
//        GameData g = new GameData();
//        //g.getRandomSet(40,16*16);
//        for(int i=0;i<16;i++){
//            for(int j=0;j<16;j++){
//                System.out.print(" ");
//                System.out.print(g.data[i][j]);
//                if(j==15){
//                    System.out.print("\n");
//                }
//            }
//        }
//    }
}
