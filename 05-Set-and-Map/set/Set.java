package set;

/**
 * @Classname: Set
 * @Description: 集合借口
 * @author: Sningning
 * @date: 2020-03-11 10:01
 */
public interface Set<E> {

    void add(E e);
    void remove(E e);
    boolean contains(E e);
    int getSize();
    boolean isEmpty();
}
