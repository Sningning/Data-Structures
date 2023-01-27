/**
 * @Classname: UnionFind6
 * @Description: TODO
 * @author: Sningning
 * @date: 2020-03-18 12:23
 */
// Path-Compression-2

public class UnionFind6 implements UF {

    private int[] parent;  // parent[i] 表示第一个元素所指向的父节点
    private int[] rank;    // rank[i] 表示以i为根的集合所表示的树的层数

    public UnionFind6(int size) {

        parent = new int[size];
        rank = new int[size];

        // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    // 查找过程, 查找元素p所对应的集合编号
    // O(h)复杂度, h为树的高度
    private int find(int p) {

        if (p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("p is out of bound.");
        }

        // 不断去查询自己的父亲节点, 直到到达根节点
        // 根节点的特点: parent[p] == p
        if (p != parent[p]) {
            // 递归，find(parent[p]) 返回 p 父亲节点的根节点，实际也就是 p 的根节点
            // 比 UnionFind5 性能差一点，UnionFind5 也能达到 UnionFind6 的效果，只不过需要多次调用 find
            // UnionFind6 相当于一直寻找，知道寻找到根节点
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }


    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素 p 和元素 q 所属的集合
    // O(h) 复杂度, h 为树的高度
    @Override
    public void unionElements(int p, int q) {

        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        // 根据两个元素所在树的 rank 不同判断合并方向
        // 将 rank 低的集合合并到 rank 高的集合上
        if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        }
        else if (rank[qRoot] < rank[pRoot]) {
            parent[qRoot] = pRoot;
        }
        else {  // rank[qRoot] == rank[pRoot]
            parent[qRoot] = pRoot;
            rank[pRoot] += 1;
        }
    }

}