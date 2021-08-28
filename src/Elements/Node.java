package Elements;

import java.util.Objects;

/**
 * @create: 2021-08-01 22:18
 * @program: tarjan-java
 * @description: nodes that compose the graph
 */
public class Node {

    public String name;

    public Node parent;

    public int depth;

    public int low;

    public Integer color;

    public Boolean isCutVertex = false;


    public Node(String name) {
        this.name = name;
        this.depth = 0;
        this.low = this.depth;
    }

    public Node(String name, Integer color) {
        this.name = name;
        this.color = color;
        this.depth = 0;
        this.low = this.depth;
    }

    @Override
    public String toString() {
        if (color == null) {
            return "{" + "'" + name + '\'' + '}' ;
        }
        return "{" + "'" + name + '\'' + ":" + color + '}' + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return depth == node.depth && low == node.low && color == node.color && Objects.equals(name, node.name) && Objects.equals(parent, node.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

