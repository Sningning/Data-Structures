/**
 * @Classname: LoopQueue
 * @Description: 循环队列
 *
 *  没有复用 Array 这个类
 *
 *  牺牲一个单元来区分队空和队满，入队时少用一个队列单元，
 *  即约定以“队头指针在队尾指针的下一位置作为队满的标志”。
 *
 *  队满条件为：(tail + 1) % data.length == front
 *  队空条件为：front == tail
 *  队列长度为：(tail - front + QueueSize) % data.length
 *
 * @author: Song Ningning
 * @date: 2020-03-03 20:29
 */
public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    private int front, tail;
    private int size;

    public LoopQueue(int capacity){
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(E e) {

        if ( (tail + 1) % data.length == front )
            resize( getCapacity() * 2 );

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size ++;
    }

    @Override
    public E dequeue() {

        if (isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size --;
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);

        return ret;
    }

    private void resize(int newCapacity) {

        E[] newData = (E[]) new Object[newCapacity + 1];

        // 遍历方式一：终止条件从 size 考虑
        for (int i = 0; i < size; i++)
            newData[i] = data[ (i + front) % data.length ];

        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E getFront() {
        if (isEmpty())
            throw new IllegalArgumentException("Queue is empty.");

        return data[front];
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        // 遍历方式二：从 tail 考虑
        for(int i = front ; i != tail ; i = (i + 1) % data.length){  // tail 可能小于 front
            res.append(data[i]);
            if((i + 1) % data.length != tail)
                res.append(", ");
        }
        res.append("] tail");

        return res.toString();
    }


    // 测试用例
    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<>();
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
