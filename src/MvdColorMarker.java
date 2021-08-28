import Elements.Edge;
import Elements.MvdGraph;
import Elements.Node;
import Utils.ReadGraphUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @create: 2021-08-15 18:37
 * @program: tarjan-java
 * @description: graph used to mvd coloring
 */
public class MvdColorMarker {

    public static List<MvdGraph> GRAPHS = new ArrayList<>();

    public int colorCount = 0;


    static {
        GRAPHS.add(ReadGraphUtils.readFiles("graph_K4-e.txt"));

        GRAPHS.add(ReadGraphUtils.readFiles("graph_C4.txt"));

        GRAPHS.add(ReadGraphUtils.readFiles("graph_K2.txt"));

        GRAPHS.add(ReadGraphUtils.readFiles("graph_9Vertex-9.txt"));

        GRAPHS.add(ReadGraphUtils.readFiles("graph_9Vertex-11.txt"));
    }


    public void markBlocks(List<MvdGraph> blocks) {
        for (MvdGraph block : blocks) {
            findTypeAndColoring(block);
        }

        //将模板块中与割点同色的点颜色，变为当前割点的颜色
        for (MvdGraph block : blocks) {
            updateColorsOfCutVertex(block);
        }

    }

    private void updateColorsOfCutVertex(MvdGraph block) {
        if (block.template == null || block.vertices == null) {
            return;
        }
        for (int i = 0; i < block.vertices.length; i++) {
            Node node = block.vertices[i];
            if (!node.isCutVertex) {
                continue;
            }

            Node templateNode = block.template[i];
            for (int j = 0; j < block.template.length; j++) {
                if (block.template[j].color.equals(templateNode.color)) {
                    block.vertices[j].color = node.color;
                }
            }

        }
    }

    private void findTypeAndColoring(MvdGraph block) {
        for (MvdGraph type : GRAPHS) {
            findTypeAndColoring(block, type);
        }
    }

    private void findTypeAndColoring(MvdGraph graph, MvdGraph type) {
        Node[] template = IsomorphicJudger.getIsomorphicColors(graph, type);
        if (template == null) {
            return;
        }


        for (int i = 0; i < graph.vertices.length; i++) {
            //染色 后面的颜色会覆盖前面
            graph.vertices[i].color = template[i].color + colorCount;
        }
        //todo
        colorCount += graph.vertices.length;
        graph.template = template;


    }


    private static MvdGraph createGraph(String[] vertexs, int[][] edges) {


        Node[] vertices = new Node[vertexs.length];
        for (int i = 0; i < vertexs.length; i++) {

            vertices[i] = new Node(vertexs[i].split(":")[0], Integer.parseInt(vertexs[i].split(":")[1]));
        }

        Edge[][] graphEdges = new Edge[edges.length][edges[0].length];
        for (int row = 0; row < edges.length; row++) {
            for (int col = 0; col < edges[0].length; col++) {
                graphEdges[row][col] = new Edge(edges[row][col]);
            }
        }
        return new MvdGraph(vertices, graphEdges);
    }

    public static void main(String[] args) {

        GRAPHS.forEach(MvdGraph::print);
    }
}
