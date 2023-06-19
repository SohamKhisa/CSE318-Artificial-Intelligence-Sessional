package algorithm;

import game.Board;
import main.JPair;

public class Heuristic {
    public int getHeuristic(int i, Board board, int playerid, int extramove) {
        int val = 0;
        JPair<Integer, Integer> store = board.getStoreStones();
        JPair<Integer, Integer> pit = board.getBinStones();
        if(i==1) {
            if(playerid==1) {
                val = (store.getElement0()-store.getElement1());
            }
            else {
                val = (store.getElement1()-store.getElement0());
            }
        }
        else if(i>=2) {
            if(playerid==1) {
                val = (13 * (store.getElement0() - store.getElement1()) + 7 * (pit.getElement0() - pit.getElement1()));
            }
            else {
                val = (13 * (store.getElement1() - store.getElement0()) + 7 * (pit.getElement1() - pit.getElement0()));
            }
            if(i==3)    val += 5 * extramove;
            else if(i==4)    val += 5 * board.possibleExtraMove(playerid);
            else if(i==5)   val += 7 * extramove + 5 * board.maxSteal(playerid);
            else if(i==6)   val += 7 * extramove + 5 * board.possibleExtraMove(playerid) + 5 * board.maxSteal(playerid);
        }
        return val;
    }
}
