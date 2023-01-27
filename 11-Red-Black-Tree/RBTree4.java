/**
 * @Classname: RBTree2
 * @Description:
 *
 *  Red-Black Tree
 *
 * @author: Sningning
 * @Date: 2020-03-22 13:56
 */

// 向红黑树中添加元素子过程：向红黑树中的 "3-node" 的中间添加元素
// 先左旋转，再右旋转，最后进行颜色翻转

// 红黑树的优势在于添加、删除元素

public class RBTree4<K extends Comparable<K>, V> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private class Node {

        public K key;
        public V value;
        public Node left, right;
        public boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            // 在 2-3 树中添加结点永远都是与一个叶子结点先融合，红黑树中红色结点代表
            // 该结点在 2-3 树中是和其父亲结点融合在一起的
            // 在新添加一个结点时，添加的结点总是要和某个结点先进行融合
            // 因此将默认 color 设为 RED，代表该结点要在红黑树中要和在等价的 2-3 树中某个结点进行融合
            this.color = RED;
        }
    }

    private Node root;
    private int size;

    public RBTree4() {
        this.root = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 判断结点 node 的颜色
    private boolean isRed(Node node) {

        if (node == null) {
            return BLACK;
        }
        return node.color;
    }


    //   node                     x
    //  /   \      左旋转        /  \
    // T1    x   --------->   node   T3
    //      / \              /   \
    //     T2 T3            T1   T2
    // 左旋是将某个结点旋转为其右孩子的左孩子
    private Node leftRotate(Node node) {

        Node x = node.right;
        // 左旋转
        node.right = x.left;
        x.left = node;
        // 维持结点颜色
        x.color = node.color;
        node.color = RED;

        return x;
    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   -------->   y   node
    //  / \                        /  \
    // y  T1                      T1  T2
    // 右旋是某个结点旋转为其左孩子的右孩子
    // 右旋转完成后，变成了 2 结点，需要再进行一次颜色翻转
    private Node rightRotate(Node node) {

        Node x = node.left;
        // 右旋转
        node.left = x.right;
        x.right = node;
        // 维持结点颜色
        x.color = node.color;
        node.color = RED;

        return x;
    }

    // 颜色翻转
    private void filpColors(Node node) {

        node.color = RED;
        node.left.color  = BLACK;
        node.right.color = BLACK;
    }

    // 向红黑树中添加新的元素(key, value)
    public void add(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;
    }

    // 向以 node 为根的红黑树中插入元素(key, value)，递归算法
    // 返回插入新结点后红黑树的根
    private Node add(Node node, K key, V value) {

        if (node == null) {
            size++;
            return new Node(key, value);  // 默认插入红色结点
        }

        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        }
        else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        }
        else {  // key.compareTo(node.key) > 0
            node.value = value;
        }

        // 维护红黑树性质，下面 3 个条件不是互斥的，需要每个都进行判断
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            filpColors(node);
        }

        return node;
    }

    // 返回以 node 为根结点的二分搜索树中，key所在的结点
    private Node getNode(Node node, K key) {

        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {  // key.compareTo(node.key) > 0
            return getNode(node.right, key);
        }
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {

        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + "doesn't exist!");
        } else {
            node.value = newValue;
        }
    }

    // 返回以node为根的二分搜索树的最小值所在的结点
    private Node minimum(Node node) {

        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小结点
    // 返回删除结点后新的二分搜索树的根
    private Node removeMin(Node node) {

        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的结点
    public V remove(K key) {

        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    // 删除掉以 node 为根的二分搜索树中键为 key 的结点，递归算法
    // 返回删除结点后新的二分搜索树的根
    private Node remove(Node node, K key) {

        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        }
        else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        }
        else {  // key.compareTo(node.key) == 0

            // 之前在判断完 if 后直接 return，所以都用 if 判断没问题
            // 但是现在判断完暂存到 retNode 中，并没有直接 return，所以每个 if 都会进行判断
            // 其实以下三种情况互斥，所以将 3 个 if 语句改为 if-else

            // 待删除结点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            // 待删除结点右子树为空的情况
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 待删除结点左右子树均不为空的情况
            else {
                // 找到比待删除结点大的最小结点, 即待删除结点右子树的最小结点
                // 用这个结点顶替待删除结点的位置
                Node successor = minimum(node.right);
                successor.right = removeMin(node.right);
                successor.left = node.left;

                node.left = node.right = null;
                return successor;
            }
        }
    }

}
