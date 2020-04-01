/**
 * @Classname: LinkedList
 * @Description: 链表
 * @author: Song Ningning
 * @date: 2020-03-04 11:44
 */
public class LinkedList<E> {

    // 内部类：Node
    private class Node{
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e,null);
        }

        public Node() {
            this(null,null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node dummyHead;  // 虚拟头结点
    private int size;

    public LinkedList(){
        dummyHead = new Node(null, null);
        size = 0;
    }

    // 获取链表中元素个数
    public int getSize() {
        return size;
    }

    // 返回链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 在链表的 index(0-based) 位置添加新元素 e
    // 在链表中不是一个常用的操作，练习用
    public void add(int index, E e) {

        if (index < 0 || index > size) // 可以取到 size，即在末尾添加新元素
            throw new IllegalArgumentException("Add failed. Illegal index.");

        // 要找 index 前一个位置的节点，因此从 dummyHead 开始遍历
        Node prev = dummyHead;
        // 有虚拟头结点，从头部插入依然是安全的
        for (int i = 0; i < index; i++)
            prev = prev.next;

        prev.next = new Node(e, prev.next);
        size ++;
    }

    // 在链表头部添加新元素 e
    public void addFirst(E e) {
        add(0, e);
    }

    // 在链表末尾添加新元素 e
    public void addLast(E e){
        add(size, e);
    }

    // 获得链表的第 index(0-based) 个位置的元素
    // 在链表中不是一个常用的操作，练习用
    public E get(int index) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Get failed. Illegal index.");

        // 要找第 index 位置的元素，因此从 dummyHead.next 开始遍历，即整个链表第一个元素开始遍历
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++)
            cur= cur.next;

        return cur.e;
    }

    // 获得链表的第一个元素
    public E getFirst() {
        return get(0);
    }

    // 获得链表的最后一个元素
    public E getLast() {
        return get(size - 1);
    }

    // 修改链表的第 index(0-based) 个位置的元素为 e，返回该位置之前的元素
    // 在链表中不是一个常用的操作，练习用
    public E set(int index, E e) {

        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");

        // 要找当前位置，因此从 dummyHead.next 开始遍历
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        E temp = cur.e;  // 将被修改位置的元素存储起来
        cur.e = e;
        size --;
        return temp;
    }

    // 查找链表中是否存在元素 e
    public boolean contains(E e) {

        // 遍历链表
        Node cur = dummyHead.next;
        while (cur != null) {
            if (cur.e.equals(e))
                return true;

            cur = cur.next;
        }
        return false;
    }

    // 从链表中删除第 index(0-based) 个位置的元素，返回被删除的元素
    // 在链表中不是一个常用的操作，练习用
    public E remove(int index) {

        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Delet failed. Illegal index.");

        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;

        return retNode.e;
    }

    // 从链表中删除第一个元素，返回被删除的元素
    public E removeFirst() {
        return remove(0);
    }

    // 从链表中删除最后一个元素，返回被删除的元素
    public E removeLast() {
        return remove(size - 1);
    }

    // 从链表中删除元素e
    public void removeElement(E e){

        Node prev = dummyHead;
        // 找到待删除节点的前驱节点
        while(prev.next != null){
            if(prev.next.e.equals(e))
                break;
            prev = prev.next;
        }

        // 处理待删除的节点
        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size --;
        }
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        // 遍历链表
//        Node cur = dummyHead.next;
//        while (cur != null) {
//            res.append(cur + "->");
//            cur = cur.next;
//        }
        for (Node cur = dummyHead.next; cur != null; cur = cur.next)
            res.append(cur + "->");

        res.append("NULL");

        return res.toString();
    }


    // 测试用例
    public static void main(String[] args) {

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);

        Integer integer1 = linkedList.set(2, 888);
        System.out.println(integer1);
        System.out.println(linkedList);

        Integer integer2 = linkedList.remove(2);
        System.out.println(integer2);
        System.out.println(linkedList);

    }

}



