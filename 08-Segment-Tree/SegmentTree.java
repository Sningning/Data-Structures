/**
 * @Classname: SegmentTree
 * @Description: 线段树
 * @author: Song Ningning
 * @date: 2020-03-15 18:07
 */
public class SegmentTree<E> {

    private E[] data;
    private E[] tree;
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {

        // 让用户传入业务逻辑接口的实现对象
        this.merger = merger;

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }

        tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, data.length - 1);
    }

    // 在 treeIndex 的位置创建表示区间 [l...r] 的线段树，递归实现，O(4n)
    // 其中 [l...r] 指的是在 data[] 中的区间
    private void buildSegmentTree(int treeIndex, int L, int R) {

        // 递归基：区间只有一个元素，即左右区间相等
        if (L == R) {
            tree[treeIndex] = data[L];
            return;
        }

        // treeIndex 位置节点的左右子树的索引位置
        int leftTreeIndex  = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = L + (R - L) / 2;
        buildSegmentTree(leftTreeIndex, L, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, R);

        // treeIndex 位置的左右两个子树创建好以后，具体 treeIndex 位置存储的内容是根据业务逻辑决定的
        // 因此可以定义一个接口，在构造器中让用户传入实现了该接口的对象
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {

        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal!");
        }
        return data[index];
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    public int leftChild(int index) {
        return 2 * index + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    public int rightChild(int index) {
        return 2 * index + 2;
    }

    // 返回区间 [queryL, queryR] 的值
    public E query(int queryL, int queryR) {

        if (queryL < 0 || queryL >= data.length || queryR < 0 ||
                queryR >= data.length || queryL > queryR) {
            throw new IllegalArgumentException("Index is illegal.");
        }
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    // 在以 treeIndex 为根的线段树中 [l...r] 的范围里，搜索区间 [queryL...queryR] 的值，递归实现
    // [l...r] 指的是在以 treeIndex 为根的线段树中 treeIndex 节点所表示的区间（也可以把三者包装成一个节点）
    // queryL 和 queryR 是查询的区间
    private E query(int treeIndex, int L, int R, int queryL, int queryR) {

//         // 调试打印
//        System.out.println("L = " + L);
//        System.out.println("R = " + R);
//        System.out.println();

        // 递归基
        if (L == queryL && R == queryR) {
            return tree[treeIndex];
        }

        int mid = L + (R - L) / 2;
        int leftChild  = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);

        if (queryL >= mid + 1) {  // 区间范围都在右孩子
            return query(rightChild, mid + 1, R, queryL, queryR);
        }
        else if (queryR <= mid) {  // 区间范围都在左孩子
            return query(leftChild, L, mid, queryL, queryR);
        }
        else {  // 两边都有
            E leftResult  = query(leftChild, L, mid, queryL, mid);
            E rightResult = query(rightChild, mid + 1, R, mid + 1, R);
            return merger.merge(leftResult, rightResult);
        }
    }

    // 将 index 位置的值，更新为 e
    public void set(int index, E e) {

        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal!");
        }
        data[index] = e;
        set(0, 0, data.length - 1, index, e);
    }

    // 在以 treeIndex 为根的线段树中更新 index 位置的值为 e
    private void set(int treeIndex, int L, int R, int index, E e) {

        // 递归基
        if (L == R) {
            tree[treeIndex] = e;
            return;
        }

        int mid = L + (R - L) / 2;
        int leftChildIndex  = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);

        if (index <= mid) {
            set(leftChildIndex, L, mid, index, e);
        }
        else {  // index >= mid + 1
            set(rightChildIndex, mid + 1, R, index, e);
        }

        // 对该节点要重新进行一次融合操作
        tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append("[");
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                res.append(tree[i]);
            }
            else {
                res.append("null");
            }

            if (i != tree.length - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }
}
