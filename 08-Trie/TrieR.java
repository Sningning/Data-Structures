import java.util.TreeMap;

/**
 * @Classname: TrieR
 * @Description: TODO
 * @author: Sningning
 * @date: 2020-03-17 14:48
 */

//  TrieR 将使用递归的方式，实现 Trie 的基本功能

public class TrieR {

    private class Node {

        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }
    }

    private Node root;
    private int size;

    public TrieR() {
        root = new Node();
        size = 0;
    }

    // 获得 Trie 中存储的单词数量
    public int getSize() {
        return size;
    }

    // 向 Trie 中添加一个新的单词 word
    public void add(String word){

        add(root, word, 0);
    }

    // 向以 node 为根的 Trie 中添加 word[index...end), 递归算法
    private void add(Node node, String word, int index) {

        if (index == word.length()) {
            if(!node.isWord) {
                node.isWord = true;
                size ++;
            }
            return;
        }

        char c = word.charAt(index);
        if (node.next.get(c) == null) {
            node.next.put(c, new Node());
        }
        add(node.next.get(c), word, index + 1);
    }

    // 查询单词 word 是否在 Trie 中
    public boolean contains(String word) {
        return contains(root, word, 0);
    }

    // 在以 node 为根的 Trie 中查询单词 word[index...end) 是否存在, 递归算法
    private boolean contains(Node node, String word, int index){

        if(index == word.length())
            return node.isWord;

        char c = word.charAt(index);
        if(node.next.get(c) == null)
            return false;

        return contains(node.next.get(c), word, index + 1);
    }

    // 查询是否在 Trie 中有单词以 prefix 为前缀
    public boolean isPrefix(String prefix) {
        return isPrefix(root, prefix, 0);
    }

    // 查询在以 Node 为根的 Trie 中是否有单词以 prefix[index...end) 为前缀, 递归算法
    private boolean isPrefix(Node node, String prefix, int index) {

        // 整个 prefix 已经遍历完了
        if (index == prefix.length()) {
            return true;
        }

        char c = prefix.charAt(index);
        if (node.next.get(c) == null) {
            return false;
        }

        return isPrefix(node.next.get(c), prefix, index + 1);
    }

    // 删除 word, 返回是否删除成功, 递归算法
    public boolean remove(String word) {

        if (word.equals("")) {
            return false;
        }
        return remove(root, word, 0);
    }

    // 在以 Node 为根的 Trie 中删除单词 word[index...end),返回是否删除成功, 递归算法
    private boolean remove(Node node, String word, int index) {

        // 递归基
        if (index == word.length()) {
            if (!node.isWord) {
                return false;
            }
            node.isWord = false;
            size--;
            return true;
        }

        char c = word.charAt(index);
        if (!node.next.containsKey(c)) {
            return false;
        }

        boolean ret = remove(node.next.get(c), word, index + 1);
        Node nextNode = node.next.get(c);
        if(!nextNode.isWord && nextNode.next.size() == 0) {
            node.next.remove(word.charAt(index));
        }
        return ret;
    }

}
