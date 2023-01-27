/**
 * @Classname: RBTree
 * @Description:
 * @author: Sningning
 * @Date: 2020-03-22 11:31
 */

// 完成红黑树的创建

public class RBTree1<K extends Comparable<K>, V> {

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
            // 在 2-3 树中添加节点永远都是与一个叶子节点先融合，红黑树中红色节点代表
            // 该节点在 2-3 树中是和其父亲节点融合在一起的
            // 在新添加一个节点时，添加的节点总是要和某个节点先进行融合
            // 因此将默认 color 设为 RED，代表该节点要在红黑树中要和在等价的 2-3 树中某个节点进行融合
            this.color = RED;
        }
    }

    private Node root;
    private int size;

    public RBTree1() {
        this.root = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 判断节点 node 的颜色
    private boolean isRed(Node node) {

        if (node == null) {
            return BLACK;
        }
        return node.color;
    }


    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, K key, V value) {

        if (node == null) {
            size++;
            return new Node(key, value);
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

        return node;
    }

    // 返回以 node 为根节点的二分搜索树中，key所在的节点
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

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node) {

        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
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

    // 从二分搜索树中删除键为key的节点
    public V remove(K key) {

        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    // 删除掉以 node 为根的二分搜索树中键为 key 的节点，递归算法
    // 返回删除节点后新的二分搜索树的根
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

            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况
            else {
                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = removeMin(node.right);
                successor.left = node.left;

                node.left = node.right = null;
                return successor;
            }
        }
    }

}
