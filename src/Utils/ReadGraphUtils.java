package Utils;

import Elements.Edge;
import Elements.MvdGraph;
import Elements.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @create: 2021-08-28 15:44
 * @program: tarjan-java
 * @description: read graphs from files
 */
public class ReadGraphUtils {

    public static MvdGraph readFiles(String fileName) {
        try {
            FileReader in = new FileReader("resources/"+fileName);
            BufferedReader reader = new BufferedReader(in);
            String line, verticesLines;
            ArrayList<ArrayList<Integer>> edgesList = new ArrayList<ArrayList<Integer>>();

            verticesLines = reader.readLine();
            Node[] vertices = createVertices(verticesLines.split(","));


            while ((line = reader.readLine()) != null) {
                String[] str = line.split(",");
                ArrayList lineEdge = new ArrayList<>();
                for (String edge : str) {
                    lineEdge.add(Integer.parseInt(edge.trim()));
                }
                edgesList.add(lineEdge);
            }

            Edge[][] edges = createEdges(edgesList);

            if (edges.length != vertices.length) {
                System.out.println("The number of vertices and edges should be equivalent");
                return null;
            }

            return new MvdGraph(vertices, edges);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static Node[] createVertices(String[] strVertices) {

        Node[] vertices = new Node[strVertices.length];
        for (int i = 0; i < strVertices.length; i++) {

            vertices[i] = new Node(strVertices[i].split(":")[0].trim(), Integer.parseInt(strVertices[i].split(":")[1].trim()));
        }
        return vertices;
    }

    private static Edge[][] createEdges(ArrayList<ArrayList<Integer>> edgesList) {
        Edge[][] result = new Edge[edgesList.size()][edgesList.size()];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[0].length; col++) {

                result[row][col] = new Edge(edgesList.get(row).get(col));
            }
        }
        return result;
    }


}
