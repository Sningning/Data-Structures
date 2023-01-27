/**
 * @Classname: MaxHeap
 * @Description: 最大堆
 * @author: Sningning
 * @date: 2020-03-12 11:53
 */
public class MaxHeap<E extends Comparable<E>> {

    Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    public MaxHeap(E[] arr) {

        // O(logn)
        data = new Array<>(arr);
        for (int i = parent(arr.length - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    // 返回堆中的元素个数
    public int size() {
        return data.getSize();
    }

    // 返回一个布尔值, 表示堆中是否为空
    public boolean isEmpty() {
        return data.isEmpty();
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
    private int parent(int index) {
        if (index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return (index - 1) / 2;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    // 向堆中添加元素
    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    // 将索引为 k 位置的元素上浮
    private void siftUp(int k) {

        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    // 看堆中最大元素
    public E findMax() {
        if (data.getSize() == 0)
            throw new IllegalArgumentException("Can not findMax when heap is empty.");

        return data.get(0);
    }

    // 取出堆中最大元素
    public E extractMax() {

        E ret = findMax();

        data.swap(0, data.getSize() - 1);
        data.removeLast();  // removeLast() 方法中已经维护了 size
        siftDown(0);

        return ret;
    }

    // 将索引为 k 位置的元素下沉
    private void siftDown(int k) {

        // 当索引位置为 k 的元素已经是叶子节点了，其左孩子的索引一定等于或超过数组 size
        while (leftChild(k) < data.getSize()) {

            int j = leftChild(k);
            // 如果有右子树并且右子树比左子树的元素大，就将右子树的索引赋值给 j
            if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0)
//                j = rightChild(k);
                j++;

            // 到这一步 data[j] 一定是 leftChild 和 rightChild 中较大者

            if (data.get(k).compareTo(data.get(j)) >= 0)
                break;

            data.swap(k, j);
            k = j;
        }
    }

    // 取出堆中的最大元素，并且替换成元素 e
    public E replace(E e) {

        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

}
