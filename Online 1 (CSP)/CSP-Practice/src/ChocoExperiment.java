import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.SetVar;


public class ChocoExperiment {
    public static void main(String[] args) {
        Model model = new Model("Choco Experiment");
        IntVar v = model.intVar("v", new int[]{1,3,9});
        IntVar vs = model.intVar("vs", 4, 10);

        System.out.println("v is: "  + v);
        System.out.println("ith value of v is: " + v.nextValue(3));
        System.out.println("vs: " + vs);
        System.out.println();

        IntVar[] arr = model.intVarArray("arr", 5, -1, 1);
        for(int i=0; i<5; i+=1)
            System.out.println(arr[i]);
        System.out.println();

        IntVar bound = model.intVar("bound", 1, 12, true);
        System.out.println(bound);
        System.out.println();

        SetVar stv = model.setVar("stv", new int[]{1, 7, 2, 19, 13, 16});
        System.out.println(stv);
        System.out.println();

        BoolVar b = model.boolVar("b");
        System.out.println(b);
        System.out.println();

        SetVar y = model.setVar("y", new int[]{}, new int[]{1, 2, 3, 5, 12});
        System.out.println(y);
        System.out.println();

        SetVar z = model.setVar("z", new int[]{2,3}, new int[]{1,2,3,5,12});
        System.out.println(z);
        System.out.println();

        RealVar x = model.realVar("x", 0.2d, 3.4d, 0.001d);
        System.out.println(x);
        System.out.println();

    }
}
