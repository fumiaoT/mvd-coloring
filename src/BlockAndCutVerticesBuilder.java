import Elements.Edge;
import Elements.MvdGraph;
import Elements.Node;
import Utils.PrintUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @create: 2021-08-01 22:17
 * @program: tarjan-java
 * @description: 整体图像表示
 */

public class BlockAndCutVerticesBuilder {


    public Node[] vertices;

    public Edge[][] edges;

    public Set<Node> cutVerticesSet = new HashSet<>();

    public List<List<Node>> block = new ArrayList<>();

    public List<Edge[][]> blocksEdges = new ArrayList<>();

    public Stack<Node> nodesStack = new Stack<>();

    int root;

    int count = 1;

    public Edge[][] create(String[] vertexs, int[][] edges) {
        this.vertices = new Node[vertexs.length];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertices[i] = new Node(vertexs[i]);
        }

        this.edges = new Edge[edges.length][edges[0].length];
        for (int row = 0; row < edges.length; row++) {
            for (int col = 0; col < edges[0].length; col++) {
                this.edges[row][col] = new Edge(edges[row][col]);
            }
        }
        return this.edges;
    }

    public List<MvdGraph> createMvdGraph() {
        List<MvdGraph> graphs = new ArrayList<>();
        for (int i = 0; i < block.size(); i++) {
            graphs.add(new MvdGraph(block.get(i).toArray(new Node[0]), blocksEdges.get(i)));
        }
        return graphs;
    }

    public void printGraph(Edge[][] edges) {
        for (int row = 0; row < edges.length; row++) {

            for (int col = 0; col < edges[0].length; col++) {
                System.out.printf("%d\t", edges[row][col].connected);
            }
            System.out.println();
        }

    }

    public void printResult() {
        System.out.println("### CutVertices and Blocks  ###");
        System.out.println("cutVertices of Input Graph:");
        System.out.println("cutVerticesSet:" + cutVerticesSet);
        System.out.println();
        System.out.println("Block generated from Graph:");
        for (int i = 0; i < block.size(); i++) {
            System.out.println("Block num " + (i + 1));
            System.out.println(block.get(i));
            PrintUtils.printGraph(blocksEdges.get(i));
        }

    }

    public void makeDFSTarjan(int activeNodeIndex) {

        vertices[activeNodeIndex].depth = 1;
        nodesStack.push(vertices[activeNodeIndex]);
        root = activeNodeIndex;
        //计算割点与块
        DFSTarjan(activeNodeIndex);
        //计算块的邻接矩阵
        cutBlocksEdges();


    }

    public void cutBlocksEdges() {

        for (List<Node> lists : block) {
            List<Integer> indices = new ArrayList<Integer>();
            for (Node node : lists) {
                indices.add(getIndexOfNode(node));
            }

            Edge[][] blockEdge = new Edge[indices.size()][indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                for (int j = 0; j < indices.size(); j++) {
                    int rowIndex = indices.get(i);
                    int colIndex = indices.get(j);
                    blockEdge[i][j] = edges[rowIndex][colIndex];
                }
            }
            blocksEdges.add(blockEdge);
        }
    }


    private void DFSTarjan(int activeNodeIndex) {

        Node currentNode = vertices[activeNodeIndex];


        while (getNodesOfUnreachedEdge(activeNodeIndex) != null || currentNode.parent != null) {

            if (getNodesOfUnreachedEdge(activeNodeIndex) != null) {
                Node nextNode = getNodesOfUnreachedEdge(activeNodeIndex);
                markEdgeReached(currentNode, nextNode);


                if (nextNode.depth == 0) {
                    nodesStack.push(nextNode);
                    nextNode.parent = currentNode;
                    count = count + 1;
                    nextNode.depth = count;
                    nextNode.low = count;
                    DFSTarjan(getIndexOfNode(nextNode));
                } else {
                    currentNode.low = Math.min(currentNode.low, nextNode.depth);
                }

            } else {
                // parent of currentNode is not null
                if (currentNode.low >= currentNode.parent.depth) {
                    if (getIndexOfNode(currentNode.parent) != root
                            || getNodesOfUnreachedEdge(root) != null) {
                        cutVerticesSet.add(currentNode.parent);
                        currentNode.parent.isCutVertex = true;
                    }
                    List<Node> list = new ArrayList<>();
                    while (!currentNode.equals(nodesStack.peek())) {
                        list.add(nodesStack.pop());
                    }
                    list.add(nodesStack.pop());
                    list.add(currentNode.parent);
                    block.add(list);
                } else {
                    currentNode.parent.low = Math.min(currentNode.parent.low, currentNode.low);
                }
                return;
            }
        }


    }

    private void markEdgeReached(Node currentNode, Node nextNode) {
        int from = getIndexOfNode(currentNode);
        int to = getIndexOfNode(nextNode);
        edges[from][to].reached = true;
        edges[to][from].reached = true;

    }

    private Node getNodesOfUnreachedEdge(int activeVex) {
        for (int col = 0; col < edges[activeVex].length; col++) {
            Edge edge = edges[activeVex][col];
            if (edge.connected != 1) {
                continue;
            }

            Node cur = vertices[col];
            if (!edge.reached) {
                return cur;
            }
        }
        return null;
    }

    private int getIndexOfNode(Node node) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].name.equals(node.name)) {
                return i;
            }
        }
        return -1;
    }
}
