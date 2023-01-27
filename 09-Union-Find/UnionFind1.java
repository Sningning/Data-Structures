/**
 * @Classname: UnionFind1
 * @Description: 第一版 Union-Find（Quick-Find）
 * @author: Sningning
 * @date: 2020-03-18 10:23
 */
public class UnionFind1 implements UF {

    // 第一版 Union-Find 本质就是一个数组
    private int[] id;

    public UnionFind1(int size) {

        id = new int[size];
        // 初始化, 每一个id[i]指向自己, 没有合并的元素
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    // 查找元素p所对应的集合编号
    // O(1)复杂度
    private int find(int p) {

        if (p < 0 || p >= id.length) {
            throw new IllegalArgumentException("p is out of bound.");
        }
        return id[p];
    }

    // 查看元素p和元素q是否所属一个集合
    // O(1)复杂度
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所属的集合
    // O(n) 复杂度
    @Override
    public void unionElements(int p, int q) {

        int pID = find(p);
        int qID = find(q);

        if (pID == qID) {
            return;
        }

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                id[i] = qID;
            }
        }
    }
}
