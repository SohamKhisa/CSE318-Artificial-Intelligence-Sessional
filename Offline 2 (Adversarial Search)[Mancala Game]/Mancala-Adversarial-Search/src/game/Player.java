package game;

import algorithm.MiniMax;
import main.JPair;

public class Player {
    int id;
    Board board=null;
    boolean maxplayer;
    int depth, heuristic;

    public Player(int id, boolean maxplayer, int depth, int heuristic) {
        this.id = id;
        this.maxplayer = maxplayer;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board move(String gametype) {
        MiniMax algo = new MiniMax();
        JPair<Integer, Integer> pair;       //element0 contains which move to take, element1 contains heuristic value
        if(maxplayer)
            pair = algo.run(board, true, depth, id, Integer.MIN_VALUE, Integer.MAX_VALUE, heuristic, 0);
        else
            pair = algo.run(board, false, depth, id, Integer.MIN_VALUE, Integer.MAX_VALUE, heuristic, 0);
        int whichmove = pair.getElement1();
        if(!gametype.equalsIgnoreCase("CC"))
            System.out.println("Bin choosen: " + whichmove);
        return board.move(id, whichmove);
    }
}
