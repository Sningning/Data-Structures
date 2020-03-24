/**
 * @Classname: LinkedListQueue
 * @Description: 利用链表实现队列
 *
 *  没有复用 LikedList 类
 *  不使用虚拟头结点 dummyHead
 *
 * @author: Song Ningning
 * @date: 2020-03-04 17:13
 */
public class LinkedListQueue<E> implements Queue<E> {

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

    private Node head, tail;
    private int size;

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void enqueue(E e) {
        if (tail == null) {  // 如果队列为空，head 和 tail 均指向 null，入队时直接将 tail 指向入队的那个节点
            tail = new Node(e);
            head = tail;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

        Node retNode = head;
        head = head.next;
        retNode.next = null;
        if (head == null)  // 如果链表中只有一个元素，这时 head 和 tail 指向这一个节点，出队后要维护一下 tail
            tail = null;
        size --;
        return retNode.e;
    }

    @Override
    public E getFront() {
        if (isEmpty())
            throw new IllegalArgumentException("Queue is empty.");
        return head.e;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue: front ");

        Node cur = head;
        while (cur != null) {
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();
    }

    // 测试用例
    public static void main(String[] args){

        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        for(int i = 0 ; i < 10 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){  // 每隔 3 个就出队 1 个元素
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
