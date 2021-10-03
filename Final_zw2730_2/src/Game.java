import java.io.Serializable;

public class Game {
    public static final long serialVersionUID = 1L;
    Minesweeper currentGame;
    public Game(){
        currentGame = new Minesweeper(this);
    }
    public Game(DataForStorage loadData){
        currentGame = new Minesweeper(this,loadData);
    }
    public void newGame(){
        currentGame.setVisible(false);
        currentGame = new Minesweeper(this);
        currentGame.setVisible(true);
    }
    public void loadGame(DataForStorage loadData){
        currentGame.setVisible(false);
        currentGame = new Minesweeper(this,loadData);
        currentGame.setVisible(true);
    }
//    public void endGame(){
//        System.exit(0);
//    }
    public static void main(String[] args){
        Game game = new Game();
        game.currentGame.setVisible(true);
    }
}
