package extra;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getF() > n2.getF())
            return 1;
        else if (n1.getF() < n2.getF())
            return -1;
        return 0;
    }
}
