package main;

import extra.Node;
import extra.Solution;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static int[][] goal;
    public static void main(String[] args) {
        int k;
        System.out.println("Please enter the grid size k:");
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        System.out.println("Please enter the initial board(enter * for the blank block):");
        try {
            k = Integer.parseInt(str);
            int[][] arr = new int[k][k];
            goal = new int[k][k];
            boolean[] check = new boolean[k*k];

            for (int i=0; i<k; i+=1) {
                str = input.nextLine();
                String[] token = str.split(" ");
                if(token.length>k) {
                    System.out.println("Error: Wrong Input. Too many values");
                    return;
                }
                for (int j=0; j<k; j+=1) {
                    if(token[j].equals("0"))
                        arr[i][j] = -1;
                    else {
                        int v = Integer.parseInt(token[j]);
                        if(v>(k*k-1) || v==0) {
                            System.out.println("Error: Wrong input. Each value in the board must be in the range [1 - " + (k*k-1) + "]");
                            return;
                        }
                        else if(check[v]) {
                            System.out.println("Error: Value " + v + " has already been entered");
                            return;
                        }
                        arr[i][j] = v;
                        check[v] = true;
                    }
                    if(i==k-1 && j==k-1)    goal[i][j] = -1;
                    else    goal[i][j] = (k*i+1)+j;
                }
            }
            Node initial = new Node(arr, "Hamming");
            if(isSolvable(initial.getTiles())) {
                printAnswer("Hamming", initial, 0);
                System.out.println("\n------------------------o---------------------------\n");
                initial = new Node(arr, "Manhattan");
                printAnswer("Manhattan", initial, 1);
                System.out.println("\n------------------------o---------------------------\n");
                initial = new Node(arr, "Linear Conflict");
                printAnswer("Linear Conflict", initial, 2);
            }
            else System.out.println("Unsolvable");
        } catch (NumberFormatException e) {
            System.out.println("Error: input should be a number");
        }
    }

    private static boolean isSolvable(int[][] blocks) {
        int size = blocks.length;
        int sum = 0;
        sum = inversion(blocks);
        if(size%2 != 0) {               //if grid size is odd, then only even inversion count is solvable
            if(sum%2==0)    return true;
            else    return false;
        }
        int row = -10;
        for(int i=0; i<size; i+=1) {
            for(int j=0; j<size; j+=1) {
                if(blocks[i][j]==-1) {
                    row = i;
                    break;
                }
            }
            if(row>=0) break;
        }
        if(row%2==0 && sum%2!=0)    return true;
        else if(row%2!=0 && sum%2==0)   return true;
        else    return false;
    }

    private static int inversion(int[][] blocks) {
        int size = blocks.length;
        int sum = 0;
        int[] temp = new int[size*size];
        for(int i=0; i<size; i+=1) {
            for(int j=0; j<size; j+=1) {
                temp[(size*i)+j] = blocks[i][j];
            }
        }
        for(int i=0; i<size*size; i+=1) {
            if(temp[i]==-1) continue;
            for(int j=i+1; j<size*size; j+=1) {
                if((temp[i]>temp[j]) && (temp[j]!=-1)) sum+=1;
            }
        }
        return sum;
    }

    public static int heuristic(String method, int[][] arr) {
        int h = 0;
        int size = arr.length;
        if(method.equalsIgnoreCase("hamming")) {
            for(int i=0; i<size; i+=1) {
                for(int j=0; j<size; j+=1) {
                    if(arr[i][j]!=-1 && arr[i][j]!=(size*i+1)+j) h+=1;
                }
            }
        }
        else if(method.equalsIgnoreCase("manhattan")) {
            h = manhattan(arr);
        }
        else if(method.equalsIgnoreCase("linear conflict")) {
            h = linearConflict(arr);         //linear conflict must be added.
        }
        return h;
    }

    private static int linearConflict(int[][] arr) {
        int h = manhattan(arr);
        int conflicts = 0;
        int size = arr.length;
        for(int i=0; i<size; i+=1) {
            for(int j=0; j<size; j+=1) {
                if(arr[i][j]>=(size*i+1) && arr[i][j]<(size*i+1)+size) {
                    for (int k=j+1; k<size; k+=1) {
                        if(arr[i][k]>=(size*i+1) && arr[i][k]<(size*i+1)+size && arr[i][j]>arr[i][k])
                            conflicts+=1;
                    }
                }
            }
        }
        h += 2*conflicts;
        return h;
    }

    private static int manhattan(int[][]arr) {
        int total = 0;
        for(int i=0; i<arr.length; i+=1) {
            for(int j=0; j< arr.length; j+=1) {
                if(arr[i][j]==-1) continue;
                int[] index = new int[2];
                index = getIndex(arr[i][j]);
                total += (Math.abs(i-index[0]) + Math.abs(j-index[1]));
            }
        }
        return total;
    }

    private static int[] getIndex(int x) {
        int[] index = new int[2];
        for(int i=0; i<goal.length; i+=1) {
            for(int j=0; j<goal.length; j+=1) {
                if(goal[i][j]==x) {
                    index[0] = i;
                    index[1] = j;
                    break;
                }
            }
        }
        return index;
    }

    private static void printAnswer(String method, Node initial, int flag) {
        Solution solution = new Solution(initial, goal);
        Node answer = solution.solve();
        if(flag==0)
            System.out.printf("Optimal cost: " + answer.getG() + "\n");
        Stack<Node> stack = new Stack<>();
        System.out.println(method + ":");
        System.out.println("Steps: ");
        while(answer!=null) {
            stack.push(answer);
            answer = answer.getParent();
        }
        while(!stack.isEmpty()) {
            Node current = stack.pop();
            current.printBoard();
            System.out.println();
        }
        System.out.println("Number of expanded nodes: " + solution.getClosedList().size());
        int explored = solution.getClosedList().size()+solution.getOpenList().size();
        System.out.println("Number of explored nodes: " + explored);
    }
}
