/**
 * @author: yuanpeng
 * @create: 2021-08-16 23:20
 * @program: tarjan-java
 * @description: 使用同型矩阵方法判断是否同构的方法类
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

        int[][] blockArray = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockArray[i1] = new int[n];
        }

        //图一同型矩阵

        int[][] blockTongxin = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockTongxin[i1] = new int[n];
        }

        //图一行异或矩阵


        int[][] blockYihuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockYihuo[i1] = new int[n];
        }

        //图一行同或矩阵
        int[][] blockTonghuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            blockTonghuo[i1] = new int[n];
        }

        //图二邻接矩阵数组
        int[][] typeArray = new int[n][n];
        for (int i2 = 0; i2 < n; i2++) {
            typeArray[i2] = new int[n];
        }

        //图二同型矩阵

        int[][] typeTongxin = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            typeTongxin[i1] = new int[n];
        }

        //图二行异或矩阵

        int[][] typeYihuo = new int[n][n];
        for (int i1 = 0; i1 < n; i1++) {
            typeYihuo[i1] = new int[n];
        }

        //图二行同或矩阵

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

        //图一同型矩阵
        tx(blockArray, blockTongxin, n);

        //图一异或矩阵
        yh(blockArray, blockTongxin, blockYihuo, n);

        //图一同或矩阵
        th(blockArray, blockTongxin, blockTonghuo, n);


        //图二同型矩阵
        tx(typeArray, typeTongxin, n);

        //图二异或矩阵
        yh(typeArray, typeTongxin, typeYihuo, n);

        //图二同或矩阵
        th(typeArray, typeTongxin, typeTonghuo, n);

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

        //定义定点模板
        Node[] vertices = new Node[n];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Node(typeVertices[i].name, typeVertices[i].color);
        }
        //用于替换的临时一维数组，存放p13
        int[] tempBlockYihuo = new int[n];
        //用于替换的临时一维数组，存放p23
        int[] tempTypeYihuo = new int[n];
        //用于替换的临时一维数组，存放p1
        int[] tempBlockArray = new int[n];
        //用于替换的临时一维数组，存放p14
        int[] tempBlockTonghuo = new int[n];
        //用于替换的临时一维数组，存放p2
        int[] tempTypeArray = new int[n];
        //用于替换的临时一维数组，存放p24
        int[] tempTypeTonghuo = new int[n];

        int t;
        int colNum;//进行跳转判断

        //行行替换
        for (int blockRow = blockRowBegin; blockRow < n; blockRow++) {


            //首先进行行赋值给另外一个数组p13
            for (int j = 0; j < n; j++) {
                tempBlockYihuo[j] = blockYihuo[blockRow][j];
            }


            //首先进行行赋值给另外一个数组p1
            for (int j = 0; j < n; j++) {
                tempBlockArray[j] = blockArray[blockRow][j];
            }

            //首先进行行赋值给另外一个数组
            for (int j = 0; j < n; j++) {
                tempBlockTonghuo[j] = blockTonghuo[blockRow][j];
            }

            //tempBlockYihuo,p33,p44冒泡排序
            bubbleSort(tempBlockYihuo, n);
            bubbleSort(tempBlockArray, n);
            bubbleSort(tempBlockTonghuo, n);

            //开始进行比较,p12的每一行与p23的每一行进行比较
            for (int typeRow = blockRow; typeRow < n; typeRow++) {
                colNum = 0;

                //首先进行行赋值给另外一个数组
                for (int j = 0; j < n; j++) {
                    tempTypeYihuo[j] = typeYihuo[typeRow][j];
                }

                //首先进行行赋值给另外一个数组
                for (int j = 0; j < n; j++) {
                    tempTypeArray[j] = typeArray[typeRow][j];
                }

                //首先进行行赋值给另外一个数组
                for (int j = 0; j < n; j++) {
                    tempTypeTonghuo[j] = typeTonghuo[typeRow][j];
                }

                //p88,p55,p66冒泡排序
                bubbleSort(tempTypeYihuo, n);
                bubbleSort(tempTypeArray, n);
                bubbleSort(tempTypeTonghuo, n);

                //开始比较

                for (int col = 0; col < n; col++) {

                    if (tempBlockYihuo[col] != tempTypeYihuo[col]) {
                        if (typeRow == n - 1) {
                            System.out.println("不同构,block:"+blockRow+"type:"+typeRow);
                            return null;
                        }
                        break;
                    }
                    colNum = col;

                    if (col != n - 1) {
                        continue;
                    }

                    //开始进行邻接矩阵对应位置比较

                    for (int b = 0; b < n; b++) {
                        if (tempBlockArray[b] == tempTypeArray[b]) {
                            continue;
                        } else if (b < n - 1) {
                            System.out.println("不同构,block:"+blockRow+"type:"+typeRow);
                            return null;
                        }
                    }

                    //开始进行同或矩阵

                    for (int c = 0; c < n; c++) {
                        if (tempBlockTonghuo[c] == tempTypeTonghuo[c]) {
                            continue;
                        } else if (c < n - 1) {
                            System.out.println("不同构,block:"+blockRow+"type:"+typeRow);
                            return null;
                        }
                    }

                    matchedRowNum++;//表示成功匹配一行
                    Node tempNode = vertices[blockRow];
                    vertices[blockRow] = vertices[typeRow];
                    vertices[typeRow] = tempNode;
                    //进行 行行转换
                    for (int c = 0; c < n; c++) {

                        t = typeArrayCopy[blockRow][c];
                        typeArrayCopy[blockRow][c] = typeArrayCopy[typeRow][c];
                        typeArrayCopy[typeRow][c] = t;

                    }

                    for (int r = 0; r < n; r++) {

                        t = typeArrayCopy[r][blockRow];
                        typeArrayCopy[r][blockRow] = typeArrayCopy[r][typeRow];
                        typeArrayCopy[r][typeRow] = t;

                    }

                    //进行行行转换p23
                    for (int c = 0; c < n; c++) {

                        t = typeYihuoCopy[blockRow][c];
                        typeYihuoCopy[blockRow][c] = typeYihuoCopy[typeRow][c];
                        typeYihuoCopy[typeRow][c] = t;

                    }

                    for (int r = 0; r < n; r++) {

                        t = typeYihuoCopy[r][blockRow];
                        typeYihuoCopy[r][blockRow] = typeYihuoCopy[r][typeRow];
                        typeYihuoCopy[r][typeRow] = t;

                    }


                    //进行行行转换p24
                    for (int c = 0; c < n; c++) {

                        t = typeTonghuoCopy[blockRow][c];
                        typeTonghuoCopy[blockRow][c] = typeTonghuoCopy[typeRow][c];
                        typeTonghuoCopy[typeRow][c] = t;

                    }

                    for (int r = 0; r < n; r++) {

                        t = typeTonghuoCopy[r][blockRow];
                        typeTonghuoCopy[r][blockRow] = typeTonghuoCopy[r][typeRow];
                        typeTonghuoCopy[r][typeRow] = t;

                    }
                }

                //上面的匹配没有问题，则进行行替换

                if (colNum == n - 1) {
                    Node[] result = getTransVertices(blockArray, blockYihuo, blockTonghuo, typeArrayCopy, typeYihuoCopy, typeTonghuoCopy, n, vertices, matchedRowNum, blockRow + 1);
                    if (blockRow == 4) {
                        System.out.println();
                    }
                    if (result != null) {
                        System.out.println("同构,block:"+blockRow+"type:"+typeRow);
                        return result;
                    }


                    if (matchedRowNum == n) {
                        if (isMaticxSame(blockArray, typeArray)) {
                            System.out.println("同构,block:"+blockRow+"type:"+typeRow);
                            return vertices;
                        }
                        return null;
                    }

                    for (int i = 0; i < vertices.length; i++) {
                        vertices[i] = new Node(typeVertices[i].name, typeVertices[i].color);
                    }
                    matchedRowNum = matchedRowNum - 1;
                    //成功跳出循环判断下一行

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
     * 同型矩阵
     */
    public static void tx(int[][] p1, int[][] p2, int n) {
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
     * 异或矩阵
     */
    public static void yh(int[][] p1, int[][] p2, int[][] p3, int n) {
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
     * 同或矩阵
     */
    public static void th(int[][] p1, int[][] p2, int[][] p4, int n) {
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
