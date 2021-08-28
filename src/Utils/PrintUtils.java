package Utils;

import Elements.Edge;
import Elements.Node;

/**
 * @create: 2021-08-29 01:06
 * @program: tarjan-isomorphic
 * @description: print results
 */
public class PrintUtils {

    public static void printGraph(Edge[][] edges) {
        for (int row = 0; row < edges.length; row++) {

            for (int col = 0; col < edges[0].length; col++) {
                System.out.printf("%d\t", edges[row][col].connected);
            }
            System.out.println();
        }

    }

    public static void printVerticesArray(Node[] nodes) {
        for (Node node:nodes) {
            System.out.print(node);
        }
        System.out.println();
    }
}
