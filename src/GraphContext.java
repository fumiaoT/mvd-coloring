import java.util.List;

/**
 * @author: yuanpeng
 * @create: 2021-08-01 22:23
 * @program: tarjan-java
 * @description: 计算割点和块的运行类
 */
public class GraphContext {

    public static void main(String[] args) {
        String[] verx = new String[]{"A", "B", "C", "D", "E", "F", "G","H","I"};
        int[][] edges = new int[][]{
                {0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 1, 0, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 1, 0, 0}
        };

        GraphMatrixBuilder graphMatrixBuilder = new GraphMatrixBuilder();
        Edge[][] matrixEdges = graphMatrixBuilder.create(verx, edges);
        System.out.println("初始化后的图");
        graphMatrixBuilder.printGraph(matrixEdges);

        //定义初始化计算的节点
        graphMatrixBuilder.makeDFSTarjan(0);
        //打印结果
        graphMatrixBuilder.printResult();
        graphMatrixBuilder.blocksEdges.forEach(graphMatrixBuilder::printGraph);

        List<MvdGraph> graphs = graphMatrixBuilder.createMvdGraph();
        //mvd 染色
        MvdColorMarker mvdColorMarker = new MvdColorMarker();
        mvdColorMarker.markBlocks(graphs);

        Node[] vertices = graphMatrixBuilder.vertices;
        for (Node node : vertices) {
            System.out.print(node);
        }


    }


}
