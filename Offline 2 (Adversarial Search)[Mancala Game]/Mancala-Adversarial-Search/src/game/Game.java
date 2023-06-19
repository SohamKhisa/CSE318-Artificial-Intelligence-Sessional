package game;

import main.JPair;

import java.util.Scanner;

public class Game {
    Scanner sc;

    public Game(Scanner sc) {
        this.sc = sc;
    }

    public int playCC(int firstturn, int depth, int heuristic1, int heuristic2) {           //playCC = Computer vs Computer //whose turn is first.
        Board board = new Board();
        Player player1, player2;

        if(firstturn==1) {
            player1 = new Player(1, true, depth, heuristic1);
            player2 = new Player(2, false, depth, heuristic2);
        }
        else {
            player1 = new Player(1, false, depth, heuristic1);
            player2 = new Player(2, true, depth, heuristic2);
        }
//        board.showBoard();
//        System.out.println();
        JPair<Integer, Integer> pair = board.getBinStones();

        while(true) {
            boolean p1=true, p2=true;
            if(firstturn==1) {
                while ((board.isExtraMove() || p1) && pair.getElement0() != 0) {
//                    System.out.println("Player 1:");
                    player1.setBoard(board);
                    board = player1.move("CC");         //Computer-Computer
//                    board.showBoard();
//                    System.out.println();
                    pair = board.getBinStones();
                    p1 = false;
                }
                if (pair.getElement0() == 0) {
                    int stone2 = pair.getElement1();
                    board.addStore2(stone2);
                    pair = board.getStoreStones();

                    board.setBin2Zero();
//                    board.showBoard();
//                    System.out.println();

                    if (pair.getElement0() > pair.getElement1()) {
//                        System.out.println("Result: Player1 won");
                        return 1;
                    } else if (pair.getElement0() < pair.getElement1()) {
//                        System.out.println("Result: Player2 won");
                        return 2;
                    } else {
//                        System.out.println("Result: match draw");
                        return 0;
                    }
//                break;
                }
                while ((board.isExtraMove() || p2) && pair.getElement1() != 0) {
//                System.out.println("Player 2:");
                    player2.setBoard(board);
                    board = player2.move("CC");         //Computer-Computer
//                board.showBoard();
//                System.out.println();
                    pair = board.getBinStones();
                    p2 = false;
                }
                if (pair.getElement1() == 0) {
                    int stone1 = pair.getElement0();
                    board.addStore1(stone1);
                    pair = board.getStoreStones();

                    board.setBin1Zero();
//                board.showBoard();
//                System.out.println();

                    if (pair.getElement0() > pair.getElement1()) {
//                    System.out.println("Result: Player1 won");
                        return 1;
                    } else if (pair.getElement0() < pair.getElement1()) {
//                    System.out.println("Result: Player2 won");
                        return 2;
                    } else {
//                    System.out.println("Result: match draw");
                        return 0;
                    }
//                break;
                }
            }
            else {
                while ((board.isExtraMove() || p2) && pair.getElement1() != 0) {
//                    System.out.println("Player 2:");
                    player2.setBoard(board);
                    board = player2.move("CC");         //Computer-Computer
//                    board.showBoard();
//                    System.out.println();
                    pair = board.getBinStones();
                    p2 = false;
                }
                if (pair.getElement1() == 0) {
                    int stone1 = pair.getElement0();
                    board.addStore1(stone1);
                    pair = board.getStoreStones();

                    board.setBin1Zero();
//                    board.showBoard();
//                    System.out.println();

                    if (pair.getElement0() > pair.getElement1()) {
//                        System.out.println("Result: Player1 won");
                        return 1;
                    } else if (pair.getElement0() < pair.getElement1()) {
//                        System.out.println("Result: Player2 won");
                        return 2;
                    } else {
//                        System.out.println("Result: match draw");
                        return 0;
                    }
//                break;
                }
                while ((board.isExtraMove() || p1) && pair.getElement0() != 0) {
//                    System.out.println("Player 1:");
                    player1.setBoard(board);
                    board = player1.move("CC");             //Computer-Computer
//                    board.showBoard();
//                    System.out.println();
                    pair = board.getBinStones();
                    p1 = false;
                }
                if (pair.getElement0() == 0) {
                    int stone2 = pair.getElement1();
                    board.addStore2(stone2);
                    pair = board.getStoreStones();

                    board.setBin2Zero();
//                    board.showBoard();
//                    System.out.println();

                    if (pair.getElement0() > pair.getElement1()) {
//                        System.out.println("Result: Player1 won");
                        return 1;
                    } else if (pair.getElement0() < pair.getElement1()) {
//                        System.out.println("Result: Player2 won");
                        return 2;
                    } else {
//                        System.out.println("Result: match draw");
                        return 0;
                    }
//                break;
                }
            }
        }
    }

    public void playHC() {                                  //playHC = human vs computer
        System.out.println("Human is player1\n");
        System.out.println("Select any bin from 1 to 6 (left to right inclusive)");

        System.out.print("Enter depth: ");
        int depth = Integer.parseInt(sc.nextLine());
        System.out.print("Enter heuristic (1 to 6 inclusive): ");
        int heuristic = Integer.parseInt(sc.nextLine());

        Player computer = new Player(2, true, depth, heuristic);

        Board board = new Board();
        JPair<Integer, Integer> pair = board.getBinStones();
        board.showBoard();
        System.out.println();

        while(true) {
            boolean p1 = true, p2 = true;
            while ((board.isExtraMove() || p1) && pair.getElement0() != 0) {
                int mv=0;
                while(true) {
                    System.out.println("Player 1:");
                    System.out.print("Enter your move: ");
                    String input = sc.nextLine();
                    System.out.println();
                    if(isNumeric(input))    mv = Integer.parseInt(input);
                    if(mv>=1 && mv<=6) {
                        if(board.getStoneNumber(1, mv)!=0)  break;
                        else System.out.println("Please do not choose an empty bin\n");
                    }
                    else {
                        System.out.println("Wrong input. Input must be from 1 to 6 (inclusive).\n");
                    }
                }

                board = board.move(1, mv);
                board.showBoard();

                System.out.println();
                pair = board.getBinStones();
                p1 = false;
            }
            if (pair.getElement0() == 0) {
                int stone2 = pair.getElement1();
                board.addStore2(stone2);
                pair = board.getStoreStones();

                board.setBin2Zero();
                board.showBoard();
                System.out.println();

                if (pair.getElement0() > pair.getElement1()) {
                    System.out.println("Result: Player1 won");
                } else if (pair.getElement0() < pair.getElement1()) {
                    System.out.println("Result: Player2 won");
                } else {
                    System.out.println("Result: match draw");
                }
                break;
            }
            while ((board.isExtraMove() || p2) && pair.getElement1() != 0) {
                System.out.println("Player 2:");
                computer.setBoard(board);
                board = computer.move("HC");            //Human-Computer
                board.showBoard();
                System.out.println();
                pair = board.getBinStones();
                p2 = false;
            }
            if (pair.getElement1() == 0) {
                int stone1 = pair.getElement0();
                board.addStore1(stone1);
                pair = board.getStoreStones();

                board.setBin1Zero();
                board.showBoard();
                System.out.println();

                if (pair.getElement0() > pair.getElement1()) {
                    System.out.println("Result: Player1 won");
                } else if (pair.getElement0() < pair.getElement1()) {
                    System.out.println("Result: Player2 won");
                } else {
                    System.out.println("Result: match draw");
                }
                break;
            }
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}
