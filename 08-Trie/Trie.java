import java.util.Stack;
import java.util.TreeMap;

/**
 * @Classname: Trie
 * @Description: 非递归实现 Trie
 * @author: Sningning
 * @date: 2020-03-16 20:55
 */

public class Trie {

    private class Node {

        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie() {
        this.root = new Node();
        this.size = 0;
    }

    /**
     * @Description: 获得 Trie 中存储的单词数量
     * @param: null
     * @return: int
     * @author: Sningning
     */
    public int getSize() {

        return size;
    }

    /**
     * @Description: 向 Trie 中添加一个新的单词 word，非递归实现
     * @param: word
     * @return: void
     * @author: Sningning
     */
    public void add(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }

        // 有可能该单词已存在
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }

    /**
     * @Description: 查询单词 word 是否在 Trie 中，非递归实现
     * @param: word
     * @return: boolean
     * @author: Sningning
     */
    public boolean contains(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
//        if (cur.isWord) {
//            return true;
//        }
//        else {
//            return false;
//        }
        return cur.isWord;
    }

    /**
     * @Description: 查询是否在 Trie 中有单词以 prefix 为前缀，非递归实现
     * @param: prefix
     * @return: boolean
     * @author: Sningning
     */
    public boolean isPrefix(String prefix) {

        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

    /**
     * @Description: 删除word, 返回是否删除成功，非递归实现
     * @param: word
     * @return: boolean
     * @author: Sningning
     */
    public boolean remove(String word) {

        // 将搜索沿路的节点放入栈中
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        for (int i = 0; i < word.length(); i ++){
            // 不包含该字符，直接返回 false
            if (!stack.peek().next.containsKey(word.charAt(i))) {
                return false;
            }
            // 否则压入栈
            stack.push(stack.peek().next.get(word.charAt(i)));
        }

        // 如果不是一个单词，直接返回 false
        if (!stack.peek().isWord) {
            return false;
        }

        // 将该单词结尾 isWord 置空
        stack.peek().isWord = false;
        size--;

        // 如果单词最后一个字母的节点的 next 非空，
        // 说明 trie 中还存储了其他以该单词为前缀的单词，直接返回
        // 如果单词最后一个字母的节点的 next 为空，先将该节点从栈中弹出，再依次自底向上删除节点
        if (stack.peek().next.size() > 0) {
            return true;
        }
        else {
            stack.pop();
        }

        // 自底向上删除
        for (int i = word.length() - 1; i >= 0; i --){
            stack.peek().next.remove(word.charAt(i));
            // 如果一个节点的 isWord为 true，或者是其他单词的前缀，则直接返回
            // 否则继续出栈，判断上一个节点
            if (stack.peek().isWord || stack.peek().next.size() > 0) {
                return true;
            }
            stack.pop();
        }
        return true;
    }
}
