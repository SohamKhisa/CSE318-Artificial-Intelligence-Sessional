import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.SetVar;

import java.util.Arrays;

public class MainCSP {
    public static void main(String[] args) {
        Model model = new Model("Killer Sudoku");
        IntVar[][] bd = model.intVarMatrix("bd", 9, 9, 1, 9);

        IntVar[] r0 = model.intVarArray("r0", 9, 1, 9);
        IntVar[] r1 = model.intVarArray("r1", 9, 1, 9);
        IntVar[] r2 = model.intVarArray("r2", 9, 1, 9);
        IntVar[] r3 = model.intVarArray("r3", 9, 1, 9);
        IntVar[] r4 = model.intVarArray("r4", 9, 1, 9);
        IntVar[] r5 = model.intVarArray("r5", 9, 1, 9);
        IntVar[] r6 = model.intVarArray("r6", 9, 1, 9);
        IntVar[] r7 = model.intVarArray("r7", 9, 1, 9);
        IntVar[] r8 = model.intVarArray("r8", 9, 1, 9);

        IntVar[] c0 = model.intVarArray("c0", 9, 1, 9);
        IntVar[] c1 = model.intVarArray("c1", 9, 1, 9);
        IntVar[] c2 = model.intVarArray("c2", 9, 1, 9);
        IntVar[] c3 = model.intVarArray("c3", 9, 1, 9);
        IntVar[] c4 = model.intVarArray("c4", 9, 1, 9);
        IntVar[] c5 = model.intVarArray("c5", 9, 1, 9);
        IntVar[] c6 = model.intVarArray("c6", 9, 1, 9);
        IntVar[] c7 = model.intVarArray("c7", 9, 1, 9);
        IntVar[] c8 = model.intVarArray("c8", 9, 1, 9);

        IntVar[] b0 = model.intVarArray("b0", 9, 1, 9);
        IntVar[] b1 = model.intVarArray("b1", 9, 1, 9);
        IntVar[] b2 = model.intVarArray("b2", 9, 1, 9);
        IntVar[] b3 = model.intVarArray("b3", 9, 1, 9);
        IntVar[] b4 = model.intVarArray("b4", 9, 1, 9);
        IntVar[] b5 = model.intVarArray("b5", 9, 1, 9);
        IntVar[] b6 = model.intVarArray("b6", 9, 1, 9);
        IntVar[] b7 = model.intVarArray("b7", 9, 1, 9);
        IntVar[] b8 = model.intVarArray("b8", 9, 1, 9);


        //To do
        IntVar[] s1 = model.intVarArray("s1", 2, 1, 9);
        model.sum(s1, "=", 3).post();
        model.arithm(bd[0][0], "=", s1[0]).post();
        model.arithm(bd[1][0], "=", s1[1]).post();
        model.allDifferent(s1).post();

        IntVar[] s2 = model.intVarArray("s2", 4, 1, 9);
        model.sum(s2, "=", 30);
        for(int i=0; i<2; i++)
            model.arithm(bd[i][1], "=", s2[i]).post();
        for(int i=2; i<4; i++)
            model.arithm(bd[i][2], "=", s2[i]).post();
        model.allDifferent(s2).post();

        IntVar[] s3 = model.intVarArray("s3", 2, 1, 9);
        model.sum(s3, "=", 9).post();
        model.arithm(bd[0][2], "=", s3[0]).post();
        model.arithm(bd[0][3], "=", s3[1]).post();
        model.allDifferent(s3).post();


        IntVar[] s4 = model.intVarArray("s4", 3, 1, 9);
        model.sum(s4, "=", 12).post();
        model.arithm(bd[0][4], "=", s4[0]).post();
        model.arithm(bd[1][4], "=", s4[1]).post();
        model.arithm(bd[1][5], "=", s4[2]).post();
        model.allDifferent(s4).post();


        IntVar[] s5 = model.intVarArray("s5", 4, 1, 9);
        model.sum(s5, "=", 23).post();
        model.arithm(bd[0][5], "=", s5[0]).post();
        model.arithm(bd[0][6], "=", s5[1]).post();
        model.arithm(bd[0][7], "=", s5[2]).post();
        model.arithm(bd[1][7], "=", s5[3]).post();
        model.allDifferent(s5).post();

        IntVar[] s6 = model.intVarArray("s6", 2, 1, 9);
        model.sum(s6, "=", 16).post();
        for(int i=0; i<2; i++)
            model.arithm(bd[i][8], "=", s6[i]).post();

        IntVar[] s7 = model.intVarArray("s7", 2, 1, 9);
        model.sum(s7, "=", 10).post();
        for(int i=2; i<4; i++)
            model.arithm(bd[i][0], "=", s7[i-2]).post();

        IntVar[] s8 = model.intVarArray("s8", 3, 1, 9);
        model.sum(s8, "=", 11).post();
        for(int i=2; i<5; i++)
            model.arithm(bd[i][1], "=", s8[i-2]).post();







        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[i][j], "=", b0[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[i][3+j], "=", b1[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[i][6+j], "=", b2[i * 3 + j]).post();


        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[3+i][j], "=", b3[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[3+i][3+j], "=", b4[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[3+i][6+j], "=", b5[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[6+i][j], "=", b6[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[6+i][3+j], "=", b7[i * 3 + j]).post();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                model.arithm (bd[6+i][6+j], "=", b8[i * 3 + j]).post();




        for (int j = 0; j < 9; j++)
            model.arithm (bd[0][j], "=", r0[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[1][j], "=", r1[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[2][j], "=", r2[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[3][j], "=", r3[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[4][j], "=", r4[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[5][j], "=", r5[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[6][j], "=", r6[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[7][j], "=", r7[j]).post();

        for (int j = 0; j < 9; j++)
            model.arithm (bd[8][j], "=", r8[j]).post();



        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][0], "=", c0[i]).post();

        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][1], "=", c1[i]).post();

        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][2], "=", c2[i]).post();

        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][3], "=", c3[i]).post();

        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][4], "=", c4[i]).post();

        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][5], "=", c5[i]).post();

        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][6], "=", c6[i]).post();
        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][7], "=", c7[i]).post();
        for (int i = 0; i < 9; i++)
            model.arithm (bd[i][8], "=", c8[i]).post();



        model.allDifferent(r0).post();
        model.allDifferent(r1).post();
        model.allDifferent(r2).post();
        model.allDifferent(r3).post();
        model.allDifferent(r4).post();
        model.allDifferent(r5).post();
        model.allDifferent(r6).post();
        model.allDifferent(r7).post();
        model.allDifferent(r8).post();

        model.allDifferent(c0).post();
        model.allDifferent(c1).post();
        model.allDifferent(c2).post();
        model.allDifferent(c3).post();
        model.allDifferent(c4).post();
        model.allDifferent(c5).post();
        model.allDifferent(c6).post();
        model.allDifferent(c7).post();
        model.allDifferent(c8).post();


        model.allDifferent(b0).post();
        model.allDifferent(b1).post();
        model.allDifferent(b2).post();
        model.allDifferent(b3).post();
        model.allDifferent(b4).post();
        model.allDifferent(b5).post();
        model.allDifferent(b6).post();
        model.allDifferent(b7).post();
        model.allDifferent(b8).post();


        Solver solver = model.getSolver();

        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();


// 5. Print the solution
        int i, j, k;
        for ( i = 0; i < 9; i++)
        {
            for ( j = 0; j < 9; j++)
            {

                System.out.print(" ");
                /* get the value for the board position [i][j] for the solved board */
                k = bd [i][j].getValue();
                System.out.print(k );
            }
            System.out.println();
        }


    }
}
