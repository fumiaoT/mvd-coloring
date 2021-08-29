package Elements;

/**
 * @create: 2021-08-02 01:41
 * @program: tarjan-java
 * @description: 表示边
 */
public class Edge {

    public int connected;

    public boolean reached;

    public Edge(int connected) {
        this.connected = connected;
        this.reached = false;
    }

}
