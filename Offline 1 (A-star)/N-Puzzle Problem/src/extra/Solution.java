package extra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Solution {
    private Node tiles;
    private int[][] goal;
    private PriorityQueue<Node> openList;
    private HashMap<Node, Boolean> closedList;

    public Solution(Node initial, int[][] goal) {
        this.tiles = initial;
        this.goal = goal;
        openList = new PriorityQueue<>(new NodeComparator());
        closedList = new HashMap<>();
    }

    public Node solve() {
        openList.add(tiles);
        Node finalNode = null;
        while(!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.put(current, true);
            //System.out.println();
            //current.printBoard();
            if(Arrays.deepEquals(current.getTiles(), goal)) {
                finalNode = current;
                break;
            }
            ArrayList<Node> expands = current.expand();
            //System.out.println("number of expands: "+expands.size());

            for(Node exp: expands) {
                if(closedList.get(exp)==null)
                    openList.add(exp);
            }
        }
        return finalNode;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public HashMap<Node, Boolean> getClosedList() {
        return closedList;
    }
}
