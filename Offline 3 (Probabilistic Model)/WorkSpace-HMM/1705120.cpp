#include <iostream>
#include <utility>

using namespace std;

struct Cell {
    double val;             // Probability of Casper being in this cell
    int adjEdge;            // Number of adjacent edges excluding obstacles
    int adjCorner;          // Number of adjacent corner excluding obstacles
};

template <typename T>
void destroy(T **state, int n) {
    for (int i=0; i<n; i++) {
        delete[] state[i];
    }
    delete[] state;
}

void show(struct Cell **room, int n, int m) {
    for (int i=0; i<n; i++) {
        cout << "+";
        for (int x=0; x<m; x++) {
            cout << "----------+";
        }
        cout << endl;
        for (int j=0; j<m; j++) {
            cout << "| ";
            printf("%.5f ", room[i][j].val*100);
        }
        cout << " |" << endl;

        if (i==n-1) {
            cout << "+";
            for (int x=0; x<m; x++) {
                cout << "----------+";
            }
            cout << endl;
        }
    }
}

void initialize(struct Cell **room, int n, int m, int k) {
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            room[i][j].val = 1.0/(n*m-k);

            // Initially let's assume that there is no obstacles
            // Each cell's adjacent has a fixed number of edge and corner transitions
            if ((i==0 && j==0) || (i==0 && j==m-1) || (i==n-1 && j==m-1) || (i==n-1 && j==0)) {
                // All corners have 2 edges and 2 corners (assuming non of its corner cell has an obstacle)
                room[i][j].adjEdge = 2; 
                room[i][j].adjCorner = 2;
            }
            else if (i==0 || i==n-1 || j==0 || j==m-1) {
                // All non corner boundary cells have 3 corners and 3 edges (No obstacle)
                room[i][j].adjCorner = 3;
                room[i][j].adjEdge = 3;
            }
            else {
                // Rest of the cells have 4 edges and 5 corners (No obstacle)
                room[i][j].adjEdge = 4;
                room[i][j].adjCorner = 5;
            }
        }
    }
}

bool isInBoundary(int u, int v, int n, int m) {
    if(u>=0 && u<n && v>=0 && v<m)
        return true;
    return false;
}

void evalObstacles(struct Cell **room, int n, int m, int u, int v) {
    room[u][v].val = 0;
    room[u][v].adjEdge = 0;
    room[u][v].adjCorner = 0;
    if (isInBoundary(u-1, v-1, n, m)) {
        // reducing the corner transition of cell(u-1, v-1). Since cell(u, v) is an obstacle.
        // Top-left corner
        if (room[u-1][v-1].val!=0)
            room[u-1][v-1].adjCorner--;
    }
    if (isInBoundary(u-1, v, n, m)) {
        // Top-middle
        if (room[u-1][v].val!=0)
            room[u-1][v].adjEdge--;
    }
    if (isInBoundary(u-1, v+1, n, m)) {
        // Top-right
        if (room[u-1][v+1].val!=0)
            room[u-1][v+1].adjCorner--;
    }
    if (isInBoundary(u, v-1, n, m)) {
        // Left
        if (room[u][v-1].val!=0)
            room[u][v-1].adjEdge--;
    }
    if (isInBoundary(u, v+1, n, m)) {
        // Right
        if (room[u][v+1].val!=0)
            room[u][v+1].adjEdge--;
    }
    if (isInBoundary(u+1, v-1, n, m)) {
        // Bottom-left
        if (room[u+1][v-1].val!=0)
            room[u+1][v-1].adjCorner--;
    }
    if (isInBoundary(u+1, v, n, m)) {
        // Bottom-middle
        if (room[u+1][v].val!=0)
            room[u+1][v].adjEdge--;
    }
    if (isInBoundary(u+1, v+1, n, m)) {
        // Bottom-right
        if (room[u+1][v+1].val!=0)
            room[u+1][v+1].adjCorner--;
    }
}

void updateRoom(struct Cell **room, int n, int m, int u, int v, int sensor) {
    double edgeTrans = 0.9;                  // Cumulitive probability for edge transition
    double cornerTrans = 1.0-edgeTrans;        // Cumulitive probability for corner transition
    double sensorTrue = 0.85;
    double sensorFalse = 1.0-sensorTrue;

    double **tempState;
    tempState = new double*[n];
    for (int i=0; i<n; i++) {
        tempState[i] = new double[m];
    }

    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            tempState[i][j] = room[i][j].val;
        }
    }


    // While updating the transitions are happening from
    // the adjacent cells (non-obstacles) to the cell(i,j)

    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            if (room[i][j].val == 0)    continue;
            double probability = 0.0;

            if (isInBoundary(i-1, j-1, n, m) && room[i-1][j-1].val!=0.0) {
                // Top-left corner
                probability += tempState[i-1][j-1] * (cornerTrans/(double)room[i-1][j-1].adjCorner);
            }
            if (isInBoundary(i-1, j, n, m) && room[i-1][j].val!=0.0) {
                // Top-middle
                probability += tempState[i-1][j] * (edgeTrans/(double)room[i-1][j].adjEdge);
            }
            if (isInBoundary(i-1, j+1, n, m) && room[i-1][j+1].val!=0.0) {
                // Top-right corner
                probability += tempState[i-1][j+1] * (cornerTrans/(double)room[i-1][j+1].adjCorner);
            }
            if (isInBoundary(i, j-1, n, m) && room[i][j-1].val!=0.0) {
                // Left
                probability += tempState[i][j-1] * (edgeTrans/(double)room[i][j-1].adjEdge);
            }
            if (isInBoundary(i, j, n, m)) {
                // The cell itself
                probability += tempState[i][j] * (cornerTrans/(double)room[i][j].adjCorner);
            }
            if (isInBoundary(i, j+1, n, m) && room[i][j+1].val!=0.0) {
                // Right
                probability += tempState[i][j+1] * (edgeTrans/(double)room[i][j+1].adjEdge);
            }
            if (isInBoundary(i+1, j-1, n, m) && room[i+1][j-1].val!=0.0) {
                // Bottom-left corner
                probability += tempState[i+1][j-1] * (cornerTrans/(double)room[i+1][j-1].adjCorner);
            }
            if (isInBoundary(i+1, j, n, m) && room[i+1][j].val!=0.0) {
                // Bottom-middle
                probability += tempState[i+1][j] * (edgeTrans/(double)room[i+1][j].adjEdge);
            }
            if (isInBoundary(i+1, j+1, n, m) && room[i+1][j+1].val!=0.0) {
                // Bottom-right corner
                probability += tempState[i+1][j+1] * (cornerTrans/(double)room[i+1][j+1].adjCorner);
            }

            // Sensor reading
            if(sensor) {
                // if cell(i,j) is equal to cell(u,v) or adjacent to cell(u,v)
                if ((i==u-1) && (j==v-1 || j==v || j==v+1)) {
                    probability *= sensorTrue;
                }
                else if ((i==u) && (j==v-1 || j==v || j==v+1)) {
                    probability *= sensorTrue;
                }
                else if ((i==u+1) && (j==v-1 || j==v || j==v+1)) {
                    probability *= sensorTrue;
                }
                else
                    probability *= sensorFalse;
            }
            else {
                if ((i==u-1) && (j==v-1 || j==v || j==v+1)) {
                    probability *= sensorFalse;
                }
                else if ((i==u) && (j==v-1 || j==v || j==v+1)) {
                    probability *= sensorFalse;
                }
                else if ((i==u+1) && (j==v-1 || j==v || j==v+1)) {
                    probability *= sensorFalse;
                }
                else
                    probability *= sensorTrue;
            }
            room[i][j].val = probability;
        }
    }
    destroy<double>(tempState, n);
}

pair<int, int> possibleLocation(struct Cell **room, int n, int m) {
    pair<int, int> location;
    double maxProb = 0.0;

    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++) {
            if (maxProb < room[i][j].val) {
                maxProb = room[i][j].val;
                location.first = i;
                location.second = j;
            }
        }
    }
    return location;
}

void normalize(struct Cell **room, int n, int m) {
    double sum = 0.0;
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++ ) {
            sum += room[i][j].val;
        }
    }
    for (int i=0; i<n; i++) {
        for (int j=0; j<m; j++ ) {
            room[i][j].val /= sum;
        }
    }
}


void printSeparator(int m) {
    cout << endl;
    for (int i=0; i<m; i++) {
        cout << "==========";
    }
    cout << endl << endl;
}

int main()
{
    int n, m, k;
    struct Cell **room;
    cout << "Enter n, m, k and the obstacle locations (for k>0):" << endl;
    cin >> n >> m >> k;                 // 3<=n,m<=20; 0<=k<=n*m
    // allocate memory
    room = new Cell*[n];
    for (int i=0; i<n; i++) {
        room[i] = new Cell[m];
    }

    //initialize
    initialize(room, n, m, k);
    for (int i=0; i<k; i++) {
        int u, v;
        cin >> u >> v;
        evalObstacles(room, n, m, u, v);
    }
    show(room, n, m);
    printSeparator(m);
    
    while(true)
    {
        char command;
        int u, v, b;
        cout << "Enter your command: " << endl;
        cin >> command;
        if (command == 'R') {
            cin >> u >> v >> b;         //u>=0, u<n; v>=0, v<m; b=1/b=0
            updateRoom(room, n, m, u, v, b);
            normalize(room, n, m);
            show(room, n, m);
            printSeparator(m);
        }
        else if (command == 'C') {
            pair<int, int> location = possibleLocation(room, n, m);
            cout << "Casper is most probably at (" << location.first << ", " << location.second << ")" << endl;
            show(room, n, m);
            printSeparator(m);
        }
        else if (command == 'Q') {
            cout << "Bye Casper" << endl;
            break;
        }
        else {
            cout << "Invalid command" << endl;
            printSeparator(m);
        }
    }
    destroy<Cell>(room, n);
    return 0;
}
