/**
 * @Classname: Queue
 * @Description: 队列接口
 * @author: Song Ningning
 * @date: 2020-03-03 18:41
 */
public interface Queue<E> {

    void enqueue(E e);

    E dequeue();

    E getFront();

    int getSize();

    boolean isEmpty();

}
