import Elements.MvdGraph;
import Elements.Node;

/**
 * @create: 2021-08-16 23:20
 * @program: tarjan-java
 * @description:
 * utilize elementary operations to judge the isomorphic graphs .
 * Return the colored vertices corresponding each blocks
 */
public class IsomorphicJudger {


    public static Node[] getIsomorphicColors(MvdGraph block, MvdGraph type) {
        if (block == null || type == null) {
            return null;
        }

        if (block.vertices == null || type.vertices == null) {
            return null;
        }

        if (block.vertices.length != type.vertices.length) {
            return null;
        }

        int n = block.vertices.length;

        //block adjacency matrix
        int[][] blockArray = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockArray[i1] = new int[n];
        }

        // block 01 adjacency matrix
        int[][] blockTongxin = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockTongxin[i1] = new int[n];
        }

        //block XOR matrix
        int[][] blockYihuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockYihuo[i1] = new int[n];
        }

        //block XNOR matrix
        int[][] blockTonghuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockTonghuo[i1] = new int[n];
        }

        //type adjacency matrix
        int[][] typeArray = new int[n][n];
        for (int i2 = 0; i2 < n; i2++) {
            typeArray[i2] = new int[n];
        }

        // type 01 adjacency matrix
        int[][] typeTongxin = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            typeTongxin[i1] = new int[n];
        }

        //block XOR matrix

        int[][] typeYihuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            typeYihuo[i1] = new int[n];
        }

        //block XNOR matrix

        int[][] typeTonghuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            typeTonghuo[i1] = new int[n];
        }

        for (int row = 0; row < block.edges.length; row++) {
            for (int col = 0; col < block.edges[0].length; col++) {
                blockArray[row][col] = block.edges[row][col].connected;
            }
        }

        for (int row = 0; row < type.edges.length; row++) {
            for (int col = 0; col < type.edges[0].length; col++) {
                typeArray[row][col] = type.edges[row][col].connected;
            }
        }

        oneZero(blockArray, blockTongxin, n);

        xor(blockArray, blockTongxin, blockYihuo, n);

        xnor(blockArray, blockTongxin, blockTonghuo, n);


        oneZero(typeArray, typeTongxin, n);

        xor(typeArray, typeTongxin, typeYihuo, n);

        xnor(typeArray, typeTongxin, typeTonghuo, n);

        return getTransVertices(blockArray, blockYihuo, blockTonghuo, typeArray, typeYihuo, typeTonghuo, n, type.vertices, 0, 0);

    }


    public static Node[] getTransVertices(int[][] blockArray, int[][] blockYihuo, int[][] blockTonghuo,
                                          int[][] typeArray, int[][] typeYihuo, int[][] typeTonghuo, int n,
                                          Node[] typeVertices, int matchedRowNum, int blockRowBegin) {

        int[][] typeArrayCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                typeArrayCopy[i][j] = typeArray[i][j];
            }
        }

        int[][] typeYihuoCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                typeYihuoCopy[i][j] = typeYihuo[i][j];
            }
        }

        int[][] typeTonghuoCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                typeTonghuoCopy[i][j] = typeTonghuo[i][j];
            }
        }

        //colored vertices template
        Node[] vertices = new Node[n];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Node(typeVertices[i].name, typeVertices[i].color);
        }
        //template array to store data
        int[] tempBlockYihuo = new int[n];
        //template array to store data
        int[] tempTypeYihuo = new int[n];
        //template array to store data
        int[] tempBlockArray = new int[n];
        //template array to store data
        int[] tempBlockTonghuo = new int[n];
        //template array to store data
        int[] tempTypeArray = new int[n];
        //template array to store data
        int[] tempTypeTonghuo = new int[n];

        int t;
        int colNum;


        for (int blockRow = blockRowBegin; blockRow < n; blockRow++) {


            for (int j = 0; j < n; j++) {
                tempBlockYihuo[j] = blockYihuo[blockRow][j];
            }


            for (int j = 0; j < n; j++) {
                tempBlockArray[j] = blockArray[blockRow][j];
            }

            for (int j = 0; j < n; j++) {
                tempBlockTonghuo[j] = blockTonghuo[blockRow][j];
            }

            //bubble sort preprocessing
            bubbleSort(tempBlockYihuo, n);
            bubbleSort(tempBlockArray, n);
            bubbleSort(tempBlockTonghuo, n);

            for (int typeRow = blockRow; typeRow < n; typeRow++) {
                colNum = 0;

                for (int j = 0; j < n; j++) {
                    tempTypeYihuo[j] = typeYihuo[typeRow][j];
                }

                for (int j = 0; j < n; j++) {
                    tempTypeArray[j] = typeArray[typeRow][j];
                }

                for (int j = 0; j < n; j++) {
                    tempTypeTonghuo[j] = typeTonghuo[typeRow][j];
                }

                bubbleSort(tempTypeYihuo, n);
                bubbleSort(tempTypeArray, n);
                bubbleSort(tempTypeTonghuo, n);

                //begin to compare

                for (int col = 0; col < n; col++) {

                    //compare the XOR matrix
                    if (tempBlockYihuo[col] != tempTypeYihuo[col]) {
                        if (typeRow == n - 1) {
                            return null;
                        }
                        break;
                    }
                    colNum = col;

                    if (col != n - 1) {
                        continue;
                    }

                    //compare the adjacency matrix
                    for (int b = 0; b < n; b++) {
                        if (tempBlockArray[b] == tempTypeArray[b]) {
                            continue;
                        } else if (b < n - 1) {
                            return null;
                        }
                    }

                    //compare the XNOR matrix
                    for (int c = 0; c < n; c++) {
                        if (tempBlockTonghuo[c] == tempTypeTonghuo[c]) {
                            continue;
                        } else if (c < n - 1) {
                            return null;
                        }
                    }

                    matchedRowNum++;//two rows match
                    Node tempNode = vertices[blockRow];
                    vertices[blockRow] = vertices[typeRow];
                    vertices[typeRow] = tempNode;
                    //elementary operations: switch adjacency cols
                    for (int c = 0; c < n; c++) {

                        t = typeArrayCopy[blockRow][c];
                        typeArrayCopy[blockRow][c] = typeArrayCopy[typeRow][c];
                        typeArrayCopy[typeRow][c] = t;

                    }

                    //elementary operations: switch adjacency rows
                    for (int r = 0; r < n; r++) {

                        t = typeArrayCopy[r][blockRow];
                        typeArrayCopy[r][blockRow] = typeArrayCopy[r][typeRow];
                        typeArrayCopy[r][typeRow] = t;

                    }

                    //elementary operations: switch XOR cols
                    for (int c = 0; c < n; c++) {

                        t = typeYihuoCopy[blockRow][c];
                        typeYihuoCopy[blockRow][c] = typeYihuoCopy[typeRow][c];
                        typeYihuoCopy[typeRow][c] = t;

                    }

                    //elementary operations: switch XOR rows
                    for (int r = 0; r < n; r++) {

                        t = typeYihuoCopy[r][blockRow];
                        typeYihuoCopy[r][blockRow] = typeYihuoCopy[r][typeRow];
                        typeYihuoCopy[r][typeRow] = t;

                    }


                    //elementary operations: switch XNOR cols
                    for (int c = 0; c < n; c++) {

                        t = typeTonghuoCopy[blockRow][c];
                        typeTonghuoCopy[blockRow][c] = typeTonghuoCopy[typeRow][c];
                        typeTonghuoCopy[typeRow][c] = t;

                    }

                    //elementary operations: switch XNOR rows
                    for (int r = 0; r < n; r++) {

                        t = typeTonghuoCopy[r][blockRow];
                        typeTonghuoCopy[r][blockRow] = typeTonghuoCopy[r][typeRow];
                        typeTonghuoCopy[r][typeRow] = t;

                    }
                }

                //if each matrix's rows matched
                if (colNum == n - 1) {
                    //recursively compare next rows until we find a isomorphic solution
                    Node[] result = getTransVertices(blockArray, blockYihuo, blockTonghuo, typeArrayCopy, typeYihuoCopy, typeTonghuoCopy, n, vertices, matchedRowNum, blockRow + 1);
                    if (result != null) {
                        return result;
                    }


                    if (matchedRowNum == n) {
                        //all rows match, but also the matrices been operated should be the same
                        if (isMaticxSame(blockArray, typeArray)) {
                            return vertices;
                        }
                        return null;
                    }

                    for (int i = 0; i < vertices.length; i++) {
                        vertices[i] = new Node(typeVertices[i].name, typeVertices[i].color);
                    }
                    matchedRowNum = matchedRowNum - 1;
                    //continue

                }


            }

        }

        return null;

    }

    private static boolean isMaticxSame(int[][] blockArray, int[][] typeArrayCopy) {
        if (blockArray.length != typeArrayCopy.length) {
            return false;
        }
        if (blockArray[0].length != typeArrayCopy[0].length) {
            return false;
        }

        for (int row = 0; row < blockArray.length; row++) {
            for (int col = 0; col < blockArray[0].length; col++) {
                if (blockArray[row][col] != typeArrayCopy[row][col]) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int mp[], int n) {
        int t;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (mp[j] > mp[j + 1]) {
                    t = mp[j];
                    mp[j] = mp[j + 1];
                    mp[j + 1] = t;
                }
            }
        }
    }


    /**
     * calculate 01 adjacency matrix
     */
    public static void oneZero(int[][] p1, int[][] p2, int n) {
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {
                if (p1[i][j] > 0) {
                    p2[i][j] = 1;
                } else {
                    p2[i][j] = 0;
                }
            }
        }

    }

    /**
     * calculate XOR matrix
     */
    public static void xor(int[][] p1, int[][] p2, int[][] p3, int n) {
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (i == j) {
                    p3[i][j] = p1[i][i];
                } else {
                    int sum1, sum12;
                    sum1 = 0;
                    for (int k = 0; k < n; k++) {
                        if (p2[i][k] == p2[j][k]) {
                            sum12 = 0;
                        } else {
                            sum12 = 1;
                        }
                        sum1 = sum1 + (p1[i][k] + p1[j][k]) * sum12;
                    }
                    p3[i][j] = sum1;
                }
            }
        }
    }

    /**
     * calculate XNOR matrix
     */
    public static void xnor(int[][] p1, int[][] p2, int[][] p4, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    p4[i][j] = p1[i][i];
                } else {
                    int sum1, sum12;
                    sum1 = 0;
                    for (int k = 0; k < n; k++) {

                        if (p2[i][k] == p2[j][k]) {
                            sum12 = 1;
                        } else {
                            sum12 = 0;
                        }

                        sum1 = sum1 + (p1[i][k] + p1[j][k]) * sum12;
                    }
                    p4[i][j] = sum1;
                }
            }
        }
    }
}
