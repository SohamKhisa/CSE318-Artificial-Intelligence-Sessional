package algorithm;

import game.Board;
import main.JPair;

import java.util.ArrayList;
import java.util.Collections;

public class MiniMax {
    Heuristic heuristic;

    public MiniMax() {
        this.heuristic = new Heuristic();
    }

    public JPair<Integer, Integer> run(Board board, boolean maxplayer, int depth, int playerid, int alpha, int beta, int h, int extramove) {
        if (depth==0 || board.isOver()) {
            int val, whichmove;
            val = heuristic.getHeuristic(h, board, playerid, extramove);       //Here, board is not the current board, rather the one at the leaf
            whichmove = board.getWhichmove();
            return JPair.createPair(val, whichmove);
        }
        if(maxplayer) {
            int maxval = Integer.MIN_VALUE;
            int whichmove = 0;
            JPair<Integer, Integer> pair = new JPair<>(0, 0);
            ArrayList<Board> children = board.getAllChildren(playerid);
            Collections.shuffle(children);      //changing move ordering
            for (Board b: children) {
                int pid;
                if(playerid==1) pid = 2;
                else pid = 1;
                if(b.isExtraMove())
                    pair = run(b, true, depth-1, playerid, alpha, beta, h, extramove+1);
                else
                    pair = run(b, false, depth-1, pid, alpha, beta, h, extramove);
                int val = pair.getElement0();
                if(val >= maxval) {
                    maxval = val;
                    whichmove = b.getWhichmove();
                    pair = JPair.createPair(val, whichmove);
                }
                else
                    pair = JPair.createPair(maxval, whichmove);
                alpha = Integer.max(alpha, val);
                if(beta <= alpha) break;
            }
            return pair;
        }
        else {
            int minval = Integer.MAX_VALUE;
            int whichmove = 0;
            JPair<Integer, Integer> pair = new JPair<>(0, 0);
            ArrayList<Board> children = board.getAllChildren(playerid);
            Collections.shuffle(children);
            for(Board b: children) {
                int pid;
                if(playerid==1) pid = 2;
                else pid = 1;
                if(b.isExtraMove())
                    pair = run(b, false, depth-1, playerid, alpha, beta, h, extramove+1);
                else
                    pair = run(b, true, depth-1, pid, alpha, beta, h, extramove);
                int val = pair.getElement0();
                if(val <= minval) {
                    minval = val;
                    whichmove = b.getWhichmove();
                    pair = JPair.createPair(val, whichmove);
                }
                else
                    pair = JPair.createPair(minval, whichmove);
                beta = Integer.min(beta, val);
                if(beta <= alpha) break;
            }
            return pair;
        }
    }
}
