package game;

import main.JPair;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[] bins1, bins2;
    private int store1, store2;
    private int whichmove;       // tracks which move produced the current state of the board. Initially 0;
    private int minmaxval;
    private boolean extraMove;

    public Board() {
        bins1 = new int[6];             //player one bottom
        bins2 = new int[6];             //player two top
        Arrays.fill(bins1, 4);
        Arrays.fill(bins2, 4);
        store1 = 0;
        store2 = 0;
        whichmove = 0;
        extraMove = false;
    }

    public Board(int[] bins1, int[] bins2, int store1, int store2, int whichmove, boolean multiturn) {
        this.bins1 = bins1;
        this.bins2 = bins2;
        this.store1 = store1;
        this.store2 = store2;
        this.whichmove = whichmove;
        this.extraMove = multiturn;
    }

    public Board move(int player, int pos) {
        int indx = pos-1;
        int[] crate1 = new int[6];
        int[] crate2 = new int[6];
        int pool1 = store1;
        int pool2 = store2;
        boolean turnagain = false;
        for(int i=0; i<6; i+=1) {
            crate1[i] = bins1[i];
            crate2[i] = bins2[i];
        }

        if(player==1) {
            int stones = crate1[indx];
            crate1[indx] = 0;
            indx += 1;
            while(stones>0) {
                for (int i = indx; i < 6; i += 1) {
                    if (stones==1 && crate1[i]==0 && crate2[i]!=0) {
                        pool1 += stones + crate2[i];
                        crate2[i] = 0;
                        stones -= 1;
                    }
                    else if (stones > 0) {
                        crate1[i] += 1;
                        stones -= 1;
                    }
                    else break;
                }
                if(stones > 0) {
                    if(stones==1) turnagain = true;
                    pool1 += 1;
                    stones -= 1;
                }
                if(stones > 0) {
                    for (int i = 5; i >= 0; i -= 1) {
                        if (stones > 1 && i == 0) {
                            crate2[i] += 1;
                            stones -= 1;
                            indx=0;
                        }
                        else if (stones > 0) {
                            crate2[i] += 1;
                            stones -= 1;
                        }
                        else break;
                    }
                }
            }
        }
        else {
            int stones = crate2[indx];
            crate2[indx] = 0;
            indx -= 1;
            while(stones>0) {
                for (int i = indx; i >=0; i -= 1) {
                    if (stones==1 && crate2[i]==0 && crate1[i]!=0) {
                        pool2 += stones + crate1[i];
                        crate1[i] = 0;
                        stones -= 1;
                    }
                    else if (stones > 0) {
                        crate2[i] += 1;
                        stones -= 1;
                    }
                    else break;
                }
                if(stones > 0) {
                    if(stones==1) turnagain = true;
                    pool2 += 1;
                    stones -= 1;
                }
                if(stones > 0) {
                    for (int i = 0; i < 6; i += 1) {
                        if (stones > 1 && i == 5) {
                            crate1[i] += 1;
                            stones -= 1;
                            indx=5;
                        }
                        else if (stones > 0) {
                            crate1[i] += 1;
                            stones -= 1;
                        }
                        else break;
                    }
                }
            }
        }
        Board board = new Board(crate1, crate2, pool1, pool2, pos, turnagain);
        return board;
    }

    public void showBoard() {
        for(int i=0; i<5; i+=1) {
            if(i==0) {
                System.out.printf("+--+   ");
                for (int j = 0; j < 6; j += 1) {
                    if(ndigits(bins1[j])==1 || (ndigits(bins1[j])==2 && ndigits(bins2[j])==2))
                        System.out.printf(bins2[j] + "  ");
                    else
                        System.out.printf(" " + bins2[j] + "  ");
                }
                System.out.println(" +--+");
            }
            else if(i==4) {
                System.out.printf("+--+   ");
                for (int j = 0; j < 6; j += 1) {
                    if(ndigits(bins2[j])==1 || (ndigits(bins1[j])==2 && ndigits(bins2[j])==2))
                        System.out.printf(bins1[j] + "  ");
                    else
                        System.out.printf(" " + bins1[j] + "  ");
                }
                System.out.println(" +--+");
            }
            else if(i==2) {
                if(ndigits(store2)==1)  System.out.printf("| " + store2 + "|  ");
                else System.out.printf("|" + store2 + "|  ");
                for (int j=0; j<6; j+=1) {
                    if(ndigits(bins1[j])==2 || ndigits(bins2[j])==2)
                        System.out.printf("    ");
                    else
                        System.out.printf("   ");
                }
                if(ndigits(store1)==1)  System.out.println("  | " + store1 + "|");
                else System.out.println("  |" + store1 + "|");
            }
            else {
                System.out.printf("|  |  ");
                for(int j=0; j<6; j+=1) {
                    if(ndigits(bins1[j])==2 || ndigits(bins2[j])==2)
                        System.out.printf("    ");
                    else
                        System.out.printf("   ");
                }
                System.out.println("  |  |");
            }
        }
    }

    private int ndigits(int num) {
        int count = 0;
        if(num==0)  return 1;
        while (num != 0) {
            num /= 10;
            ++count;
        }
        return count;
    }


    public ArrayList<Board> getAllChildren(int playerid) {
        ArrayList<Board> children = new ArrayList<>();
        for (int i=1; i<=6; i+=1) {
            if((playerid==1 && bins1[i-1]!=0) || (playerid==2 && bins2[i-1]!=0)) {
                children.add(move(playerid, i));
            }
        }
        return children;
    }

    public JPair<Integer, Integer> getBinStones() {
        int stone1=0, stone2=0;
        for(int i=0; i<6; i+=1) {
            stone1 += bins1[i];
            stone2 += bins2[i];
        }
        JPair<Integer, Integer> pair = new JPair<>(stone1, stone2);
        return pair;
    }

    public JPair<Integer, Integer> getStoreStones() {
        JPair<Integer, Integer> pair = new JPair<>(store1, store2);
        return pair;
    }

    public boolean isExtraMove() {
        return extraMove;
    }

    public void addStore1(int stones) {
        this.store1 += stones;
    }

    public void addStore2(int stones) {
        this.store2 += stones;
    }

    public void setBin1Zero() {
        for(int i=0; i<6; i+=1)
            bins1[i] = 0;
    }

    public void setBin2Zero() {
        for(int i=0; i<6; i+=1)
            bins2[i] = 0;
    }

    public int possibleExtraMove(int playerid) {
        int count = 0;
        if(playerid==1) {
            int target = 6;
            for(int i=0; i<6; i+=1) {
                if(bins1[i]==target) count+=1;
                else {                              //bins exist on the right + 14
                    int mv = bins1[i]%13;
                    int binsright = (i+1)-6;
                    if(mv-binsright==1) count+=1;
                }
                target-=1;
            }
            return count;
        }
        else {
            for(int i=1; i<=6; i+=1) {
                if(bins2[i-1]==i)   count+=1;
                else {                              //bins exist on the left + 14
                    int mv = bins1[i-1]%13;
                    int binsleft = i-1;
                    if(mv-binsleft==1) count+=1;
                }
            }
            return count;
        }
    }

    public int getWhichmove() {
        return whichmove;
    }

    public int maxSteal(int playerid) {
        int maxsteal = 0;
        if(playerid==1) {
            for (int i=0; i<6; i+=1) {
                if (bins1[i]!=0) {
                    int binsright = 6-(i+1);
                    if(bins1[i] <= binsright) {             //num of stones in the ith bin is less than or equal to the bin nums in its right.
                        int target = i+bins1[i];
                        if(bins1[target]==0 && bins2[target]!=0)
                            maxsteal = Integer.max(maxsteal, bins2[target]+1);
                    }
                    else if (bins1[i] > binsright) {
                        int mod = bins1[i]%13;
                        int binsleft = i;
                        if((mod>=(13-binsleft) && bins1[i]<=13) || mod==0) {
                            int target = mod-(7+binsright+1);
                            if(mod==0 && bins2[i]!=0)
                                maxsteal = Integer.max(maxsteal, bins2[i]+1);
                            else if(target<0)
                                return maxsteal;
                            else if(bins2[target]!=0 && bins1[target]==0)
                                maxsteal = Integer.max(maxsteal, bins2[target]+1);
                        }
                    }
                }
            }
        }
        else {
            for (int i=0; i<6; i+=1) {
                if (bins2[i]!=0) {
                    int binsleft = i;
                    if(bins2[i] <= binsleft) {             //num of stones in the ith bin is less than or equal to the bin nums in its left.
                        if(bins2[i]==0 && bins1[i]!=0)
                            maxsteal = Integer.max(maxsteal, bins1[i]+1);
                    }
                    else if (bins2[i] > binsleft) {
                        int mod = bins2[i]%13;
                        int binsright = 6-(i+1);
                        if((mod>=(13-binsright) && bins2[i]<=13) || mod==0) {
                            int target = mod-(7+binsleft+1);
                            if(mod==0 && bins1[i]!=0)
                                maxsteal = Integer.max(maxsteal, bins1[i]+1);
                            else if(target<0)
                                return maxsteal;
                            else if(bins1[target]!=0 && bins2[target]==0)
                                maxsteal = Integer.max(maxsteal, bins1[target]+1);
                        }
                    }
                }
            }
        }
        return maxsteal;
    }

    public boolean isOver() {
        int count1=0, count2=0;
        for (int i=0; i<6; i+=1) {
            if(bins1[i]==0) count1+=1;
            if(bins2[i]==0) count2+=1;
        }
        if(count1==6 || count2==6) return true;
        else return false;
    }

    public int getStoneNumber(int playerid, int pos) {
        if(playerid==1) return bins1[pos-1];
        else    return bins2[pos-1];
    }
}
