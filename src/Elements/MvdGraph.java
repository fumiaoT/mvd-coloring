package Elements;

/**
 * @create: 2021-08-15 19:20
 * @program: tarjan-java
 * @description: 用于mvd染色的图
 */
public class MvdGraph {

    public Edge[][] edges;

    public Node[] vertices;

    //模板块记录的染色点数组
    public Node[] template;


    public MvdGraph(Node[] vertices, Edge[][] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public void print() {
        for (Node node : vertices) {
            System.out.print(node);
        }
        System.out.println();
        printGraph(edges);
    }

    private void printGraph(Edge[][] edges) {
        for (int row = 0; row < edges.length; row++) {

            for (int col = 0; col < edges[0].length; col++) {
                System.out.printf("%d\t", edges[row][col].connected);
            }
            System.out.println();
        }
        System.out.println("----------------");
    }
}
