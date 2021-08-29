import Elements.Edge;
import Elements.MvdGraph;
import Elements.Node;

import java.util.List;

/**
 * @create: 2021-08-01 22:23
 * @program: tarjan-java
 * @description: Running context of the entire program
 */
public class GraphContext {

    public static void main(String[] args) {
        String[] verx = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"};
        int[][] edges = new int[][]{

                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}
        };

        BlockAndCutVerticesBuilder blockAndCutVerticesBuilder = new BlockAndCutVerticesBuilder();
        Edge[][] matrixEdges = blockAndCutVerticesBuilder.create(verx, edges);
        System.out.println("### Input Graph ###");
        blockAndCutVerticesBuilder.printGraph(matrixEdges);

        //use tarjan algorithm to calculate cutVertices and blocks
        blockAndCutVerticesBuilder.makeDFSTarjan(0);
        //print cutVertices, blocks and Adjacency matrix
        blockAndCutVerticesBuilder.printResult();
        System.out.println();


        List<MvdGraph> graphs = blockAndCutVerticesBuilder.createMvdGraph();
        //mvd coloring
        MvdColorMarker mvdColorMarker = new MvdColorMarker();
        mvdColorMarker.markBlocks(graphs);

        Node[] vertices = blockAndCutVerticesBuilder.vertices;
        System.out.println("### Coloring Vertices Results ###");
        for (Node node : vertices) {
            System.out.print(node);
        }


    }


}
