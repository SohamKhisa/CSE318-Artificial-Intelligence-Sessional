package main;

import game.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainMancala {
    public static void main(String[] args) throws IOException {
        int flag = 1;
        while(flag==1) {
            System.out.println("1. Computer vs Computer (win-loss of 100 games)");
            System.out.println("2. Human vs Computer");
            System.out.println("3. Heuristic vs Heuristic (With varying depth)");
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                System.out.println();
                flag = 0;
                Game game = new Game(sc);
                int winp1=0, winp2=0;
                System.out.print("Enter depth: ");
                int depth = Integer.parseInt(sc.nextLine());
                System.out.print("Enter heuristic (1 to 6 inclusive): ");
                int heuristic = Integer.parseInt(sc.nextLine());
                for (int i=1; i<=100; i+=1) {
                    int res;
                    res = game.playCC(1, depth, heuristic, heuristic);
                    if(res==1) winp1+=1;
                    else if(res==2) winp2+=1;
                }
                System.out.println("Player1 (first turn): Win-loss: " + winp1 + "/" + winp2);
                winp1=0;
                winp2=0;
                for (int i=1; i<=100; i+=1) {
                    int res;
                    res = game.playCC(2, depth, heuristic, heuristic);
                    if(res==1) winp1+=1;
                    else if(res==2) winp2+=1;
                }
                System.out.println("Player2 (first turn): Win-loss: " + winp2 + "/" + winp1);
            } else if (choice.equals("2")) {
                System.out.println();
                flag = 0;
                Game game = new Game(sc);
                game.playHC();
            }
            else if (choice.equals("3")) {
                String common = "./src/experiment/1705120_depth";
                String path;
                for(int i=1; i<7; i+=1) {           //DEPTH
                    Game game = new Game(sc);
                    path = common + i + ".csv";
                    PrintWriter pw = new PrintWriter(path);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Player1/Player2, H1, H2, H3, H4, H5, H6");
                    sb.append('\n');
                    for (int j=1; j<=6; j+=1) {          //HEURISTIC Player 1 (row)
                        sb.append("H" + j + ", ");
                        for(int k=1; k<=6; k+=1) {       // HEURISTIC Player 2 (column)
                            int winp1=0;
                            int winp2=0;
                            for (int l=1; l<=100; l+=1) {       // Result of 100 games
                                int res;
                                res = game.playCC(1, i, j, k);
                                if(res==1) winp1+=1;
                                else if(res==2) winp2+=1;
                            }
                            sb.append(winp1 + "/" + winp2 + ", ");
                        }
                        sb.append('\n');
                    }
                    pw.write(sb.toString());
                    pw.close();
                }
                flag=0;
            }else {
                System.out.println("\nChoice \"" + choice + "\" does not exist");
            }
        }
    }
}
