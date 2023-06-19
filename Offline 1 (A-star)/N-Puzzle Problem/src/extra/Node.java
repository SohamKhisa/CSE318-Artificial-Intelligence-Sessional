package extra;

import main.Main;

import java.util.ArrayList;
import java.util.Objects;


public class Node {
    private int f;
    private int[][] tiles;
    private int g;
    private Node parent = null;
    private String method;

    public Node(int[][] tiles, String method) {
        int k = tiles.length;
        this.tiles = new int[k][k];
        for(int i=0; i<k; i+=1) {
            for(int j=0; j<k; j+=1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        if(parent==null) this.g=0;
        else this.g = parent.getG() + 1;
        this.method = method;
        this.f = this.g + Main.heuristic(method, tiles);
    }

    public Node(int[][] tiles, String method, Node parent) {
        int k = tiles.length;
        this.tiles = new int[k][k];
        for(int i=0; i<k; i+=1) {
            for(int j=0; j<k; j+=1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        this.parent = parent;
        this.g = parent.getG() + 1;
        this.method = method;
        this.f = this.g + Main.heuristic(method, tiles);
    }

    public void printBoard() {
        int size = tiles.length;
        for(int i=0; i<size; i+=1) {
            if(i==0) {
                for (int j=0; j<size; j+=1) {
                    if(j==size-1) System.out.printf("+--+");
                    else System.out.printf("+--");
                }
                System.out.println();
            }
            for(int j=0; j<size; j+=1) {
                if(j==size-1) {
                    if(tiles[i][j]==-1)
                        System.out.printf("|  |");
                    else if((int)(Math.log10(tiles[i][j])+1)==1)
                        System.out.printf("| " + tiles[i][j] + "|");
                    else
                        System.out.printf("|" + tiles[i][j] + "|");
                }
                else {
                    if(tiles[i][j]==-1)
                        System.out.printf("|  ");
                    else if((int)(Math.log10(tiles[i][j])+1)==1)
                        System.out.printf("| " + tiles[i][j]);
                    else
                        System.out.printf("|" + tiles[i][j]);
                }
            }
            System.out.println();
            for (int j=0; j<size; j+=1) {
                if(j==size-1) System.out.printf("+--+");
                else System.out.printf("+--");
            }
            System.out.println();
        }
    }


    public ArrayList<Node> expand() {
        ArrayList<Node> expanded = new ArrayList<>();
        int size = tiles.length;
        int[][] temp = new int[size][size];
        for(int i=0; i<size; i+=1) {
            for(int j=0; j<size; j+=1) {
                if(tiles[i][j]==-1) {
                    if(i-1>=0) {
                        for(int x=0; x<size; x+=1) {
                            for(int y=0; y<size; y+=1) {
                                temp[x][y] = tiles[x][y];
                            }
                        }
                        temp = swap(temp, i-1, j, i, j);
                        Node node = new Node(temp, method, this);
                        expanded.add(node);
                    }
                    if(j-1>=0) {
                        for(int x=0; x<size; x+=1) {
                            for(int y=0; y<size; y+=1) {
                                temp[x][y] = tiles[x][y];
                            }
                        }
                        temp = swap(temp, i, j-1, i, j);
                        Node node = new Node(temp, method, this);
                        expanded.add(node);
                    }
                    if(i+1<size) {
                        for(int x=0; x<size; x+=1) {
                            for(int y=0; y<size; y+=1) {
                                temp[x][y] = tiles[x][y];
                            }
                        }
                        temp = swap(temp, i+1, j, i, j);
                        Node node = new Node(temp, method, this);
                        expanded.add(node);
                    }
                    if(j+1<size) {
                        for(int x=0; x<size; x+=1) {
                            for(int y=0; y<size; y+=1) {
                                temp[x][y] = tiles[x][y];
                            }
                        }
                        temp = swap(temp, i, j+1, i, j);
                        Node node = new Node(temp, method, this);
                        expanded.add(node);
                    }
                }
            }
        }
        return expanded;
    }

    private int[][] swap(int[][] arr, int r1, int c1, int r2, int c2) {
        int[][] copy = arr;
        int tmp = copy[r1][c1];
        copy[r1][c1] = copy[r2][c2];
        copy[r2][c2] = tmp;
        return copy;
    }

    @Override
    public int hashCode() {
        int lsum=0, rsum=0, usum=0, dsum=0, msum=0, midvsum=0, midhsum=0;
        int size = tiles.length;
        int mid;
        if(size%2==0) mid = size/2+1;
        else    mid = (size-1)/2;
        for(int i=0; i<size; i+=1) {
            usum += tiles[0][i];                // first row
            dsum += tiles[size-1][i];           // last row
            lsum += tiles[i][0];                // first column
            rsum += tiles[i][size-1];           // last column
            msum += tiles[i][i];                //diagonal sum
            midvsum += tiles[i][mid];           // mid column sum
            midhsum += tiles[mid][i];           // mid row sum
        }
        return Objects.hash(lsum, rsum, usum, dsum, msum, midhsum, midvsum);
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        else if(o==null || getClass()!=o.getClass()) return false;
        int size = tiles.length;
        int[][] blocks = ((Node) o).getTiles();
        for(int i=0; i<size; i+=1) {
            for(int j=0; j<size; j+=1) {
                if(tiles[i][j]!=blocks[i][j]) return false;
            }
        }
        return true;
    }

    public void setF() {
        this.f = this.g + Main.heuristic(method, tiles);
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public void setValue(int i, int j, int val) {
        this.tiles[i][j] = val;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getF() {
        return f;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int getValue(int i, int j) {
        return tiles[i][j];
    }

    public Node getParent() {
        return parent;
    }

    public int getG() {
        return g;
    }
}
