import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class MapColoring {
    public static void main(String[] args) {
        Model model = new Model("Map Coloring: Australia");
        IntVar wa = model.intVar("wa", 1, 3);
        IntVar nt = model.intVar("nt", 1, 3);
        IntVar sa = model.intVar("sa", 1, 3);
        IntVar ql = model.intVar("ql", 1, 3);
        IntVar nsw = model.intVar("nsw", 1, 3);
        IntVar vc = model.intVar("vc", 1, 3);
        IntVar ts = model.intVar("ts", 1, 3);

        model.arithm(wa, "!=", nt).post();
        model.arithm(wa, "!=", sa).post();
        model.arithm(sa, "!=", nt).post();
        model.arithm(nt, "!=", ql).post();
        model.arithm(sa, "!=", ql).post();
        model.arithm(sa, "!=", nsw).post();
        model.arithm(ql, "!=", nsw).post();
        model.arithm(sa, "!=", vc).post();
        model.arithm(nsw, "!=", vc).post();

//        model.allDifferent(wa).post();
//        model.allDifferent(nt).post();
//        model.allDifferent(sa).post();
//        model.allDifferent(ql).post();
//        model.allDifferent(nsw).post();
//        model.allDifferent(vc).post();
//        model.allDifferent(ts).post();

        Solver solver = model.getSolver();
        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();

        System.out.println("Western Aus: " + wa);
        System.out.println("Northern territory: " + nt);
        System.out.println("Southern Aus: " + sa);
        System.out.println("Queen's Land: " + ql);
        System.out.println("New South Wales: " + nsw);
        System.out.println("Victoria: " + vc);
        System.out.println("Tasmania: " + ts);
    }
}
