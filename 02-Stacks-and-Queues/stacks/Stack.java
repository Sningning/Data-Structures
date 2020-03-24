/**
 * @Classname: Stack
 * @Description: 栈接口
 * @author: Song Ningning
 * @date: 2020-03-03 16:22
 */
public interface Stack<E> {

    int getSize();

    boolean isEmpty();

    void push(E e);

    E pop();

    E peek();

}
