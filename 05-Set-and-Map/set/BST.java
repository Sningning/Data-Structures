package set;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Classname: BST
 * @Description: 
 *
 * 二分搜索树 Binary Search Tree
 *
 * @author: Song Ningning
 * @date: 2020-03-05 22:24
 */
public class BST<E extends Comparable<E>> {

    private class Node{
        private E e;
        private Node left, right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /*
    // 循环实现向二分搜索树中添加新元素 e
    public void add(E e) {
        Node cur = root;
        while (cur != null) {
            if (e.equals(cur.e)) {
                return;
            } else if (e.compareTo(cur.e) < 0) {
                if (cur.left == null) {
                    cur.left = new Node(e);
                    size++;
                    return;
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new Node(e);
                    size++;
                    return;
                }
                cur = cur.right;
            }
        }
        root = new Node(e);
    }
     */

    // 调用递归方法实现向二分搜索树中添加新元素 e
    public void add(E e) {
        /*

        // 改进递归前的写法
        if (root == null) {
            root = new Node(e);
            size ++;
        } else {
            add(root, e);
        }

         */

        // 改进递归后的写法
        root = add(root, e);
    }

    /*
    // 向以 node 为根的二分搜索树中插入新元素 e，递归算法
    // 但算法不统一，还是要在上面 add(E e) 中处理 root 为空的情况
    private void add(Node node, E e) {

        // 递归终止条件
        if (e.equals(node.e)) {
            return;
        } else if (e.compareTo(node.e) < 0 && node.left == null) {
            node.left = new Node(e);
            size++;
            return;
        } else if (e.compareTo(node.e) > 0 && node.right == null) {
            node.right = new Node(e);
            size++;
            return;
        }

        // 递归调用
        if (e.compareTo(node.e) < 0) {
            add(node.left, e);
        } else {  // e.compareTo(node.e) > 0
            add(node.right, e);
        }
    }
     */


    // 改进后的递归算法：
    /**
     * @Description:
     *   向以 node 为根的二分搜索树中插入新元素 e，递归算法
     *   返回插入新节点后二分搜索树的根（也考虑了 root 为空的情况）
     * @param: node
     * @param e
     * @return: BST<E>.Node
     * @author: Song Ningning
     * @date: 2020-03-06 18:09
     */
    private Node add(Node node, E e) {

        // 递归终止条件
        if (node == null) {
            size ++;
            return new Node(e);
        }

        // 递归调用
        if (e.compareTo(node.e) < 0) {
            // 返回来插入新节点后的左子树，让进入递归前该节点的左子树接住这个变化
            // 这样更新完的 node 依然是根节点
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }

        // 如果 e.equals(node.e)，上面代码不执行，直接将该节点返回
        // 如果执行了上面代码，这里返回的 node 依然是以 add(Node node, E e) 中 node 为根的二分搜索树的根节点
        return node;

    }

    // 查看二分搜索树中是否包含元素 e
    public boolean contains(E e) {
        return contains(root, e);
    }

    // 看以 node 为根的二分搜索树中是否包含元素 e，递归算法
    private boolean contains(Node node, E e) {
        if (node == null)
            return false;

        if (e.compareTo(node.e) == 0) {
            return true;
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {  // e.compareTo(node.e) > 0
            return contains(node.right, e);
        }
    }

    // 二叉树的前序遍历
    public void preOrder() {
        preOrder(root);
    }

    // 前序遍历以 node 为根的二分搜索树，递归算法
    private void preOrder(Node node) {

        if (node == null)
            return;

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    // 二分搜索树的非递归前序遍历
    public void preOrderNR() {

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur.e);

            if (cur.right != null)
                stack.push(cur.right);
            if (cur.left != null)
                stack.push(cur.left);
        }
    }

    // 二分搜索树的中序遍历
    public void inOrder() {
        inOrder(root);
    }

    // 中序遍历以 node 为根的二分搜索树，递归算法
    private void inOrder(Node node) {

        if (node == null)
            return;

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    // 二分搜索树的后序遍历
    public void postOrder() {
        postOrder(root);
    }

    // 后序遍历以 node 为根的二分搜索树，递归算法
    private void postOrder(Node node) {

        if (node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    // 二分搜索树的层序遍历（广度优先遍历）
    public void levelOrder() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            System.out.println(cur.e);

            if (cur.left != null)
                queue.add(cur.left);
            if (cur.right != null)
                queue.add(cur.right);
        }
    }

    // 寻找二分搜索树的最小元素（递归实现）
    public E minimum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        return minimum(root).e;
    }

    // 返回以 node 为根的二分搜索树的最小键值所在的节点
    private Node minimum(Node node) {
        if (node.left == null)
            return node;

        return minimum(node.left);
    }

    // 寻找二分搜索树的最小元素（非递归实现）
    public E minimumNR() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        Node cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.e;
    }

    // 寻找二分搜索树的最大元素（递归实现）
    public E maximum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        return maximum(root).e;
    }

    // 返回以 node 为根的二分搜索树的最大键值所在的节点
    private Node maximum(Node node) {
        if (node.right == null)
            return node;

        return maximum(node.right);
    }

    // 寻找二分搜索树的最大元素（非递归实现）
    public E maximumNR() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        Node cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.e;
    }

    // 从二分搜索树中删除最小值所在的节点，返回最小值
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    // 删除掉以 node 为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node) {

        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除最大值所在的节点，返回最大值
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    // 删除掉以 node 为根的二分搜索树中的最大节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    // 从二分搜索树中删除元素为 e 的节点
    public void remove(E e) {
        root = remove(root,e);
    }

    // 删除掉以node为根的二分搜索树中值为e的节点, 递归算法
    // 返回删除节点后新的二分搜索树的根
    private Node remove(Node node, E e) {
        if (node == null) {
            return null;
        } else if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else {  // e.compareTo(node.e) == 0

            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
//            size++;
            successor.left = node.left;

            node.left = node.right = null;
//            size--;
            return successor;

            /*
            // 找到比待删除节点小的最大节点, 即待删除节点左子树的最大节点
            // 用这个节点顶替待删除节点的位置
            Node predecessor = maximum(node.left);
            predecessor.left = removeMax(node.left);
            predecessor.right = node.right;

            node.right = node.left = null;
            return predecessor;
             */
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    // 生成以 node 为根节点，深度为 depth 的描述二叉树的字符串（前序遍历）
    private void generateBSTString(Node node, int depth, StringBuilder res) {

        if (node == null) {
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.e + "\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++)
            res.append("-");

        return res.toString();
    }


}
